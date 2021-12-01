package fr.eni.servlets;

import fr.eni.bll.EnchereManager;
import fr.eni.bo.AuthentificationUtilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    /**
     * Servlet appelé lors de la déconnexion de l'utilisateur.
     * La session contenant l'utilisateur est détruite.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
        }
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            String selector = "";
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("selector")){
                    selector = cookie.getValue();
                }
                if(!selector.isEmpty()){
                    EnchereManager em = new EnchereManager();
                    AuthentificationUtilisateur auth = em.findAuthTokenBySelector(selector);
                    if(auth != null){
                        em.deleteAuthToken(selector);
                        Cookie userSelector = new Cookie("selector", "");
                        userSelector.setMaxAge(0);
                        Cookie userValidator = new Cookie("validator", "");
                        userValidator.setMaxAge(0);
                        resp.addCookie(userSelector);
                        resp.addCookie(userValidator);
                    }
                }
            }
        }
        req.logout();
        resp.sendRedirect(req.getContextPath());
        //req.getRequestDispatcher("WEB-INF/liste-encheres.jsp").forward(req, resp);
    }
}
