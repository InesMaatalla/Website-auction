package fr.eni.bo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Article {

    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait retrait;
    private String dateDebutFormat;
    private String dateFinFormat;
    private String imageName;
    private int comparaisonDateDebut;
    private int comparaisonDateFin;

    public Article(int noArticle, String nomArticle, String description, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie, String dateDebutFormat, String dateFinFormat, int comparaisonDateDebut, int comparaisonDateFin) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.dateDebutFormat = dateDebutFormat;
        this.dateFinFormat = dateFinFormat;
        this.comparaisonDateDebut = comparaisonDateDebut;
        this.comparaisonDateFin = comparaisonDateFin;
    }

    public Article(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
    }

    public Article(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
    }

    public Article(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie, Retrait retrait) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.retrait = retrait;
    }

    public Article(int noArticle) {
        this.noArticle = noArticle;
    }

    public Article(int noArticle, String nomArticle, String description, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie, String dateDebutFormat, String dateFinFormat) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.dateDebutFormat = dateDebutFormat;
        this.dateFinFormat = dateFinFormat;
    }


    public Article() {

    }

    public Article(int no_article, LocalDate dateEnchere, int prixVente) {
        this.noArticle = no_article;
        this.dateDebutEncheres = dateEnchere;
        this.prixVente = prixVente;
    }
}
