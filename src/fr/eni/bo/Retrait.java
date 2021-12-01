package fr.eni.bo;

import lombok.Data;

@Data
public class Retrait {

    private Article article;
    private Adresse adresse;

    private int idArticle;
    private int idAdresse;

//    public Retrait(int idArticle, int idAdresse) {
//        this.idArticle = idArticle;
//        this.idAdresse = idAdresse;
//    }

    public Retrait(Adresse adresse) {
    }

    public Retrait(Article article, Adresse adresse) {
        this.adresse = adresse;
        this.article = article;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Retrait() {

    }

}


