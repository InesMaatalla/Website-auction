package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
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
@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
    private EnchereManager enchereManager;
    private Utilisateur utilisateur;

    public ProfilServlet() {
    }

    /**
     * Si on arrive du line s'inscrire on affiche le profil avec le bouton créer
     * Si on arrive du lien profil en étant connecté on affiche le bouton modifier
     * SINON on redirige vers la page de connexion
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);

        String destPage = "WEB-INF/profil.jsp";
        enchereManager = new EnchereManager();

        String idProfilRetourne = req.getParameter("idProfil");

        HttpSession s = req.getSession(false);
        Utilisateur u = (Utilisateur) s.getAttribute("user");
        int idUtilisateurSession = u.getNoUtilisateur();

        if (idProfilRetourne == null) {

            Utilisateur userSession = enchereManager.select(idUtilisateurSession);
            req.setAttribute("userSession", userSession);
        } else {
            int idProfil = Integer.parseInt(idProfilRetourne);

            if (idUtilisateurSession == idProfil) {
                Utilisateur userSession = enchereManager.select(idUtilisateurSession);
                req.setAttribute("userSession", userSession);
            } else {
                Utilisateur utilisateur = enchereManager.selectById(idProfil);
                req.setAttribute("utilisateur", utilisateur);
            }
        }

        /* TODO Afficher la liste des enchères vendus par l'utilisateur */
        req.getRequestDispatcher(destPage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        enchereManager = new EnchereManager();

        if(req.getParameter("status").equals("update") ){ //mise a jour du profil
            System.out.println("update");
            Utilisateur user =  EncheresService.getUserFromRequest(req);
            Utilisateur userInSession = EncheresService.getUserInSession(req);
            user.setNoUtilisateur(userInSession.getNoUtilisateur());
            enchereManager.updateUser(user);
            HttpSession session = req.getSession(false);
            session.setAttribute("user", user);
        }
        if(req.getParameter("status").equals("create") ){ //suppression du profil
            System.out.println("create");
            Utilisateur user = EncheresService.getUserFromRequest(req);
            enchereManager.insertUser(user);
            HttpSession s = req.getSession(true);
            s.setAttribute("user", user);
        }

        req.getRequestDispatcher("WEB-INF/liste-encheres.jsp").forward(req,resp);
    }
}

