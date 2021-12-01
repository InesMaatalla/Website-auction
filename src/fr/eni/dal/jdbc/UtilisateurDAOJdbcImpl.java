package fr.eni.dal.jdbc;

import fr.eni.bo.Adresse;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ConnectionProvider;
import fr.eni.dal.UtilisateurDAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private final String SELECT = "SELECT e.no_utilisateur, " +
            "e.pseudo, e.nom, e.prenom, e.email, e.telephone, e.mot_de_passe, " +
            "e.credit, e.administrateur, e.id_adresse, a.rue, a.code_postal, a.ville " +
            "FROM UTILISATEURS e INNER JOIN ADRESSES a ON id_adresse = ?";
    private final String SELECT_USER = "select u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone,u.credit, u.id_adresse, a.code_postal, a.rue,  a.ville from utilisateurs u inner join adresses a on a.id = u.id_adresse where no_utilisateur = ?";
    private final String INSERT_UTILISATEUR = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe,credit, administrateur, id_adresse) VALUES (?,?,?,?,?,?,640,0,?)";
    private final String INSERT_ADRESSE = "INSERT INTO adresses (rue, code_postal, ville) VALUES (?,?,?)";
    private final String SELECT_USER_BY_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, credit, A.rue, A.code_postal, A.ville FROM utilisateurs\n" +
            "    INNER JOIN ADRESSES A on UTILISATEURS.id_adresse = A.id\n" +
            "    WHERE no_utilisateur = ?";
    private final String SELECT_ID_ADRESSE_BY_ID_USER = "SELECT id_adresse FROM utilisateurs WHERE no_utilisateur = ?";
    private final String SELECT_USER_LOGIN = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, mot_de_passe, credit   FROM UTILISATEURS where email = ? and mot_de_passe = ?";
    private final String SELECT_ADRESSE_BY_ID_USER = "SELECT rue, code_postal, ville FROM ADRESSES INNER JOIN UTILISATEURS on id_adresse = id WHERE no_utilisateur = ?";
    private final String INSERT_HASH_PWD_RESET = "INSERT INTO UTILISATEURS (passwordToken) VALUES (?) WHERE email = ?";
    private final String DELETE_USER_BY_ID = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
    private final String SELECT_EMAIL = "select count(*) from UTILISATEURS where email like ?";
    private final String SELECT_PSEUDO = "select count(*) from UTILISATEURS where pseudo like ?";
    private final String UPDATE_USER = "update utilisateurs set pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, mot_de_passe = ? where no_utilisateur = ?";
    private final String UPDATE_USER_ADRESS = "update adresses set rue = ?, code_postal = ?, ville = ? where id = ?";
    private final String UPDATE_CREDIT = "UPDATE utilisateurs set credit = ? WHERE no_utilisateur = ?";

    @Override
    public Utilisateur select(int id) {
        Utilisateur user = null;
        Adresse adresse;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Utilisateur();
                adresse = new Adresse();
                user.setNoUtilisateur(rs.getInt(1));
                user.setPseudo(rs.getString(2));
                user.setNom(rs.getString(3));
                user.setPrenom(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setTelephone(rs.getString(6));
                user.setCredit(rs.getInt(7));
                adresse.setId(rs.getInt(8));
                adresse.setCodePostal(rs.getString(9));
                adresse.setRue(rs.getString(10));
                adresse.setVille(rs.getString(11));
                user.setAdresse(adresse);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public Utilisateur insertUser(Utilisateur u) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(INSERT_UTILISATEUR, Statement.RETURN_GENERATED_KEYS);
            pstt.setString(1, u.getPseudo());
            pstt.setString(2, u.getNom());
            pstt.setString(3, u.getPrenom());
            pstt.setString(4, u.getEmail());
            if (u.getTelephone() != null) {
                pstt.setString(5, u.getTelephone());
            } else {
                pstt.setNull(5, Types.VARCHAR);
            }

            pstt.setString(6, u.getMotDePasse());
            Adresse a = u.getAdresse();
            this.insertUserAdress(a);
            pstt.setInt(7, a.getId());
            pstt.executeUpdate();
            ResultSet rs = pstt.getGeneratedKeys();

            if (rs.next()) {
                u.setNoUtilisateur(rs.getInt(1));
            }

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return u;
    }

    @Override
    public Adresse insertUserAdress(Adresse a) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(INSERT_ADRESSE, Statement.RETURN_GENERATED_KEYS);

            pstt.setString(1, a.getRue());
            pstt.setString(2, a.getCodePostal());
            pstt.setString(3, a.getVille());

            pstt.executeUpdate();
            ResultSet rs = pstt.getGeneratedKeys();

            if (rs.next()) {
                a.setId(rs.getInt(1));
            }

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return a;
    }

    @Override
    public Utilisateur selectById(int id) {
        Utilisateur utilisateur = null;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstt = cnx.prepareStatement(SELECT_USER_BY_ID);
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();

            if (rs.next()) {
                int idUtilisateur = rs.getInt(1);
                String pseudo = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String email = rs.getString(5);
                String telephone = rs.getString(6);
                int credit = rs.getInt(7);
                String rue = rs.getString(8);
                String codePostal = rs.getString(9);
                String ville = rs.getString(10);

                Adresse a = new Adresse(rue, codePostal, ville);
                utilisateur = new Utilisateur(idUtilisateur, pseudo, nom, prenom, email, telephone, a, credit);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public Utilisateur checkLogin(String email, String password) {

        Utilisateur user = null;

        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_USER_LOGIN);
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Utilisateur();
                user.setNoUtilisateur(rs.getInt(1));
                user.setPseudo(rs.getString(2));
                user.setNom(rs.getString(3));
                user.setPrenom(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setTelephone(rs.getString(6));
                user.setCredit(rs.getInt(8));
            }
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public Adresse selectAdresseByIdUtilisateur(int id) {
        Adresse adresse = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_ADRESSE_BY_ID_USER);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String rue = rs.getString(1);
                String codePostal = rs.getString(2);
                String ville = rs.getString(3);

                adresse = new Adresse(rue, codePostal, ville);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return adresse;
    }

    @Override
    public Utilisateur selectIdAdresseByIdUer(int id) {
        Utilisateur utilisateur = null;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstt = cnx.prepareStatement(SELECT_ID_ADRESSE_BY_ID_USER);
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();

            if (rs.next()) {
                int idAdresse = rs.getInt(1);
                utilisateur = new Utilisateur(idAdresse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public void delete(int idUser) {
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_USER_BY_ID);
        ) {
            pstmt.setInt(1, idUser);
            int result = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean isMailExist(String email) {
        boolean result = false;

        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_EMAIL);
        ) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (Integer.parseInt(rs.getString(1)) > 0) {// le count a retourné 1 ou plus il y a un email en bdd
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isPseudoExist(String pseudo) {
        boolean result = false;

        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PSEUDO);
        ) {
            pstmt.setString(1, pseudo);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (Integer.parseInt(rs.getString(1)) > 0) {// le count a retourné 1 ou plus il y a un email en bdd
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Utilisateur user) {

        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(UPDATE_USER);
            pstt.setString(1, user.getPseudo());
            pstt.setString(2, user.getNom());
            pstt.setString(3, user.getPrenom());
            pstt.setString(4, user.getEmail());
            pstt.setString(5, user.getTelephone());
            pstt.setString(6, user.getMotDePasse());

            pstt.setInt(7, user.getNoUtilisateur());

            this.updateUserAdress(user);


            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCredit(Utilisateur user ) {

        try (	Connection cnx = ConnectionProvider.getConnection();
                 PreparedStatement pstt = cnx.prepareStatement(UPDATE_CREDIT)) {
            pstt.setInt(1, user.getCredit());
            pstt.setInt(2, user.getNoUtilisateur());

            pstt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateUserAdress(Utilisateur user) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(UPDATE_USER_ADRESS);
            pstt.setString(1, user.getAdresse().getRue());
            pstt.setString(2, user.getAdresse().getCodePostal());
            pstt.setString(3, user.getAdresse().getVille());
            pstt.setInt(4, user.getAdresse().getId());

            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertUserHash(String token, String email) {
        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstt = cnx.prepareStatement(INSERT_HASH_PWD_RESET);
            pstt.setString(1, token);
            pstt.setString(2, email);

            pstt.executeUpdate();

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


