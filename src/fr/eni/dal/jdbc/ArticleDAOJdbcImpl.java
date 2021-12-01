package fr.eni.dal.jdbc;


import fr.eni.bo.Adresse;
import fr.eni.bo.Retrait;
import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.ConnectionProvider;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ArticleDAOJdbcImpl implements ArticleDAO {

    private final String SELECT_ALL_ARTICLES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo, image_path FROM ARTICLES\n" +
            "            INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur ORDER BY date_fin_encheres";

    private final String INSERT_ARTICLE = "INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie, image_path) VALUES (?,?,?,?,?,?,?,?)";

    private final String SELECT_BY_ID = "" +
            "SELECT " +
                "A.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, AD.id, AD.rue, AD.code_postal, AD.ville, pseudo, libelle, A.image_path\n" +
            "FROM " +
                "ARTICLES A\n" +
            "INNER JOIN UTILISATEURS U on U.no_utilisateur = A.no_utilisateur\n" +
            "INNER JOIN RETRAITS R2 on A.no_article = R2.no_article\n" +
            "INNER JOIN CATEGORIES C on C.no_categorie = A.no_categorie\n" +
            "INNER JOIN ADRESSES AD on R2.id_adresse = AD.id\n" +
            "WHERE R2.no_article = ?";

    private final String SELECT_ARTICLES_BY_ID_CATEGORIES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo FROM ARTICLES\n" +
            "INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur WHERE no_categorie = ?";

    private final String SELECT_ARTICLES_BY_ID_UTILISATEUR = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo FROM ARTICLES\n" +
            "INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur WHERE U.no_utilisateur = ?";

    private final String UPDATE_ARTICLE = "UPDATE articles set prix_vente = ? WHERE no_article = ?";

    /**
     * Récupère la liste des articles et retourne une liste d'articles
     * si la date de fin d'enchère est après la date du jour.
     * @return listeArticles
     */
    @Override
    public List<Article> selectAllArticles() {
        List<Article> listeArticles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();
             Statement pstmt = cnx.createStatement();
             ResultSet rs = pstmt.executeQuery(SELECT_ALL_ARTICLES)) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int numUtilisateur = rs.getInt(8);
                int numCategorie = rs.getInt(9);
                String pseudo = rs.getString(10);
                String image_name = rs.getString(11);

                LocalDate dateDuJour = LocalDate.now();
                Utilisateur utilisateur = new Utilisateur(numUtilisateur, pseudo);
                Categorie categorie = new Categorie(numCategorie);

                int comparaisonDateDebut = dateDuJour.compareTo(dateDebut);
                int comparaisonDateFin = dateDuJour.compareTo(dateFin);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateDebutFormat = dateDebut.format(formatter);
                String dateFinFormat = dateFin.format(formatter);

                Article a = new Article(id, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat, comparaisonDateDebut, comparaisonDateFin);
                a.setImageName(image_name);

                listeArticles.add(a);
            }




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listeArticles;
    }

    @Override
    public Article selectArticleById(int id) {
        Article article = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idArticle = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int idAdresse = rs.getInt(8);
                String rue = rs.getString(9);
                String codePostal = rs.getString(10);
                String ville = rs.getString(11);
                String pseudo = rs.getString(12);
                String libelle = rs.getString(13);
                String image_name = rs.getString(14);


                Utilisateur utilisateur = new Utilisateur(pseudo);
                Categorie categorie = new Categorie(libelle);
                Adresse adresse = new Adresse(idAdresse, rue, codePostal, ville);

                LocalDate dateDuJour = LocalDate.now();
                int comparaisonDateDebut = dateDuJour.compareTo(dateDebut);
                int comparaisonDateFin = dateDuJour.compareTo(dateFin);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateDebutFormat = dateDebut.format(formatter);
                String dateFinFormat = dateFin.format(formatter);

                article = new Article(idArticle, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat, comparaisonDateDebut, comparaisonDateFin);
                article.setImageName(image_name);
                article.setDateFinEncheres(dateFin);
                article.setDateDebutEncheres(dateDebut);
                Retrait r = new Retrait(article, adresse);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return article;
    }

    @Override
    public Article insertArticle(Article a, int idUtilisateur, int idCategorie) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstt = cnx.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);

            pstt.setString(1, a.getNomArticle());
            pstt.setString(2, a.getDescription());
            pstt.setDate(3, java.sql.Date.valueOf(a.getDateDebutEncheres()));
            pstt.setDate(4, java.sql.Date.valueOf(a.getDateFinEncheres()));
            pstt.setInt(5, a.getPrixInitial());
            pstt.setInt(6, idUtilisateur);
            pstt.setInt(7, idCategorie);
            pstt.setString(8, a.getImageName());

            pstt.executeUpdate();
            ResultSet rs = pstt.getGeneratedKeys();

            if (rs.next()) {
                a.setNoArticle(rs.getInt(1));
            }

            pstt.close();
            cnx.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return a;
    }

    @Override
    public void updateArticle(Article article) {
        try (	Connection cnx = ConnectionProvider.getConnection();
                 PreparedStatement pstt = cnx.prepareStatement(UPDATE_ARTICLE)) {
            pstt.setInt(1, article.getPrixVente());
            pstt.setInt(2, article.getNoArticle());

            pstt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Article> selectArticlesByIdCategories(int idCat) {
        List<Article> listeArticles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLES_BY_ID_CATEGORIES);
        ) {
            pstmt.setInt(1, idCat);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int numUtilisateur = rs.getInt(8);
                int numCategorie = rs.getInt(9);
                String pseudo = rs.getString(10);

                LocalDate dateDuJour = LocalDate.now();

                int resultatDate = dateDuJour.compareTo(dateFin);
                int comparaisonDateDebut = dateDuJour.compareTo(dateDebut);
                int comparaisonDateFin = dateDuJour.compareTo(dateFin);

                if (resultatDate <= 0) {
                    Utilisateur utilisateur = new Utilisateur(numUtilisateur, pseudo);
                    Categorie categorie = new Categorie(numCategorie);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateDebutFormat = dateDebut.format(formatter);
                    String dateFinFormat = dateFin.format(formatter);

                    Article a = new Article(id, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat, comparaisonDateDebut,
                            comparaisonDateFin);
                    listeArticles.add(a);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listeArticles;
    }

    @Override
    public List<Article> selectArticlesByRecherche(String valeurRecherche) {
        List<Article> listeArticles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();
             Statement pstmt = cnx.createStatement();
             ResultSet rs = pstmt.executeQuery(SELECT_ALL_ARTICLES)) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int numUtilisateur = rs.getInt(8);
                int numCategorie = rs.getInt(9);
                String pseudo = rs.getString(10);

                if (nomArticle.contains(valeurRecherche)) {
                    Utilisateur utilisateur = new Utilisateur(numUtilisateur, pseudo);
                    Categorie categorie = new Categorie(numCategorie);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateDebutFormat = dateDebut.format(formatter);
                    String dateFinFormat = dateFin.format(formatter);

                    Article a = new Article(id, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat);
                    listeArticles.add(a);
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listeArticles;
    }

    @Override
    public List<Article> selectArticlesByIdUtilisateur(int idUtilisateur) {
        List<Article> listeArticles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLES_BY_ID_UTILISATEUR);
        ) {
            pstmt.setInt(1, idUtilisateur);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int numUtilisateur = rs.getInt(8);
                int numCategorie = rs.getInt(9);
                String pseudo = rs.getString(10);

                Utilisateur utilisateur = new Utilisateur(numUtilisateur, pseudo);
                Categorie categorie = new Categorie(numCategorie);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateDebutFormat = dateDebut.format(formatter);
                String dateFinFormat = dateFin.format(formatter);

                Article a = new Article(id, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat);
                listeArticles.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listeArticles;
    }

    /**
     * Méthode appelé lors de la recherche d'articles
     * @param requestType
     * @param enchereEnCours
     * @param enchereOuverte
     * @param enchereRemporte
     * @param categorieRecherche
     * @param recherche
     * @param noUtilisateur
     * @return
     */
    @Override
    public List<Article> selectArticleByMultiCriteria(String requestType,  boolean enchereEnCours, boolean enchereOuverte, boolean enchereRemporte, int categorieRecherche, String recherche, int noUtilisateur) {

        //tous les achats
        String queryAchats = " SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo, image_path FROM ARTICLES INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur  WHERE ARTICLES.nom_article like ? ";
        String queryEnchereEnCours = " and ARTICLES.date_debut_encheres <= GETDATE() AND ARTICLES.date_fin_encheres >= GETDATE() ";
        String queryEnchereOuverte = " SELECT no_article, nom_article, description, date_debut_encheres, " +
                "date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo, image_path FROM ARTICLES INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur  WHERE ARTICLES.nom_article like ? and ARTICLES.date_debut_encheres > GETDATE()  ";
        String queryUnion = " UNION ";
        String queryEnchereRemporte = "select DISTINCT ARTICLES.no_article, nom_article, description, date_debut_encheres, " +
                "date_fin_encheres , prix_initial, prix_vente, ARTICLES.no_utilisateur, no_categorie, u.pseudo, image_path from " +
                "ARTICLES inner join ENCHERES E on ARTICLES.no_article = E.no_article INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur WHERE date_fin_encheres < GETDATE()  AND  " +
                "ARTICLES.nom_article like ? AND E.no_utilisateur = ? and ARTICLES.no_article in\n" +
                "(select TOP(1)ENCHERES.no_article\n" +
                "from ENCHERES\n" +
                "inner join ARTICLES A on ENCHERES.no_article = A.no_article\n" +
                "where ENCHERES.no_utilisateur =?\n" +
                "and date_fin_encheres < GETDATE()\n" +
                "order by date_enchere DESC)";

        //mes ventes
        String queryMesVentes = " SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES.no_utilisateur, " +
                "no_categorie, u.pseudo, image_path FROM ARTICLES INNER JOIN UTILISATEURS U on U.no_utilisateur = ARTICLES.no_utilisateur  WHERE ARTICLES.nom_article like ? AND " +
                "ARTICLES.no_utilisateur = ? ";
        String queryMesVentesEnCours = " and ARTICLES.date_debut_encheres <= GETDATE() AND ARTICLES.date_fin_encheres >= GETDATE() ";
        String queryMesVentesOuverte = " and ARTICLES.date_debut_encheres > GETDATE() ";
        String queryUtilisateur = "  AND ARTICLES.no_utilisateur = ? ";

        String queryCategorieRecherche = " and ARTICLES.no_categorie = ? ";

        List<Article> listeArticles = new ArrayList<>();

        StringBuffer sb = null;

        switch (requestType) {
            case "disconnected" :
                sb = new StringBuffer(queryAchats);
                sb.append(queryEnchereEnCours);
                if(categorieRecherche > 0) {
                    sb.append(queryCategorieRecherche);
                }
                sb.append(queryUnion);
                sb.append(queryEnchereOuverte);
                if(categorieRecherche > 0) {
                    sb.append(queryCategorieRecherche);
                }
                break;
            case "true": //radio button des achats en cours
                if(enchereOuverte && !enchereEnCours && !enchereRemporte) {//
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryEnchereOuverte);
                        sb.append(queryCategorieRecherche);
                    }else{
                        sb = new StringBuffer(queryEnchereOuverte);
                    }

                }
                if(enchereOuverte && enchereEnCours && !enchereRemporte) {//
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereOuverte);
                        sb.append(queryCategorieRecherche);
                    }else{
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryUnion);
                        sb.append(queryEnchereOuverte);
                    }

                }
                if(enchereOuverte && enchereEnCours && enchereRemporte) {
                    if(categorieRecherche > 0 ){
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereOuverte);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                        sb.append(queryCategorieRecherche);
                    }else {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryUnion);
                        sb.append(queryEnchereOuverte);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                    }

                }
                if(!enchereOuverte && enchereEnCours && !enchereRemporte) {
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryCategorieRecherche);
                    } else {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                    }
                }
                if(!enchereOuverte && enchereEnCours && enchereRemporte) {
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                        sb.append(queryCategorieRecherche);
                    } else {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryEnchereEnCours);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                    }

                }
                if(!enchereOuverte && !enchereEnCours && enchereRemporte) {
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryEnchereRemporte);
                        sb.append(queryCategorieRecherche);
                    } else {
                        sb = new StringBuffer(queryEnchereRemporte);
                    }

                }
                if(!enchereOuverte && !enchereEnCours && !enchereRemporte) {
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryCategorieRecherche);
                    }else {
                        sb = new StringBuffer(queryAchats);
                    }

                }
                if(enchereOuverte && !enchereEnCours && enchereRemporte) {
                    if(categorieRecherche > 0) {
                        sb = new StringBuffer(queryAchats);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereOuverte);
                        sb.append(queryCategorieRecherche);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                        sb.append(queryCategorieRecherche);
                    } else {
                        sb = new StringBuffer(queryEnchereOuverte);
                        sb.append(queryUnion);
                        sb.append(queryEnchereRemporte);
                    }

                }
//                sb = new StringBuffer(queryAchats);
//                if(enchereEnCours) {
//                    sb.append(queryEnchereEnCours);
//                    if(categorieRecherche > 0) {
//                        sb.append(queryCategorieRecherche);
//                    }
//                }
//                if(categorieRecherche > 0) {
//                    sb.append(queryCategorieRecherche);
//                }
//
//                if(enchereOuverte) {
//                    sb.append(queryUnion);
//                    sb.append(queryEnchereOuverte);
//                }
//                if(enchereOuverte && !enchereEnCours && !enchereRemporte) {
//                    sb = new StringBuffer(queryEnchereOuverte);
//                }
//                if(!enchereOuverte && !enchereEnCours && enchereRemporte) {
//                    sb = new StringBuffer(queryEnchereOuverte);
//                }
//                if(categorieRecherche > 0) {
//                    sb.append(queryCategorieRecherche);
//                }
                break;
            case "false" :
                // TODO il existe des erreurs dans les récupérations des article  mes ventes
                sb = new StringBuffer(queryMesVentes);

                if(enchereEnCours) {
                    sb.append(queryMesVentesEnCours);
                }
                if(enchereOuverte) {
                    sb.append(queryMesVentesOuverte);
                }
                if(categorieRecherche > 0) {
                    sb.append(queryCategorieRecherche);
                }
                break;
        }
        System.out.println(sb.toString());
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(sb.toString())
             ) {

            //dans tous les cas on mets le libellé
//            pstmt.setString(1, "%" + recherche + "%");

            switch (requestType) {
                case "disconnected" :
                    pstmt.setString(1, "%" + recherche + "%");
                    if(categorieRecherche > 0) {
                        pstmt.setInt(2, categorieRecherche);
                        pstmt.setString(3, "%" + recherche + "%");
                        pstmt.setInt(4, categorieRecherche);
                    } else {
                        pstmt.setString(2, "%" + recherche + "%");
                    }
                    break;
                case "true" :

                    if(enchereOuverte && !enchereEnCours && !enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                        }else{
                            pstmt.setString(1, "%" + recherche + "%");
                        }

                    }
                    if(enchereOuverte && enchereEnCours && !enchereRemporte) {//
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                            pstmt.setString(3, "%" + recherche + "%");
                            pstmt.setInt(4, categorieRecherche);
                        }else{
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setString(2, "%" + recherche + "%");
                        }

                    }
                    if(enchereOuverte && enchereEnCours && enchereRemporte) {
                        if(categorieRecherche > 0 ){
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                            pstmt.setString(3, "%" + recherche + "%");
                            pstmt.setInt(4, categorieRecherche);
                            pstmt.setString(5, "%" + recherche + "%");
                            pstmt.setInt(6, noUtilisateur);
                            pstmt.setInt(7, noUtilisateur);
                            pstmt.setInt(8, categorieRecherche);
                        }else {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setString(2, "%" + recherche + "%");
                            pstmt.setString(3, "%" + recherche + "%");
                            pstmt.setInt(4, noUtilisateur);
                            pstmt.setInt(5, noUtilisateur);
                        }

                    }
                    if(!enchereOuverte && enchereEnCours && !enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                        } else {
                            pstmt.setString(1, "%" + recherche + "%");
                        }
                    }
                    if(!enchereOuverte && enchereEnCours && enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                            pstmt.setString(3, "%" + recherche + "%");
                            pstmt.setInt(4, noUtilisateur);
                            pstmt.setInt(5, noUtilisateur);
                            pstmt.setInt(6, categorieRecherche);
                        } else {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setString(2, "%" + recherche + "%");
                            pstmt.setInt(3, noUtilisateur);
                        }

                    }
                    if(!enchereOuverte && !enchereEnCours && enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, noUtilisateur);
                            pstmt.setInt(3, noUtilisateur);
                            pstmt.setInt(4, categorieRecherche);
                        } else {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, noUtilisateur);
                            pstmt.setInt(3, noUtilisateur);
                        }

                    }
                    if(!enchereOuverte && !enchereEnCours && !enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                        }else {
                            pstmt.setString(1, "%" + recherche + "%");
                        }

                    }
                    if(enchereOuverte && !enchereEnCours && enchereRemporte) {
                        if(categorieRecherche > 0) {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setInt(2, categorieRecherche);
                            pstmt.setString(3, "%" + recherche + "%");
                            pstmt.setInt(4, categorieRecherche);
                            pstmt.setString(5, "%" + recherche + "%");
                            pstmt.setInt(6, noUtilisateur);
                            pstmt.setInt(7, noUtilisateur);
                            pstmt.setInt(8, categorieRecherche);
                        } else {
                            pstmt.setString(1, "%" + recherche + "%");
                            pstmt.setString(2, "%" + recherche + "%");
                            pstmt.setInt(3, noUtilisateur);
                            pstmt.setInt(4, noUtilisateur);
                        }

                    }

                    break;
                case "false" :
                    if(categorieRecherche > 0) {
                        pstmt.setInt(2, categorieRecherche);
                    }
                    pstmt.setInt(3, noUtilisateur);
                    break;
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String nomArticle = rs.getString(2);
                String description = rs.getString(3);
                LocalDate dateDebut = rs.getDate(4).toLocalDate();
                LocalDate dateFin = rs.getDate(5).toLocalDate();
                int prixInitial = rs.getInt(6);
                int prixVente = rs.getInt(7);
                int numUtilisateur = rs.getInt(8);
                int numCategorie = rs.getInt(9);
                String pseudo = rs.getString(10);
                String image_name = rs.getString(11);

                LocalDate dateDuJour = LocalDate.now();
                Utilisateur utilisateur = new Utilisateur(numUtilisateur, pseudo);
                Categorie categorie = new Categorie(numCategorie);

                int comparaisonDateDebut = dateDuJour.compareTo(dateDebut);
                int comparaisonDateFin = dateDuJour.compareTo(dateFin);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateDebutFormat = dateDebut.format(formatter);
                String dateFinFormat = dateFin.format(formatter);

                Article a = new Article(id, nomArticle, description, prixInitial, prixVente, utilisateur, categorie, dateDebutFormat, dateFinFormat, comparaisonDateDebut, comparaisonDateFin);
                a.setImageName(image_name);
                a.setDateDebutEncheres(dateDebut);
                a.setDateFinEncheres(dateFin);
                listeArticles.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listeArticles;
    }

}
