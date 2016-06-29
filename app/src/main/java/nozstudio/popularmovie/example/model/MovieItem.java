package nozstudio.popularmovie.example.model;

/**
 * Created by CLient-Pc on 29/06/2016.
 */
public class MovieItem {
    private String urlImage;
    private double vote;

    public MovieItem(String urlImage, double vote) {
        this.urlImage = urlImage;
        this.vote = vote;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public MovieItem setUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public double getVote() {
        return vote;
    }

    public MovieItem setVote(double vote) {
        this.vote = vote;
        return this;
    }
}
