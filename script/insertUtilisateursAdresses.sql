INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('Bugy', 'Maatalla', 'Ines', 'ines@bugy.com', '0604030204', 'bug500', '500', 0, 1);

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('AsPointCom', 'Sorin', 'Antoine', 'contact@antoine-sorin.com', '0604030202', 'bug400', '400', 0, 2);

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('Bedomon', 'Leray', 'Alex', 'alex@bedomon.fr', '0604030201', 'bug300', '300', 0, 3);


INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('9 rue du bug', '44000', 'Nantes');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('23 rue de la fontaine', '44230', 'Saint-Sébastien-sur-Loire');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('1 rue de la piqure', '44150', 'Ancenis');


INSERT INTO CATEGORIES (libelle) VALUES ('Informatique');
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement');
INSERT INTO CATEGORIES (libelle) VALUES ('Vêtement');
INSERT INTO CATEGORIES (libelle) VALUES ('Sport & Loisirs');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES ('Apple TV 4K', 'Avec la nouvelle Apple TV 4K, profitez du meilleur de la télé. Et de vos appareils et services Apple préférés. Pour une expérience unique qui change votre salon du tout au tout.',
        '22-06-2021', '30-06-2021', 50000, 100000, 3, 1);

    INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES ('Go Pro', 'Découvrez HERO8 Black, la caméra la plus polyvalente et la plus stable de la famille HERO. Encore plus simple à transporter grâce à son design épuré, elle possède des tiges articulées intégrées permettant de changer de fixation en quelques secondes seulement.',
    '22-06-2021', '30-06-2021', 40000, 80000, 2, 1);

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
VALUES ('Mug', 'Mug Espresso "Best coffee in town".',
    '22-06-2021', '30-06-2021', 5000, 2000, 4, 5);