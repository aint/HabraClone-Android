package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.model.Article;

public class DisplayArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_article);

        Article article = ((Article) getIntent().getSerializableExtra(MainActivity.ARTICLE_ATTRIBUTE));

        ((TextView) findViewById(R.id.article_date_label)).setText(String.valueOf(article.getCreationDate()));
        ((TextView) findViewById(R.id.article_author_label)).setText(article.getAuthorUsername());

        ((TextView) findViewById(R.id.article_title_label)).setText(article.getTitle());
        ((TextView) findViewById(R.id.article_hub_label)).setText(article.getHubName());

        ((TextView) findViewById(R.id.article_body_label)).setText(article.getBody());

        ((TextView) findViewById(R.id.rating_button)).setText(String.valueOf(article.getRating()));
        ((TextView) findViewById(R.id.views_button)).setText(String.valueOf(article.getViews()));
        ((TextView) findViewById(R.id.comments_button)).setText(String.valueOf(article.getFavorites()));
    }

}
