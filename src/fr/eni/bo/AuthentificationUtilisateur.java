package fr.eni.bo;

import lombok.Data;

@Data
public class AuthentificationUtilisateur {

    private int id;
    private String selector;
    private String validator;
    private Utilisateur user;

}
