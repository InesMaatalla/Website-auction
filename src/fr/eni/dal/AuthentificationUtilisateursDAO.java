package fr.eni.dal;


import fr.eni.bo.AuthentificationUtilisateur;

public interface AuthentificationUtilisateursDAO {


    AuthentificationUtilisateur selectBySelector(String selector);
    void insert(AuthentificationUtilisateur auth);
    void update(AuthentificationUtilisateur auth);

    void delete(String selector);
}
