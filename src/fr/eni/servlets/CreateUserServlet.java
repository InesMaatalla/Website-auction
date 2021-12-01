package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Adresse;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * TODO voir la gestion de l'encodage de champs texte dans l'ajout d'un nouvel article
 */
@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        String destPage = "WEB-INF/createUser.jsp";
        req.getRequestDispatcher(destPage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        EnchereManager enchereManager = new EnchereManager();
        Utilisateur user;
        String status = req.getParameter("status");

        if(status.equals("create") ){ //create d'un user

            user = EncheresService.getUserFromRequest(req);
            enchereManager.insertUser(user);
            HttpSession s = req.getSession(true);
            s.setAttribute("user", user);

            resp.sendRedirect(req.getContextPath() + "/liste-encheres");
        }
    }
}
