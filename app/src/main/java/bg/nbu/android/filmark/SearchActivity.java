package bg.nbu.android.filmark;
import android.os.AsyncTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {

    public ArrayAdapter<Movie> adapterMovies;
    private ArrayList<Movie> moviesSearchResult;
    String JSON_URL;
    String JSON_STRING;

    public void searchOmdb(View view) throws InterruptedException, ExecutionException, JSONException {
        // get text from button
        JSON_URL = getString(R.string.omdb_api_url);

        TextView searchFiled = (TextView) findViewById(R.id.search_result);
        String searchTitle = searchFiled.getText().toString();
        if (searchTitle != null) {
//            new BackgroundTask().execute(searchTitle);
            String responseMsg = test(searchTitle);
            test2(responseMsg);

        }



    }
    public String test (String title) throws JSONException, ExecutionException, InterruptedException {
        String parsedUrl = String.format(JSON_URL, title);
        String json = new DataManager().execute(parsedUrl).get();
        JSONObject jsonObject = new JSONObject(json);

        moviesSearchResult = new ArrayList<Movie>() {
        };
        if (!jsonObject.getBoolean("Response")) {
            return jsonObject.getString("Error");
        }
        JSONArray moviesJsonList = jsonObject.getJSONArray("Search");

        for (int i = 0; i < moviesJsonList.length(); i++) {
            JSONObject movieJson = (JSONObject) moviesJsonList.get(i);
            Movie movie = new Movie(
                    movieJson.getString("imdbID"),
                    movieJson.getString("Title"),
                    movieJson.getString("Year"));

            moviesSearchResult.add(movie);
        }

        return jsonObject.getJSONArray("Search").length() + "";

    }

    public void test2(String responseMessage){
        TextView statusMessage = (TextView) findViewById(R.id.error_message);
        if(moviesSearchResult.size() > 0) {
            responseMessage = "Found " + responseMessage + " results !";
        }
        statusMessage.setText(responseMessage);

        final ListView listMovies = (ListView) findViewById(R.id.result_list);

        adapterMovies  = new ArrayAdapter<Movie>(SearchActivity.this , android.R.layout.simple_list_item_1, moviesSearchResult);

        listMovies.setAdapter(adapterMovies);
        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // will see what will do
                String movieId = moviesSearchResult.get(position).getId().toString();

                Intent intent = new Intent(SearchActivity.this, MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.MOVIE_ID_PROP, movieId);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
