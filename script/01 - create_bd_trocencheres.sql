-- Script de creation de la base de donnees ENCHERES
--   type :      SQL Server 2012
--   � :         2019-11-23 18:00:51 CET

/**
    Libellés des catégories d'articles
 */
CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY ,
    libelle        VARCHAR(30) NOT NULL
);


CREATE TABLE ENCHERES (
    id                  int identity primary key,
    no_utilisateur      INTEGER NOT NULL,
    no_article          INTEGER NOT NULL,
    date_enchere        DATETIME NOT NULL,
    montant_enchere     INTEGER NOT NULL
)


CREATE TABLE RETRAITS (
	no_article      INTEGER PRIMARY KEY NOT NULL ,
    id_adresse      INT NOT NULL
);



CREATE TABLE UTILISATEURS (
    no_utilisateur      INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY ,
    pseudo              VARCHAR(30) NOT NULL,
    nom                 VARCHAR(30) NOT NULL,
    prenom              VARCHAR(30) NOT NULL,
    email               VARCHAR(50) NOT NULL,
    telephone           VARCHAR(15),
    id_adresse          INT NOT NULL,
    mot_de_passe        VARCHAR(30) NOT NULL,
    credit              INTEGER NOT NULL,
    administrateur      BIT NOT NULL,
    passwordToken       VARCHAR(64) null
)


CREATE TABLE ARTICLES (
    no_article              INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY ,
    nom_article             VARCHAR(30) NOT NULL,
    description             VARCHAR(300) NOT NULL,
	date_debut_encheres     DATE NOT NULL,
    date_fin_encheres       DATE NOT NULL,
    prix_initial            INTEGER,
    prix_vente              INTEGER,
    no_utilisateur          INTEGER NOT NULL,
    no_categorie            INTEGER NOT NULL

)

create table ADRESSES (
    id               INT IDENTITY PRIMARY KEY,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
);



ALTER TABLE RETRAITS ADD CONSTRAINT FK_articles FOREIGN KEY (no_article) REFERENCES ARTICLES(no_article) ON DELETE CASCADE;
ALTER TABLE RETRAITS ADD CONSTRAINT FK_adresse FOREIGN KEY (id_adresse) REFERENCES ADRESSES(id);

ALTER TABLE UTILISATEURS ADD CONSTRAINT fk_adresse FOREIGN KEY (id_adresse) REFERENCES ADRESSES(id);

ALTER TABLE ENCHERES ADD CONSTRAINT FK_ENCHERES_UTILISATEURS FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur ) ON DELETE NO ACTION ON UPDATE no action

ALTER TABLE ENCHERES ADD CONSTRAINT FK_ENCHERES_ARTICLES_VENDUS FOREIGN KEY ( no_article ) REFERENCES ARTICLES ( no_article ) ON DELETE NO ACTION ON UPDATE no action

ALTER TABLE ARTICLES ADD CONSTRAINT FK_ARTICLES_CATEGORIES FOREIGN KEY ( no_categorie ) REFERENCES CATEGORIES ( no_categorie ) ON DELETE NO ACTION ON UPDATE no action

ALTER TABLE ARTICLES ADD CONSTRAINT FK_VENTES_UTILISATEURS FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur ) ON DELETE NO ACTION ON UPDATE no action

