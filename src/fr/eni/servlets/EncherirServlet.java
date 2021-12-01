package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/encherir")
public class EncherirServlet extends HttpServlet {

    EnchereManager em = new EnchereManager();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);

        req.getRequestDispatcher("WEB-INF/detail-vente.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) req.getSession(false).getAttribute("user");

        int noArticle = Integer.parseInt(req.getParameter("idArticle"));
        int montantPropose = Integer.parseInt(req.getParameter("montantPropose"));
        Article article = em.selectArticleById(noArticle);
        int prixInitial = article.getPrixInitial();
        int prixVente = article.getPrixVente();
        article.setPrixVente(prixVente);

        String dateFin = String.valueOf(article.getDateFinEncheres());
        article.setDateFinEncheres(LocalDate.parse(dateFin));

        int idArticle = Integer.parseInt(req.getParameter("idArticle"));
        req.setAttribute("utilisateur", utilisateur);
        req.setAttribute("article", article);
        Retrait retrait = em.selectRetraitByIdArticle(idArticle);
        req.setAttribute("retrait", retrait);
        Enchere enchere = null;

        if (utilisateur.getCredit() > 0 ){
            if (utilisateur.getCredit() > prixVente) {
                    if (montantPropose < utilisateur.getCredit() ) {
                        if (montantPropose > prixVente){
                            LocalDateTime dateEnchere = LocalDateTime.now();
                            Enchere newEnchere = new Enchere(utilisateur, dateEnchere, article, montantPropose);
                            newEnchere.setDateEnchere(dateEnchere);
                            try {
                                em.encherir(newEnchere);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            if (montantPropose > prixInitial) {
                                utilisateur.setCredit(utilisateur.getCredit() - montantPropose );
                                em.updateCredit(utilisateur);

                                article.setPrixVente(newEnchere.getMontantEnchere());
                                em.updateArticle(article);

                                enchere = em.selectEnchereByPrixVente(newEnchere.getMontantEnchere());
                                req.setAttribute("enchere", enchere);
                                String noUtilisateur = String.valueOf(enchere.getUtilisateur());
                                enchere.setIdUtilisateur(enchere.getIdUtilisateur());

                                String msg1 = "Nouvelle offre de prix !";
                                req.setAttribute("msg1", msg1);

                                renvoyerLaValeurDeLaProposition(req, article);

                                renvoyerLeNombreDePointsDisponibles(req, utilisateur);
                            } else {
                                String msg2 = "Enchère insuffisante";
                                req.setAttribute("msg2", msg2);
                                renvoyerLesInformationsDeLenchere(req, article);

                                renvoyerLeNombreDePointsDisponibles(req, utilisateur);
                                renvoyerLaValeurDeLaProposition(req, article);
                            }

                        } else {
                            String msg2 = "Enchère insuffisante";
                            req.setAttribute("msg2", msg2);
                            renvoyerLesInformationsDeLenchere(req, article);

                            renvoyerLeNombreDePointsDisponibles(req, utilisateur);
                            renvoyerLaValeurDeLaProposition(req, article);

                        }
                    } else  {
                        String msg3 = "Points insuffisant";
                        req.setAttribute("msg3", msg3);
                        renvoyerLesInformationsDeLenchere(req, article);
                        renvoyerLeNombreDePointsDisponibles(req, utilisateur);
                        renvoyerLaValeurDeLaProposition(req, article);
                    }
            } else {
                String msg4 = "Pas assez de points";
                req.setAttribute("msg4", msg4);
                renvoyerLesInformationsDeLenchere(req, article);
                renvoyerLeNombreDePointsDisponibles(req, utilisateur);
                renvoyerLaValeurDeLaProposition(req, article);
            }
        } else {
            String msg5 = "Aucun crédit";
            req.setAttribute("msg5", msg5);
            renvoyerLesInformationsDeLenchere(req, article);
            renvoyerLeNombreDePointsDisponibles(req, utilisateur);
            renvoyerLaValeurDeLaProposition(req, article);
        }

        req.getRequestDispatcher("WEB-INF/detail-vente.jsp").forward(req,resp);
    }

    private void renvoyerLaValeurDeLaProposition(HttpServletRequest req, Article article) {
        int valueProposition = 0;
        if(article.getPrixVente() > article.getPrixInitial()) {
            valueProposition = article.getPrixVente();
            req.setAttribute("valueProposition", valueProposition);
        } else {
            valueProposition = article.getPrixInitial();
            req.setAttribute("valueProposition", valueProposition);
        }
    }

    private void renvoyerLesInformationsDeLenchere(HttpServletRequest req, Article article) {
        Enchere enchere;
        enchere = em.selectEnchereByPrixVente(article.getPrixVente());
        req.setAttribute("enchere", enchere);
    }

    private void renvoyerLeNombreDePointsDisponibles(HttpServletRequest req, Utilisateur utilisateur) {
        int pointUtilisateur = utilisateur.getCredit();
        String pointDispo = null;
        if (pointUtilisateur > 1){
            pointDispo = pointUtilisateur + " points disponibles";
        } else {
            pointDispo = pointUtilisateur + " point disponible";
        }
        req.setAttribute("pointDispo", pointDispo);
    }
}








