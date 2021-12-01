package fr.eni.bo;

import lombok.Data;

@Data
public class Adresse {

    private int id;
    private String rue;
    private String codePostal;
    private String ville;

    public Adresse(){
    }

    public Adresse(int id) {
        this.id = id;
    }

    public Adresse(int id, String rue, String codePostal, String ville) {
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Adresse(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
}
