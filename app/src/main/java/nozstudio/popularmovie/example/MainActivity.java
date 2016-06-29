package nozstudio.popularmovie.example;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nozstudio.popularmovie.example.adapter.MoviesAdapter;
import nozstudio.popularmovie.example.model.MovieItem;

public class MainActivity extends AppCompatActivity {

    final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/popular?";
    final String API_KEY = "796b78a940cdb3ba4ac81d7d423b34a6" ;

    List<MovieItem> nMovies = new ArrayList<>();
    MoviesAdapter moviesAdapter;
    private GridView gvMovies;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

        View tvView = findViewById(R.id.tvEmptyView);

        gvMovies = (GridView) findViewById(R.id.gvMovies);
        gvMovies.setEmptyView(tvView);
        moviesAdapter = new MoviesAdapter(this, nMovies);
        gvMovies.setAdapter(moviesAdapter);

        fetchDataFromServer();
    }

    private void fetchDataFromServer(){
            Uri uri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .build();

        System.out.println("URI " + uri.toString());
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                uri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<MovieItem> result = parseJson(response);
                        System.out.println("DATA " + response.toString());
                        if(result != null){
                            moviesAdapter.clear();
                            moviesAdapter.addAll(result);
                            moviesAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(stringRequest);
    }

    private List<MovieItem> parseJson(String response){
        final String TMDB_RESULT = "results";
        final String TMDB_URL_IMAGE = "poster_path";
        final String TMDB_VOTE = "vote_average";
        List<MovieItem> resultMovies = new ArrayList<>();

        try{
            JSONObject objResponse = new JSONObject(response);
            JSONArray resultArray = objResponse.getJSONArray(TMDB_RESULT);
            int arraySize = resultArray.length();
            for(int i = 0; i <arraySize; i++){
                JSONObject movieObject = resultArray.getJSONObject(i);
                String urlImage = movieObject.getString(TMDB_URL_IMAGE);
                System.out.println("URL " + urlImage);
                double voteMovies = movieObject.getDouble(TMDB_VOTE);

                MovieItem movieItem = new MovieItem(urlImage, voteMovies);
                resultMovies.add(movieItem);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return resultMovies;
    }
}
