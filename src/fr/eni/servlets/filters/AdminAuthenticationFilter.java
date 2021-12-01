package fr.eni.servlets.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 */
@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {

    public AdminAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("adminUser") != null);

        String loginURI = httpRequest.getContextPath() + "/admin/connexion";

        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

        boolean isLoginPage = httpRequest.getRequestURI().endsWith("connexion.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            //l'admin est déja connecté et il essaie de se reconnecté
            // on le forward ver sa page d'accueil
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/admin/");
            dispatcher.forward(servletRequest, servletResponse);

        } else if (isLoggedIn || isLoginRequest) {
            // la requete va la ou elle doit aller
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            //L'admin n'est pas connecté, l'authetification est necessaire
            //donc redirection vers sa page de login
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("connexion.jsp");
            dispatcher.forward(servletRequest, servletResponse);

        }
    }
}
