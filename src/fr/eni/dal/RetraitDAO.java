package fr.eni.dal;


import fr.eni.bo.Retrait;

import java.util.List;

public interface RetraitDAO {
    List<Retrait> selectAll();
    Retrait selectById(int id);
    void insertRetraitById(int idArticle, int idAdresse);
}
