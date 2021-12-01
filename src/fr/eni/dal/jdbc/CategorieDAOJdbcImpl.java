package fr.eni.dal.jdbc;


import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.CategorieDAO;
import fr.eni.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CategorieDAOJdbcImpl implements CategorieDAO {

    private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";


    @Override
    public List<Categorie> selectAllCategories() {
        List<Categorie> listeCategories = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();
             Statement pstmt = cnx.createStatement();
             ResultSet rs = pstmt.executeQuery(SELECT_ALL_CATEGORIES)) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String libelle = rs.getString(2);
                Categorie cat = new Categorie(id, libelle);
                listeCategories.add(cat);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listeCategories;

    }
}
