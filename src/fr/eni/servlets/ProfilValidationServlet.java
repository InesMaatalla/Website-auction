package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfilValidation", urlPatterns = "/profilValidation")
public class ProfilValidationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EnchereManager em = new EnchereManager();
        boolean isMailExist;
        boolean isPseudoExist;

        String status = req.getParameter("status");
        Utilisateur userInSession = EncheresService.getUserInSession(req);

        if(status.equals("recovery")){//demande de mot de passe perdu
            String email = req.getParameter("email");
            //verification que l'email existe en bdd
            isMailExist = em.isMailExist(email);
            resp.getWriter().write(String.valueOf(isMailExist));
        } else {
            if(req.getParameter("email") != null ){ //validation de l'email
                String email = req.getParameter("email");
                isMailExist = em.isMailExist(email);
                if(status.equals("create")){
                    resp.getWriter().write(String.valueOf(!isMailExist));

                }else {
                    if(userInSession.getEmail().equals(email) ){
                        resp.getWriter().write(String.valueOf(true));
                    }else{
                        resp.getWriter().write(String.valueOf(!isMailExist));
                    }
                }
            }
            if(req.getParameter("pseudo") != null ){ //validation de l'email
                String pseudo = req.getParameter("pseudo");
                isPseudoExist = em.isPseudoExist(req.getParameter("pseudo"));
                if(status.equals("create")){
                    resp.getWriter().write(String.valueOf(!isPseudoExist));
                }else {
                    System.out.println("user in session : " + userInSession.getPseudo());
                    System.out.println("user in form : " + pseudo);
                    if(userInSession.getPseudo().equals(pseudo)){
                        System.out.println(  "equals");
                        resp.getWriter().write(String.valueOf(true));
                    } else {
                        resp.getWriter().write(String.valueOf(!isPseudoExist));
                    }
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
