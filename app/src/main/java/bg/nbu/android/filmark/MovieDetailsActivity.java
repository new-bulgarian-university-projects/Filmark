package bg.nbu.android.filmark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MovieDetailsActivity extends AppCompatActivity {

    public final static String MOVIE_ID_PROP = "imdbID";
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String movieId = intent.getStringExtra(MOVIE_ID_PROP);

        String url = String.format(getString(R.string.details_imdb_url), movieId);
        try {
            String json = new DataManager().execute(url).get();
            JSONObject jsonObject = new JSONObject(json);

            asssignMovieDetails(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void asssignMovieDetails(JSONObject jsonObject) throws JSONException {
        String title = jsonObject.getString("Title").toString();
        String year = jsonObject.getString("Year").toString();
        String plot = jsonObject.getString("Plot").toString();
        String posterUrl = jsonObject.getString("Poster").toString();

        new DownloadImageTask((ImageView) findViewById(R.id.movie_poster))
                                            .execute(posterUrl);

        ((TextView) findViewById(R.id.movie_title)).setText(title);
        ((TextView) findViewById(R.id.movie_year)).setText(year);
        ((TextView) findViewById(R.id.movie_plot)).setText(plot);

    }

}
