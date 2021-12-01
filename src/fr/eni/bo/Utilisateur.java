package fr.eni.bo;

import lombok.Data;

@Data
public class Utilisateur {

    private int noUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Adresse adresse;
    private String motDePasse;
    private int credit;
    private int idAdresse;
    private boolean administrateur;

    public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse, int credit) {
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.credit = credit;
    }

    public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse, String motDePasse, int credit, boolean administrateur) {
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
    }

    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse, String motDePasse, int credit, boolean administrateur) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
    }


    public Utilisateur() {

    }

    public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, String motDePasse, int credit, boolean administrateur) {
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
    }

    public Utilisateur(int noUtilisateur, String pseudo) {
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
    }

    public Utilisateur(String pseudo) {
        this.pseudo = pseudo;
    }

    public Utilisateur(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, Adresse adresse, String motDePasse) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
    }
}
