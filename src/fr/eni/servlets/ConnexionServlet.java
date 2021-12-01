package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.Utils.HashGeneratorUtils;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.AuthentificationUtilisateur;
import fr.eni.bo.Utilisateur;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        req.getRequestDispatcher("WEB-INF/connexion.jsp").forward(req,resp);
    }

    /**
     * Servlet appelé lors de la connexion de l'utilisateur.
     * Si la connexion se passe bien l'utilisateur est ajouté à la session.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //récuperation de la saisie de l'utilisateur sur la pge de connexion
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //recupération de la checkbox remerber-me
        boolean rememberMe = request.getParameter("scales") != null;

        EnchereManager em = new EnchereManager();

        String destPage = "WEB-INF/connexion.jsp";

        if (email != null && email.trim().length() > 0 && password != null && password.trim().length() > 0) {
            //vérification de l'existence de ce user en bdd
            Utilisateur user = em.checkLogin(email, password);
            if (user != null) { //l'utilisateur existe ajout dans la session et redirection vers la liste des enchères
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if(rememberMe){
                    AuthentificationUtilisateur auth = new AuthentificationUtilisateur();
                    //creation des tokens de validation
                    String selector = RandomStringUtils.randomAlphabetic(12);
                    String rawValidator = RandomStringUtils.randomAlphabetic(64);
                    String hashValidator = HashGeneratorUtils.generateSHA256(rawValidator);
                    auth.setSelector(selector);
                    auth.setValidator(hashValidator);
                    auth.setUser(user);

                    em.insertAuthToken(auth);

                    Cookie userSelector = new Cookie("selector", selector);
                    Cookie userValidator = new Cookie("validator", rawValidator);
                    userSelector.setMaxAge(60 * 5); //5 minutes
                    userValidator.setMaxAge(60 * 5);

                    response.addCookie(userSelector);
                    response.addCookie(userValidator);
                }

                response.sendRedirect(request.getContextPath() + "/liste-encheres");
//                response.sendRedirect("WEB-INF/liste-encheres.jsp");
            }else {//l'utilisateur n'existe pas affichage d'un messaage d'erreur
                String message = "Invalid email/password";
                request.setAttribute("message", message);
                request.getRequestDispatcher(destPage).forward(request, response);
            }
        } else {//l'utilisateur n'existe pas affichage d'un messaage d'erreur
            String message = "Invalid email/password";
            request.setAttribute("message", message);
            request.getRequestDispatcher(destPage).forward(request, response);
        }
    }
}