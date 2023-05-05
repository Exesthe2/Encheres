package bo;

public class Image {
    private int no_image;
    private int no_article;
    private String image;

    public Image() {
    }

    public Image(int no_article, String image) {
        this.no_article = no_article;
        this.image = image;
    }

    public Image(int no_image, int no_article, String image) {
        this.no_image = no_image;
        this.no_article = no_article;
        this.image = image;
    }

    public int getNo_image() {
        return no_image;
    }

    public void setNo_image(int no_image) {
        this.no_image = no_image;
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
