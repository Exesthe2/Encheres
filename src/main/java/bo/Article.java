package bo;

import java.time.LocalDateTime;

public class Article {

    private int no_article;
    private String nom;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Integer prixInitial;
    private Integer prixVente;
    private int no_utilisateur;
    private int no_categorie;
    private String etatVente;
    private Enchere enchere;

    public Article() {
    }

    public Article(int no_article, String nom, LocalDateTime dateFin, Integer prixInitial, int no_utilisateur) {
        this.no_article = no_article;
        this.nom = nom;
        this.dateFin = dateFin;
        this.prixInitial = prixInitial;
        this.no_utilisateur = no_utilisateur;
    }

    public Article(int no_article, String nom, String description, LocalDateTime dateDebut, LocalDateTime dateFin, Integer prixInitial, Integer prixVente, int no_utilisateur, int no_categorie, String etatVente, Enchere enchere) {
        this.no_article = no_article;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.no_utilisateur = no_utilisateur;
        this.no_categorie = no_categorie;
        this.etatVente = etatVente;
        this.enchere = enchere;
    }

    public Article(String nom, String description, LocalDateTime dateDebut, LocalDateTime dateFin, Integer prixInitial, Integer prixVente, int no_utilisateur, int no_categorie, String etatVente) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.no_utilisateur = no_utilisateur;
        this.no_categorie = no_categorie;
        this.etatVente = etatVente;
    }

    public Article(int no_article, String nom, String description, LocalDateTime dateDebut, LocalDateTime dateFin, Integer prixInitial, Integer prixVente, int no_utilisateur, int no_categorie, String etatVente) {
        this.no_article = no_article;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.no_utilisateur = no_utilisateur;
        this.no_categorie = no_categorie;
        this.etatVente = etatVente;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(Integer prixInitial) {
        this.prixInitial = prixInitial;
    }

    public Integer getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Integer prixVente) {
        this.prixVente = prixVente;
    }

    public int getNo_utilisateur() {
        return no_utilisateur;
    }

    public void setNo_utilisateur(int no_utilisateur) {
        this.no_utilisateur = no_utilisateur;
    }

    public int getNo_categorie() {
        return no_categorie;
    }

    public void setNo_categorie(int no_categorie) {
        this.no_categorie = no_categorie;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }


    @Override
    public String toString() {
        return "Article{" +
                "no_article=" + no_article +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", no_utilisateur=" + no_utilisateur +
                ", no_categorie=" + no_categorie +
                ", etatVente='" + etatVente + '\'' +
                ", enchere='" + enchere + '\'' +
                '}';
    }
}
