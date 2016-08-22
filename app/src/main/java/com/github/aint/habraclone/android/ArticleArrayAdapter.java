package com.github.aint.habraclone.android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.aint.habraclone.android.model.Article;

import java.util.List;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    private final List<Article> articles;
    private final LayoutInflater inflater;

    private int position;
    private View convertView;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, R.layout.listview_article_layout, articles);
        this.articles = articles;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_article_layout, parent, false);

            this.position = position;
            this.convertView = convertView;

            setUpArticleView();
        }
        return convertView;
    }

    private void setUpArticleView() {
        ((TextView) convertView.findViewById(R.id.article_date_label)).setText(String.valueOf(articles.get(position).getCreationDate()));
        ((TextView) convertView.findViewById(R.id.article_author_label)).setText(articles.get(position).getAuthorUsername());

        ((TextView) convertView.findViewById(R.id.article_title_label)).setText(articles.get(position).getTitle());
        ((TextView) convertView.findViewById(R.id.article_hub_label)).setText(articles.get(position).getHubName());

        setArticleRating();
        ((TextView) convertView.findViewById(R.id.article_views_label)).setText(String.valueOf(articles.get(position).getViews()));
        ((TextView) convertView.findViewById(R.id.article_comments_label)).setText(String.valueOf(articles.get(position).getFavorites()));
    }

    private void setArticleRating() {
        TextView ratingTextView = (TextView) convertView.findViewById(R.id.article_rating_label);
        int rating = articles.get(position).getRating();
        ratingTextView.setText(rating >= 0 ? "+" + rating : "-" + rating);
        ratingTextView.setTextColor(rating >= 0 ? Color.GREEN : Color.RED);
    }

}
