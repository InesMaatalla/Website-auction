package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.time.LocalDate;
import java.util.List;

@WebServlet("/detail-vente")
public class DetailVenteServlet extends HttpServlet {

    EnchereManager em = new EnchereManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);

        String destPage = "WEB-INF/detail-vente.jsp";
        HttpSession s = req.getSession(false);
        Utilisateur u = (Utilisateur) s.getAttribute("user");

        int idArticle = Integer.parseInt(req.getParameter("idArticle"));

        Utilisateur utilisateur = em.select(u.getNoUtilisateur());
        req.setAttribute("utilisateur", utilisateur);

        int pointUtilisateur = u.getCredit();

        String pointDispo = null;

        if (pointUtilisateur > 1){
            pointDispo = pointUtilisateur + " points disponibles";
        } else {
            pointDispo = pointUtilisateur + " point disponible";
        }

        req.setAttribute("pointDispo", pointDispo);

        Article article = em.selectArticleById(idArticle);
        req.setAttribute("article",article);

        int valueProposition = article.getPrixInitial();
        req.setAttribute("valueProposition", valueProposition);

        Retrait retrait = em.selectRetraitByIdArticle(idArticle);
        req.setAttribute("retrait", retrait);

        int prixVente = article.getPrixVente();
        Enchere enchere = em.selectEnchereByPrixVente(prixVente);

        if (enchere != null) {
            req.setAttribute("enchere", enchere);
        }

        boolean isWinner = false;
        boolean isLooser = false;
        boolean isPasVendu = false;
        LocalDate date = LocalDate.now();
        List<Enchere> listeEnchere = em.getLastEnchere(idArticle, 1);
        if (article.getDateFinEncheres().compareTo(date) < 0 && listeEnchere.size() == 1) { //Enchere terminé et l'utilisateur en session est le dernier enchériseur
            if (listeEnchere.get(0).getIdUtilisateur() == u.getNoUtilisateur()) {
                isWinner = true;
                String messageVictoire = "Bravo vous avez reporté l'enchère";
                req.setAttribute("messageVictoire", messageVictoire);
            }
            if (listeEnchere.get(0).getIdUtilisateur() != u.getNoUtilisateur()){
                isLooser = true;
                String messageLooser = "L'enchère a été remportée par ";
                req.setAttribute("messageLooser", messageLooser);
            }
        }
        if (article.getDateFinEncheres().compareTo(date) < 0 && listeEnchere.size() == 0) {
            isPasVendu = true;
            String messagePasVendu = "L'article n'a pas été vendu.";
            req.setAttribute("messagePasVendu", messagePasVendu);
        }
        req.setAttribute("isWinner", isWinner);
        req.setAttribute("isLooser", isLooser);
        req.setAttribute("isPasVendu", isPasVendu);
        req.getRequestDispatcher(destPage).forward(req, resp);

    }
}
