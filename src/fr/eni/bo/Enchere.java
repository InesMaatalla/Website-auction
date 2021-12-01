package fr.eni.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Enchere {

    private int idUtilisateur;
    private int idArticle;
    private int id;
    private Utilisateur utilisateur;
    private Article article;
    private LocalDateTime dateEnchere;
    private int montantEnchere;
    private int montantPropose;
    private String pseudoUtilisateur;

    public Enchere(int idUtilisateur, int idArticle, LocalDateTime dateEnchere, int montantEnchere, String pseudoUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.idArticle = idArticle;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.pseudoUtilisateur = pseudoUtilisateur;
    }

    public Enchere(Utilisateur utilisateur, Article article, LocalDateTime dateEnchere, int montantEnchere) {
        this.utilisateur = utilisateur;
        this.article = article;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere() {
    }

    public Enchere(Utilisateur utilisateur, Article article) {
        this.utilisateur = utilisateur;
        this.article = article;
    }


    public Enchere(Utilisateur utilisateur, LocalDateTime dateEnchere, Article article, int montantPropose) {
        this.utilisateur = utilisateur;
        this.dateEnchere = dateEnchere;
        this.article = article;
        this.montantEnchere = montantPropose;
    }


}
