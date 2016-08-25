package com.github.aint.habraclone.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.adapter.ArticleArrayAdapter;
import com.github.aint.habraclone.android.rest.HabraCloneRestClient;
import com.github.aint.habraclone.android.rest.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, Callback<List<Article>> {

    private static final String TAG = MainActivity.class.getName();

    public static final String DATE_PATTERN = "dd.MM.yyyy hh:mm";
    public static final String ARTICLE_ATTRIBUTE = "article";
    public static final String USERNAME_ATTRIBUTE = "username";

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setUpListView();
    }

    private void setUpListView() {
        listView = ((ListView) findViewById(R.id.article_list_view));
        listView.setOnItemClickListener(this);
        HabraCloneRestClient.getInstance().getTopArticles().enqueue(this);
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, DisplayArticleActivity.class)
                .putExtra(ARTICLE_ATTRIBUTE, (Article) parent.getItemAtPosition(position)));
    }

}
