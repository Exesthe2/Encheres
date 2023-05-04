package bll;

import bo.Article;
import bo.Categorie;
import dal.ArticleDAO;
import dal.DAOFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleBLL {

    private CategorieBLL categorieBLL = new CategorieBLL();

    private ArticleDAO dao;

    public ArticleBLL() {
        dao = DAOFactory.getArticleDAO();
    }

    public void insert(Article article) throws BLLException {
        // Recuperer la liste des categories
        List<Categorie> categories = categorieBLL.selectAll();
        String nom = article.getNom();
        String description = article.getDescription();
        LocalDateTime dateDebut = article.getDateDebut();
        LocalDateTime dateFin = article.getDateFin();
        Integer prixInitial = article.getPrixInitial();
        int no_categorie = article.getNo_categorie();
        String image = article.getImage();

        if ("".equals(nom) || "".equals(description) || dateDebut == null || dateFin == null) {
            throw new BLLException("Un des champs obligatoires est vide");
        }
        if (nom.length() > 30) {
            throw new BLLException("le champ nom ne peux pas depasser 30 charactères");
        }
        if (description.length() > 300) {
            throw new BLLException("le champ description ne peux pas depasser 300 charactères");
        }
        if (dateDebut.isBefore(LocalDateTime.now())) {
            throw new BLLException("le champ date de début doit être supérieur à la date et heure actuelle");
        }
        if (dateFin.isBefore(LocalDateTime.now()) || dateFin.isBefore(dateDebut)) {
            throw new BLLException("le champ date de début doit être supérieur à la date de debut et la date et heure actuelle");
        }
        if (prixInitial < 0) {
            throw new BLLException("le champ prix initial n'est pas correct");
        }
        if (no_categorie < 0 || no_categorie > categories.size()) {
            throw new BLLException("le champ catégorie n'est pas correct");
        }
        if (image != null) {
            if (image.length() > 150) {
                throw new BLLException("Le nom de l'image ne doit pas dépasser 150 charactères");
            }
        }

        dao.insert(article);
    }

    public void update(Article article) {
        dao.update(article);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}
