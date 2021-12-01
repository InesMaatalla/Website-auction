-- Addresses
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('9 rue du bug', '44000', 'Nantes');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('23 rue de la fontaine', '44230', 'Saint-Sébastien-sur-Loire');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('1 rue francklin', '44150', 'Ancenis');

INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('47 Boulevard Paul Chabas', '44100', 'Nantes');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('Rue Eugène Delacroix', '44100', 'Nantes');
INSERT INTO ADRESSES (rue, code_postal, ville) VALUES ('1 Rue Amédée de la Patellière', '44100', 'Nantes');

-- Utilisateurs
INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('Bugy', 'Maatalla', 'Ines', 'ines@gmail.com', '0604030204', '1111111111', '600', 0, (SELECT id FROM ADRESSES WHERE rue = '9 rue du bug'));


INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('AsPointCom', 'Sorin', 'Antoine', 'antoine@gmail.com', '0604030202', '2222222222', '13542', 0, (SELECT id FROM ADRESSES WHERE rue = '23 rue de la fontaine') );

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('Bedomon', 'Leray', 'Alex', 'alex@gmail.com', '0604030201', '3333333333', '60000', 0, (SELECT id FROM ADRESSES WHERE rue = '1 rue francklin') );

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,mot_de_passe, credit, administrateur, id_adresse)
values ('test', 'test', 'test', 'test@gmail.com', '0604030209', '4444444444', '0', 0, (SELECT id FROM ADRESSES WHERE rue = '47 Boulevard Paul Chabas'));

-- Catégories
INSERT INTO CATEGORIES (libelle) VALUES ('Informatique');
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement');
INSERT INTO CATEGORIES (libelle) VALUES ('Vêtement');
INSERT INTO CATEGORIES (libelle) VALUES ('Sport & Loisirs');

-- Informatique
INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('TV 4K Apple', 'Avec la nouvelle Apple TV 4K, profitez du meilleur de la télé. Et de vos appareils et services Apple préférés. Pour une expérience unique qui change votre salon du tout au tout.',
        '01-07-2021', '10-07-2021', 500, 0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'AsPointCom'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Informatique'), 'appletv.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Apple TV 4K', 'Avec la nouvelle Apple TV 4K, profitez du meilleur de la télé. Et de vos appareils et services Apple préférés. Pour une expérience unique qui change votre salon du tout au tout.',
        '01-07-2021', '30-07-2021', 219, 0 , (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bedomon'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Informatique'), 'rfb-apple-tv.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Go Pro', 'Découvrez HERO8 Black, la caméra la plus polyvalente et la plus stable de la famille HERO. Encore plus simple à transporter grâce à son design épuré, elle possède des tiges articulées intégrées permettant de changer de fixation en quelques secondes seulement.',
        '22-06-2021', '30-06-2021',650, 0 , (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bugy'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Informatique'), 'GoPro-Hero-8.jpg');

-- Ameublement
INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Fauteuil', 'Pour un design hors du commun et un confort hors normes optez pour le fauteuil main CATCHY en velours qui donnera un style tendance à votre intérieur !',
        '22-08-2021', '30-08-2021', 200, 0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bedomon'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Ameublement'), 'fauteuil.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Lampe de Chevet', 'LED Design 8W RGBW Dimmable.',
        '02-07-2021', '04-07-2021', 150, 0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bugy'),(SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Ameublement'), 'lampe.jpg');

-- Vêtement
INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Veste à capuche premium Homme', 'Développeur Web Edition',
        '22-06-2021', '30-06-2021', 350, 0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'AsPointCom'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Vêtement'), 'veste.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('CSS Is Awesome Tshirt', 'For Web Developers & Programmers',
        '01-07-2021', '08-07-2021',2000,  0 , (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bedomon'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Vêtement'), 'tshirt.jpg');

-- Sport & Loisirs
INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Team-Ball Los Angeles Lakers', 'Ballon de basket officiel des Los Angeles Lakers',
        '22-06-2021', '01-07-2021', 1500, 0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'AsPointCom'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Sport & Loisirs'), 'ballon.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('LEGO Technic Lamborghini', 'Voiture de Course, Set avancé pour Adultes, modèle de Collection',
        '22-06-2021', '30-08-2021',249412,  0, (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bugy'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Sport & Loisirs'), 'lambo.jpg');

INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, image_path)
VALUES ('Planche de surf 7ft', 'Une planche compacte avec un bon volume pour prendre la vague',
        '22-06-2021', '30-08-2021', 1790, 0 , (SELECT no_utilisateur FROM utilisateurs WHERE pseudo LIKE 'Bugy'), (SELECT no_categorie FROM CATEGORIES WHERE libelle LIKE 'Sport & Loisirs'), 'image-not-found.jpg');

-- Enchères
INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'AsPointCom' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'CSS Is Awesome Tshirt'  ), '2021-01-07 10:01:00', 2001);

UPDATE UTILISATEURS set credit = '11541' WHERE pseudo = 'AsPointCom';
UPDATE ARTICLES set prix_vente = 2001 WHERE nom_article = 'CSS Is Awesome Tshirt';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'AsPointCom' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'), '2021-23-06 14:15:05', 651);

UPDATE UTILISATEURS set credit = '10890' WHERE pseudo = 'AsPointCom';
UPDATE ARTICLES set prix_vente = 651 WHERE nom_article = 'Go Pro';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'Bedomon' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'  ), '2021-23-06 16:15:05', 660);

UPDATE UTILISATEURS set credit = '59340' WHERE pseudo = 'Bedomon';
UPDATE ARTICLES set prix_vente = 660 WHERE nom_article = 'Go Pro';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'AsPointCom' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'  ), '2021-25-06 09:54:15', 700);

UPDATE UTILISATEURS set credit = '10190' WHERE pseudo = 'AsPointCom';
UPDATE ARTICLES set prix_vente = 700 WHERE nom_article = 'Go Pro';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'Bedomon' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'  ), '2021-27-06 20:15:25', 710);

UPDATE UTILISATEURS set credit = '58630' WHERE pseudo = 'Bedomon';
UPDATE ARTICLES set prix_vente = 710 WHERE nom_article = 'Go Pro';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'AsPointCom' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'  ), '2021-29-06 21:30:55', 750);

UPDATE UTILISATEURS set credit = '9440' WHERE pseudo = 'AsPointCom';
UPDATE ARTICLES set prix_vente = 750 WHERE nom_article = 'Go Pro';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'Bugy' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Apple TV 4K' ), '2021-01-07 11:31:11', 220);

UPDATE UTILISATEURS set credit = '380' WHERE pseudo = 'Bugy';
UPDATE ARTICLES set prix_vente = 220 WHERE nom_article = 'Apple TV 4K';

INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
VALUES ((SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo LIKE 'Bedomon' ), (SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Veste à capuche premium Homme'  ), '2021-01-07 11:11:11', 355);

UPDATE UTILISATEURS set credit = '58275' WHERE pseudo = 'Bedomon';
UPDATE ARTICLES set prix_vente = 355 WHERE nom_article = 'Veste à capuche premium Homme';

-- Retrait

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'CSS Is Awesome Tshirt'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'CSS Is Awesome Tshirt'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Team-Ball Los Angeles Lakers'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Team-Ball Los Angeles Lakers'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Apple TV 4K'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Apple TV 4K'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'TV 4K Apple'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'TV 4K Apple'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Go Pro'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Go Pro'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Fauteuil'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Fauteuil'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Lampe de Chevet'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Lampe de Chevet'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'LEGO Technic Lamborghini'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'LEGO Technic Lamborghini'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Planche de surf 7ft'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Planche de surf 7ft'));

INSERT INTO RETRAITS (no_article, id_adresse)
VALUES ((SELECT no_article FROM ARTICLES WHERE nom_article LIKE 'Veste à capuche premium Homme'), (SELECT u.id_adresse FROM UTILISATEURS u INNER JOIN ARTICLES A on u.no_utilisateur = A.no_utilisateur WHERE nom_article  LIKE 'Veste à capuche premium Homme'));