package fr.eni.dal.jdbc;

import fr.eni.bo.Adresse;
import fr.eni.bo.AuthentificationUtilisateur;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.AuthentificationUtilisateursDAO;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.DAOFactory;

import java.sql.*;

public class AuthentificationUtilisateursJdbcImpl implements AuthentificationUtilisateursDAO {

    private final String INSERT_AUTH = "insert into AUTHENTIFICATION_UTILISATEURS (selector, validator, user_id) VALUES (?, ?, ?)";
    private final String SELECT_AUTH_BY_SELECTOR = "select id, selector, validator, user_id from authentification_utilisateurs where selector like ?";
    private final String UPDATE_AUTH = "update AUTHENTIFICATION_UTILISATEURS set selector = ?, validator = ? where id = ?";
    private final String DELETE_AUTH = "delete from authentification_utilisateurs where selector like ?";

    @Override
    public AuthentificationUtilisateur selectBySelector(String selector) {
        AuthentificationUtilisateur auth = null;
        Utilisateur user = null;

        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_AUTH_BY_SELECTOR);
        ) {
            pstmt.setString(1, selector);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                auth = new AuthentificationUtilisateur();

                auth.setId(rs.getInt("id"));
                auth.setSelector(rs.getString("selector"));
                auth.setValidator(rs.getString("validator"));

                user = DAOFactory.getUtilisateurDAO().select(rs.getInt("user_id"));

                auth.setUser(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return auth;
    }

    @Override
    public void insert(AuthentificationUtilisateur auth) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(INSERT_AUTH);
            pstt.setString(1, auth.getSelector());
            pstt.setString(2, auth.getValidator());
            pstt.setInt(3,auth.getUser().getNoUtilisateur() );

            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AuthentificationUtilisateur auth) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(UPDATE_AUTH);
            pstt.setString(1, auth.getSelector());
            pstt.setString(2, auth.getValidator());
            pstt.setInt(3,auth.getId() );

            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String selector) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(DELETE_AUTH);
            pstt.setString(1, selector);

            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
