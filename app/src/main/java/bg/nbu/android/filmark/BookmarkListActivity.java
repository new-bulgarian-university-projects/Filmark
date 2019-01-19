package bg.nbu.android.filmark;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class BookmarkListActivity extends AppCompatActivity {

    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);
        this.myDb = new DatabaseHelper(this);
        ListView listBookmarks = (ListView) findViewById(R.id.bookmarked_movies_list);

        Cursor bookmarkedMoviesCursor = myDb.getBookmarkedMovies();
        final List<Movie> bookmarkedList = ParserHelper.parseBookmarkedMovies(bookmarkedMoviesCursor);

        if(bookmarkedList == null){
            return;
        }

        ArrayAdapter<Movie> adapterBookmarks = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, bookmarkedList);
        listBookmarks.setAdapter(adapterBookmarks);

        listBookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookmarkListActivity.this, MovieDetailsActivity.class);
                Movie selectedMovie = bookmarkedList.get(position);
                intent.putExtra(MovieDetailsActivity.MOVIE_ID_PROP, selectedMovie.getId());

                startActivity(intent);
            }
        });
    }
}
