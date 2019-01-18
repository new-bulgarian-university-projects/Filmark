package bg.nbu.android.filmark;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataManager extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            String urlString = strings[0];
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String json = reader.readLine();

            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
