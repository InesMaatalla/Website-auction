package fr.eni.dal.jdbc;

import fr.eni.bo.Adresse;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Retrait;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.RetraitDAO;

import java.sql.*;
import java.util.List;

public class RetraitDAOJdbcImpl implements RetraitDAO {

    private final String SELECT_BY_ID = "SELECT no_article, id_adresse, a.rue, a.code_postal, a.ville FROM retraits inner join ADRESSES a on RETRAITS.id_adresse = a.id WHERE no_article = ?";
    private final String INSERT_RETRAIT_BY_ID = "INSERT INTO RETRAITS (no_article, id_adresse) VALUES (?,?)";

    public List<Retrait> selectAll() {
        return null;
    }

    public Retrait selectById(int idArticle) {
        Retrait retrait = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECT_BY_ID);
        ) {
            pstt.setInt(1, idArticle);

            ResultSet rs = pstt.executeQuery();

            if (rs.next()) {
                retrait = new Retrait();
                Article article = new Article(rs.getInt(1));
                Adresse adresse = new Adresse(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));
                retrait.setArticle(article);
                retrait.setAdresse(adresse);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return retrait;

    }

    @Override
    public void insertRetraitById(int idArticle, int idAdresse) {
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(INSERT_RETRAIT_BY_ID)) {

            pstt.setInt(1, idArticle);
            pstt.setInt(2, idAdresse);

            pstt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
