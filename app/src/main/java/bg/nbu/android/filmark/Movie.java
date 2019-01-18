package bg.nbu.android.filmark;

public class Movie {
    private String title;
    private Integer year;
    private Double rating;

    public Movie(String title, Integer year){
        this.title = title;
        this.year = year;
    }

    public String getTitle() {return this.title;}
    public void setTitle(String title){
        if(title != null)
            this.title = title;
    }

    public Integer getYear() {return this.year; }
    public void setYear(Integer year){
        if(year > 0){
            this.year = year;
        }
    }

    public Double getRating() {return this.rating; }
    public void setRating(Double rating){
        if(rating >=0){
            this.rating = rating;
        }
    }
    @Override
    public String toString() {
        return this.title + " | " + this.year;
    }
}
