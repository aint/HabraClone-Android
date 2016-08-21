package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.github.aint.habraclone.android.ArticleArrayAdapter;
import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.model.Article;
import com.github.aint.habraclone.android.service.HabraCloneService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final String HABRA_CLONE_API_URL = "http://192.168.0.100:9090/api/";

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .baseUrl(HABRA_CLONE_API_URL)
            .build();

    private HabraCloneService habraCloneService = retrofit.create(HabraCloneService.class);

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();

        listView = ((ListView) findViewById(R.id.article_list_view));

        setUpListView();
    }

    private void setUpListView() {
        habraCloneService.getTopArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful()) {
                    listView.setAdapter(new ArticleArrayAdapter(MainActivity.this, response.body()));
                } else {
                    Toast.makeText(MainActivity.this, OOPS_ERROR_TOAST + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(MainActivity.this, OOPS_ERROR_TOAST + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(
                LayoutInflater.from(this).inflate(R.layout.actionbar, null),
                new ActionBar.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        );
    }

}
