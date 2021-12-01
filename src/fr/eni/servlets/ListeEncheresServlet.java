package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "liste-encheres", urlPatterns = "/liste-encheres")
public class ListeEncheresServlet extends HttpServlet {

    EnchereManager em = new EnchereManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);


        List<Categorie> listeCategories = em.selectAllCategories();
        req.setAttribute("listeCategories", listeCategories);

        List<Article> listeArticles = em.selectAllArticles();
        req.setAttribute("listeArticles", listeArticles);

        req.getRequestDispatcher("WEB-INF/liste-encheres.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        String radioButton = "disconnected";
        int idUtilisateur = -1;
        Utilisateur user = new Utilisateur();
        List<Article> listeArticles = new ArrayList<>();

        HttpSession session = req.getSession(false);
        if(session.getAttribute("user") != null) {
            user = (Utilisateur) session.getAttribute("user");
            idUtilisateur = user.getNoUtilisateur();
            radioButton = req.getParameter("radio");
        }

        Boolean checkOuverte = Boolean.valueOf(req.getParameter("checkOuverte"));
        Boolean checkEnCours = Boolean.valueOf(req.getParameter("checkEnCours"));
        Boolean checkRemportees = Boolean.valueOf(req.getParameter("checkRemportees"));
        Boolean checkMesVentesEnCours = Boolean.valueOf(req.getParameter("checkMesVentesEnCours"));
        Boolean checkNonDebutees = Boolean.valueOf(req.getParameter("checkNonDebutees"));
        Boolean checkTerminees = Boolean.valueOf(req.getParameter("checkTerminees"));


        int idCat = Integer.parseInt(req.getParameter("categories"));
        req.setAttribute("idCategories", idCat);

        String valeurRecherche = req.getParameter("recherche");

        if (valeurRecherche != null && !valeurRecherche.equals("")) {
            req.setAttribute("valeurRecherche", valeurRecherche);
        }

//        req.setAttribute("idCategories", idCat);

        List<Categorie> listeCategories = em.selectAllCategories();
        req.setAttribute("listeCategories", listeCategories);

        if(radioButton.equals("true")) {
            listeArticles = em.selectArticlebyMultiCriteria(radioButton, checkEnCours, checkOuverte,
                    checkRemportees, idCat, valeurRecherche, idUtilisateur);
        }
        if(radioButton.equals("false")) {
            listeArticles = em.selectArticlebyMultiCriteria(radioButton, checkMesVentesEnCours, checkNonDebutees,
                    checkTerminees, idCat, valeurRecherche, idUtilisateur);
        }
        if(radioButton.equals("disconnected")) {
            if(idCat < 1 && "".equals(valeurRecherche)) { //aucune catégorie de selectionnék
                listeArticles = em.selectAllArticles();
            } else {
                listeArticles = em.selectArticlebyMultiCriteria(radioButton, false, false, false, idCat, valeurRecherche,idUtilisateur);
            }
        }

        req.setAttribute("listeArticles", listeArticles);

        req.getRequestDispatcher("WEB-INF/liste-encheres.jsp").forward(req,resp);
    }
}
