USE ENCHERES;
create table ADRESSES (
    id               int identity not null,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
);
ALTER TABLE ADRESSES ADD CONSTRAINT PK_ADRESSE PRIMARY KEY  (id);
drop table RETRAITS;

create table RETRAITS
(
    no_article int not null,
    id_adresse int not null
);

ALTER TABLE RETRAITS ADD CONSTRAINT PK_RETRAIT PRIMARY KEY  (no_article);
ALTER TABLE RETRAITS
    ADD CONSTRAINT FK_RETRAITS_ARTICLES FOREIGN KEY ( no_article ) REFERENCES ARTICLES ( no_article )
        ON DELETE NO ACTION
        ON UPDATE no action

alter TABLE UTILISATEURS drop column rue;
alter TABLE UTILISATEURS drop column code_postal;
alter TABLE UTILISATEURS drop column ville;
alter table UTILISATEURS add id_adresse int not null;


alter table UTILISATEURS add constraint fk_utilisateurs_adresse foreign key (id_adresse) references ADRESSES(id) on delete cascade;



