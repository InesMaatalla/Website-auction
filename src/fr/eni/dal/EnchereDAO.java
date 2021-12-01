package fr.eni.dal;

import fr.eni.bo.Enchere;

import java.sql.SQLException;
import java.util.List;

public interface EnchereDAO {

   Enchere insert(Enchere e) throws SQLException;
   Enchere selectById(int id);
   Enchere selectEnchereByPrixVente(int prixVente);
   int selectMontantEnchere(int noArticle, int noUtilisateur);
   List<Enchere> getLastEnchere(int idArticle, int nbRow);

}
