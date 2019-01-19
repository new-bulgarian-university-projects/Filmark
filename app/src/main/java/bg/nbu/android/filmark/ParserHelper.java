package bg.nbu.android.filmark;

import android.database.Cursor;

import java.util.ArrayList;

public class ParserHelper {
    public static ArrayList<Movie> parseBookmarkedMovies(Cursor res){
        if(res.getCount() == 0){
            return null;
        }

        ArrayList<Movie> bookmarkedMoviesList = new ArrayList<>();

        while(res.moveToNext()){
            String imdbId = res.getString(1);
            String title = res.getString(2);
            String year = res.getString(3);

            Movie bookmarkedMovie = new Movie(imdbId, title,year);
            bookmarkedMoviesList.add(bookmarkedMovie);
        }

        return  bookmarkedMoviesList;
    }
}
