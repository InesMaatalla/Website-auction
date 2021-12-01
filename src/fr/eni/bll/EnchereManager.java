package fr.eni.bll;

import fr.eni.bo.*;
import fr.eni.dal.*;

import java.sql.SQLException;
import java.util.List;

public class EnchereManager {

    private final EnchereDAO eDAO;
    private final UtilisateurDAO uDAO;
    private final CategorieDAO cDAO;
    private final ArticleDAO aDAO;
    private final RetraitDAO rDAO;
    private final AuthentificationUtilisateursDAO auDAO;

    public EnchereManager() {
        this.eDAO = DAOFactory.getEnchereDAO();
        this.uDAO = DAOFactory.getUtilisateurDAO();
        this.cDAO = DAOFactory.getCategorieDAO();
        this.aDAO = DAOFactory.getArticleDAO();
        this.rDAO = DAOFactory.getRetraitDAO();
        this.auDAO = DAOFactory.getAuthentificationUtilisateursDAO();
    }

    // Utilisateur
    public Utilisateur select(int id) {
        return this.uDAO.select(id);
    }

    public Utilisateur insertUser(Utilisateur u) {
        return this.uDAO.insertUser(u);
    }

    public Utilisateur selectById(int id) {
        return this.uDAO.selectById(id);
    }

    public Utilisateur selectIdAdresseByIdUer(int id) {
        return uDAO.selectIdAdresseByIdUer(id);
    }
    public void updateCredit(Utilisateur user) {
        this.uDAO.updateCredit(user);
    }

    // Categorie
    public List<Categorie> selectAllCategories() {
        return cDAO.selectAllCategories();
    }

    // Article
    public List<Article> selectAllArticles() {
        return aDAO.selectAllArticles();
    }
    public Article selectArticleById(int id) {
        return aDAO.selectArticleById(id);}
    public Article insertArticle(Article a, int idUtilisateur, int idCategorie) {
        return aDAO.insertArticle(a, idUtilisateur, idCategorie);
    }
    public List<Article> selectArticlesByIdCategories(int idCat){
        return aDAO.selectArticlesByIdCategories(idCat);
    }
    public List<Article> selectArticlesByRecherche(String valeurRecherche) {
        return aDAO.selectArticlesByRecherche(valeurRecherche);
    }

    public List<Article> selectArticlesByIdUtilisateur(int idUtilisateur) {
        return aDAO.selectArticlesByIdUtilisateur(idUtilisateur);
    }

    public void updateArticle(Article article) {
        this.aDAO.updateArticle(article);
    }

    // Enchère
    public void insert(Enchere enchere) throws SQLException {
        eDAO.insert(enchere);
    }

    public int selectMontantEnchere(int noArticle, int noUtilisateur) {
        return eDAO.selectMontantEnchere(noArticle, noUtilisateur);
    }

    public Enchere encherir(Enchere enchere) throws SQLException {
        return eDAO.insert(enchere);

    }

    public Enchere selectEnchereByPrixVente(int prixVente) {
        return eDAO.selectEnchereByPrixVente(prixVente);
    }

    public List<Enchere> getLastEnchere(int idArticle, int nbRow) {
        return this.eDAO.getLastEnchere(idArticle, nbRow);
    }

    /**
     * Vérification que l'email et le mot de passe saisie par l'utilisateur existe en base de données
     * @param email
     * @param mdp
     * @return l'utilisateur ou null s'il existe pas en bdd
     */
    public Utilisateur checkLogin(String email, String mdp) {
        return this.uDAO.checkLogin(email, mdp);
    }

    // Retrait
    public Retrait selectRetraitByIdArticle(int idArticle) {
        return this.rDAO.selectById(idArticle);
    }
    public void insertRetraitById(int idArticle, int idAdresse){
        this.rDAO.insertRetraitById(idArticle, idAdresse);
    }



    // Adresse
    public Adresse selectAdresseByIdUtilisateur(int id) {
        return this.uDAO.selectAdresseByIdUtilisateur(id);
    }
    public Adresse insert(Adresse a) {
        return uDAO.insertUserAdress(a);
    }
//    public String initPasswordReset(Utilisateur user) {
//
//    }


    public void deleteUser(int idUser) {
        this.uDAO.delete(idUser);
    }

    public boolean isMailExist(String email) {

        return this.uDAO.isMailExist(email);

    }

    public boolean isPseudoExist(String pseudo) {
        return this.uDAO.isPseudoExist(pseudo);
    }

    public void insertAuthToken(AuthentificationUtilisateur auth) {
        this.auDAO.insert(auth);
    }

    public AuthentificationUtilisateur findAuthTokenBySelector(String selector) {
        return this.auDAO.selectBySelector(selector);
    }

    public void updateAuthToken(AuthentificationUtilisateur auth) {
        this.auDAO.update(auth);
    }

    public void updateUser(Utilisateur user) {
        this.uDAO.update(user);
    }

    public void deleteAuthToken(String selector) {
        this.auDAO.delete(selector);
    }

    public List<Article> selectArticlebyMultiCriteria(String requestType, boolean checkEnCours, boolean checkOuverte, boolean checkRemportees, int idCat, String valeurRecherche, int noUtilisateur) {
        return this.aDAO.selectArticleByMultiCriteria(requestType, checkEnCours, checkOuverte, checkRemportees, idCat, valeurRecherche, noUtilisateur);
    }
}
