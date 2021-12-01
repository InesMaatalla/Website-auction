package fr.eni.bll;

import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.UtilisateurDAO;

public class UserManager {

    private final UtilisateurDAO uDAO;

    public UserManager() {
        this.uDAO = DAOFactory.getUtilisateurDAO();
    }


    public void updateUser(Utilisateur user) {

        this.uDAO.update(user);

    }




}
