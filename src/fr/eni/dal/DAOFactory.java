package fr.eni.dal;

import fr.eni.dal.jdbc.*;

public class DAOFactory {

    public static EnchereDAO getEnchereDAO() {
        return new EnchereDAOJdbcImpl();
    }

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOJdbcImpl();
    }

    public static CategorieDAO getCategorieDAO() {
        return new CategorieDAOJdbcImpl();
    }

    public static ArticleDAO getArticleDAO() {
        return new ArticleDAOJdbcImpl();
    }

    public static RetraitDAO getRetraitDAO() {
        return new RetraitDAOJdbcImpl();
    }

    public static AuthentificationUtilisateursDAO getAuthentificationUtilisateursDAO() {
        return new AuthentificationUtilisateursJdbcImpl();
    }
}
