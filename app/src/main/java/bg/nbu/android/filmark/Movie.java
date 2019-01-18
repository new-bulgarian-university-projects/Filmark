package bg.nbu.android.filmark;

public class Movie {
    private String id;
    private String title;
    private String year;
    private Double rating;

    public Movie(String id, String title, String year){
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String getId() {return this.id;}

    public String getTitle() {return this.title;}

    public String getYear() {return this.year; }

    public Double getRating() {return this.rating; }
    @Override
    public String toString() {
        return this.title + " | " + this.year;
    }
}
