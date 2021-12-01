package fr.eni.dal.jdbc;

import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.EnchereDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private final String INSERT = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?,?,?,?)";
    private final String SELECT_BY_ID = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES e WHERE e.no_article = ?";
    private final String SELECT_MONTANT_ENCHERE = "SELECT montant_enchere FROM encheres WHERE no_article = ? AND no_utilisateur = ?";
    private final String SELECT_ENCHERE_BY_PRIX_VENTE = "SELECT E.no_utilisateur, E.no_article, E.date_enchere, E.montant_enchere, U.pseudo FROM ENCHERES E\n" +
            "INNER JOIN UTILISATEURS U on U.no_utilisateur = E.no_utilisateur WHERE E.montant_enchere = ?";


    @Override
    public Enchere insert(Enchere enchere) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(INSERT);
            pstt.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
            pstt.setInt(2, enchere.getArticle().getNoArticle());
            pstt.setTimestamp( 3, Timestamp.valueOf(enchere.getDateEnchere()));
            pstt.setInt(4, enchere.getMontantEnchere());

            pstt.executeUpdate();
            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return enchere;
    }


    @Override
    public Enchere selectById(int id) {
        Enchere listeEnchere = new Enchere();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int no_utilisateur = rs.getInt(2);
                int no_article = rs.getInt(3);
                LocalDateTime date_enchere = LocalDateTime.from(rs.getDate(4).toLocalDate());
                int montant_enchere = rs.getInt(5);
                listeEnchere = new Enchere(new Utilisateur(no_utilisateur),
                        new Article(no_article),
                        date_enchere,
                        montant_enchere);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listeEnchere;
    }

    /**
     * Récupère l'enchère la plus élevée de l'article par rapport à son prix de vente actuel
     * et le pseudo de l'utilisateur qui a fait l'enchère.
     * @param prixVente
     * @return
     */
    @Override
    public Enchere selectEnchereByPrixVente(int prixVente) {
        Enchere enchere = null;
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_PRIX_VENTE);
            pstmt.setInt(1, prixVente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int no_utilisateur = rs.getInt(1);
                int no_article = rs.getInt(2);
                Timestamp date_enchere = rs.getTimestamp(3);
                LocalDateTime dateEnchereFormat = date_enchere.toLocalDateTime();
                int montant_enchere = rs.getInt(4);
                String pseudoUtilisateur = rs.getString(5);

                enchere = new Enchere(no_utilisateur, no_article, dateEnchereFormat, montant_enchere, pseudoUtilisateur);
            }
            rs.close();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return enchere;
    }

    @Override
    public int selectMontantEnchere(int noArticle, int noUtilisateur) {
        int creditEncherie = 0;

        try (Connection cnx = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = cnx.prepareStatement(SELECT_MONTANT_ENCHERE);
            ResultSet rs = pstmt.executeQuery();
            pstmt.setInt(1, rs.getInt("no_article"));
            pstmt.setInt(2, rs.getInt("no_utilisateur"));


            if (rs.next()) {
                creditEncherie = rs.getInt("montant_enchere");
            }

            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditEncherie;
    }

    @Override
    public List<Enchere> getLastEnchere(int idArticle, int nbRow) {
        String requete = "select TOP(?)  id, ENCHERES.no_utilisateur, ENCHERES.no_article, date_enchere, montant_enchere\n" +
                "from ENCHERES\n" +
                "inner join ARTICLES A on ENCHERES.no_article = A.no_article\n" +
                "where ENCHERES.no_article = ? \n" +
                "and date_fin_encheres < GETDATE()\n" +
                "order by date_enchere DESC";

        List<Enchere> listeEncheres = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()){


            PreparedStatement pstmt = cnx.prepareStatement(requete);
            pstmt.setInt(1, nbRow);
            pstmt.setInt(2, idArticle);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setId(rs.getInt(1));
                enchere.setIdUtilisateur(rs.getInt(2));
                enchere.setIdArticle(rs.getInt(3));
                enchere.setDateEnchere(rs.getTimestamp(4).toLocalDateTime());
                enchere.setMontantEnchere(rs.getInt(5));
                listeEncheres.add(enchere);
            }

            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeEncheres;
    }

}

