package fr.eni.dal;

import fr.eni.bo.Article;
import fr.eni.bo.Enchere;

import java.util.List;

public interface ArticleDAO {

   List<Article> selectAllArticles();
   List<Article> selectArticlesByIdCategories(int idCat);
   List<Article> selectArticlesByIdUtilisateur(int idUtilisateur);
   List<Article> selectArticlesByRecherche(String valeurRecherche);
   Article selectArticleById(int id);
   
   Article insertArticle(Article a, int idUtilisateur, int idCategorie);
   void updateArticle(Article article);


   List<Article> selectArticleByMultiCriteria(String requestType, boolean enchereEnCours, boolean enchereOuverte, boolean enchereRemporte, int categorieRecherche, String recherche, int noUtilisateur);
}
