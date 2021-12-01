package fr.eni.dal;

import fr.eni.bo.Adresse;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

public interface UtilisateurDAO {

    Utilisateur select(int id);
    Utilisateur insertUser(Utilisateur u);
    Adresse insertUserAdress(Adresse a);
    Utilisateur selectById(int id);
    void delete(int idUser);
    void update(Utilisateur user);
    void updateCredit(Utilisateur user );
    void updateUserAdress(Utilisateur user);
    Utilisateur checkLogin(String email, String password);
    Utilisateur selectIdAdresseByIdUer(int id);
    Adresse selectAdresseByIdUtilisateur(int id);

    boolean isMailExist(String email);
    boolean isPseudoExist(String pseudo);
}
