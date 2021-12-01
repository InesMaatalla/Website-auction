--alter table ENCHERES drop constraint PK_ENCHERES;
--alter table ENCHERES add id int identity PRIMARY KEY;

--alter table ENCHERES ADD id int identity PRIMARY KEY

truncate table ENCHERES;
DROP TABLE ENCHERES;
CREATE TABLE ENCHERES (
    id int identity primary key ,
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     DATETIME NOT NULL,
    montant_enchere  INTEGER NOT NULL,
    constraint FK_article FOREIGN KEY (no_article) REFERENCES ARTICLES(no_article),
    constraint FK_utilisateur FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS(no_utilisateur)

)