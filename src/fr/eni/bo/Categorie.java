package fr.eni.bo;

import lombok.Data;

@Data
public class Categorie {

    private int noCategorie;
    private String libelle;

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public Categorie(int noCategorie, String libelle) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
    }
}
