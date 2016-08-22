package com.github.aint.habraclone.android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.model.Article;

public class DisplayArticleActivity extends AppCompatActivity {

    public static final String ARTICLE_ID_ATTRIBUTE = "article_id";

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_article);

        article = ((Article) getIntent().getSerializableExtra(MainActivity.ARTICLE_ATTRIBUTE));

        ((TextView) findViewById(R.id.article_date_label)).setText(String.valueOf(article.getCreationDate()));
        ((TextView) findViewById(R.id.article_author_label)).setText(article.getAuthorUsername());

        ((TextView) findViewById(R.id.article_title_label)).setText(article.getTitle());
        ((TextView) findViewById(R.id.article_hub_label)).setText(article.getHubName());

        ((TextView) findViewById(R.id.article_body_label)).setText(article.getBody());

        setArticleRating();
        ((TextView) findViewById(R.id.views_button)).setText(String.valueOf(article.getViews()));
        ((TextView) findViewById(R.id.comments_button)).setText(String.valueOf(article.getFavorites()));
    }

    private void setArticleRating() {
        Button ratingButton = (Button) findViewById(R.id.rating_button);
        int rating = article.getRating();
        ratingButton.setText(rating >= 0 ? "+" + rating : "-" + rating);
        ratingButton.setTextColor(rating >= 0 ? Color.GREEN : Color.RED);
    }

    public void onCommentsButtonClick(View view) {
        startActivity(new Intent(this, DisplayCommentsActivity.class)
                .putExtra(ARTICLE_ID_ATTRIBUTE, article.getId()));
    }
}
