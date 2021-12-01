package fr.eni.servlets.filters;

import fr.eni.Utils.HashGeneratorUtils;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.AuthentificationUtilisateur;
import fr.eni.bo.Enchere;
import org.apache.commons.lang3.RandomStringUtils;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Ce filtre intercepte la requête transforme
 * la réponse pour implémenter la fonction d'authentification
 * Depuis la version 4.0 des servlets on implémente seulement la méthode nécéssaire
 * puisque les methodes init et destroy on des cpmt par défauts
 */
@WebFilter("/*")
public class UserAuthenticationFilter implements Filter {
    private HttpServletRequest httpRequest;
    private HttpServletResponse httpReponse;

    /**
     * Ce tableau regroupe toutes les urls qui nécéssite une authentification
     * TODO detruire les cookies lors de la déconnexion
     */
    private static final String[] loginRequiredURLs = {
            "/detail-vente", "/encherir", "/profil", "/ajout-article" /* , "/update_profile" edit_profile ?? */
    };

    private static final String[] availableUrl = {
            "/detail-vente", "/liste-encheres", "/ajout-article", "/profil", "/detail-vente", "/encherir"
    };

    public UserAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        httpRequest = (HttpServletRequest) servletRequest;
        httpReponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String requestUri = httpRequest.getRequestURI();

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());



        if (path.startsWith("/admin/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (path.startsWith("/profilValidation")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        String loginURI = httpRequest.getContextPath() + "/connexion";
//        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
//        boolean isLoginPage = httpRequest.getRequestURI().endsWith("/connexion");
//        boolean isSubscribeInProgress = httpRequest.getMethod().equalsIgnoreCase("POST") && httpRequest.getRequestURI().endsWith("profil");
//        boolean isSubscribeRequest =  "inscription".equals(servletRequest.getParameter("from") );

        Cookie[] cookies = httpRequest.getCookies();
        //l'utilisateur n'est pas connecté et il y a des cookies
        if(!isLoggedIn && cookies != null) {
            String selector = "";
            String rawValidaotr = "";

            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("selector")) {
                    selector = cookie.getValue();
                } else if(cookie.getName().equals("validator")) {
                    rawValidaotr = cookie.getValue();
                }
            }

            if(!"".equals(selector) && !"".equals(rawValidaotr)) {
                EnchereManager em = new EnchereManager();
                AuthentificationUtilisateur auth = em.findAuthTokenBySelector(selector);

                if (auth != null) {
                    String hashValidatorBdd = auth.getValidator();
                    String hashValidatorCookie = HashGeneratorUtils.generateSHA256(rawValidaotr);

                    if(hashValidatorCookie.equals(hashValidatorBdd)) {
                        session = httpRequest.getSession();
                        session.setAttribute("user", auth.getUser());
                        isLoggedIn = true;

                        //mise à jour du token
                        String newSelector = RandomStringUtils.randomAlphanumeric(12);
                        String newRawValidator  = RandomStringUtils.randomAlphanumeric(64);
                        String newHashValidator = HashGeneratorUtils.generateSHA256(newRawValidator);

                        auth.setSelector(newSelector);
                        auth.setValidator(newHashValidator);
                        em.updateAuthToken(auth);

                        //mise a jour des cookies
                        Cookie selectorCookie = new Cookie("selector", selector);
                        selectorCookie.setMaxAge(60 * 5);
                        Cookie validatorCookie = new Cookie("validator", newRawValidator);
                        validatorCookie.setMaxAge(60 * 5);

                        httpReponse.addCookie(selectorCookie);
                        httpReponse.addCookie(validatorCookie);
                    }

                }
            }
        }

        if (!isLoggedIn && isLoginRequired())  {
            //on crée un cookie pour stocker l'url demandé avant le login
            for (String url : availableUrl) {
                if (requestUri.contains(url)) {
                    Cookie redirectCookie = new Cookie("redirect", path);
                    httpReponse.addCookie(redirectCookie);
                }
            }

            // L'utilissateur n'est pas loggé et la page demandé nécéssite une authentification
            //ensuite on redirige le user vers lapage demandé
            String loginPage = "WEB-INF/connexion.jsp";
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
            dispatcher.forward(servletRequest, servletResponse);
        } else {
            String redirect = "";
            //si le cookie redirect existe on redirige vers la valeur du cookie
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("redirect")) {
                    redirect = cookie.getValue();
                }
            }
            redirect = "WEB-INF" + redirect;
            //pour les pages qui ne nécéssite pas d'authentification
            // ou l'utilisateur est déja connecté on continue vers la page demandé
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();

        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }

}
