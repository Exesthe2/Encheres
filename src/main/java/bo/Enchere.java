package bo;

import java.time.LocalDateTime;

public class Enchere {

    private int no_enchere;
    private int no_utilisateur;
    private int no_article;
    private LocalDateTime date_enchere;
    private int montant_enchere;

    public Enchere(int no_enchere, int no_utilisateur, int no_article, LocalDateTime date_enchere, int montant_enchere) {
        this.no_enchere = no_enchere;
        this.no_utilisateur = no_utilisateur;
        this.no_article = no_article;
        this.date_enchere = date_enchere;
        this.montant_enchere = montant_enchere;
    }

    public int getNo_enchere() {
        return no_enchere;
    }

    public void setNo_enchere(int no_enchere) {
        this.no_enchere = no_enchere;
    }

    public int getNo_utilisateur() {
        return no_utilisateur;
    }

    public void setNo_utilisateur(int no_utilisateur) {
        this.no_utilisateur = no_utilisateur;
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public LocalDateTime getDate_enchere() {
        return date_enchere;
    }

    public void setDate_enchere(LocalDateTime date_enchere) {
        this.date_enchere = date_enchere;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "no_enchere=" + no_enchere +
                ", no_utilisateur=" + no_utilisateur +
                ", no_article=" + no_article +
                ", date_enchere=" + date_enchere +
                ", montant_enchere=" + montant_enchere +
                '}';
    }
}
