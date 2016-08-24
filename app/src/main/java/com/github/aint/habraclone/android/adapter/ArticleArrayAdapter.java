package com.github.aint.habraclone.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.activity.DisplayCommentsActivity;
import com.github.aint.habraclone.android.model.Article;

import java.util.Date;
import java.util.List;

import static com.github.aint.habraclone.android.activity.DisplayArticleActivity.ARTICLE_ID_ATTRIBUTE;
import static com.github.aint.habraclone.android.activity.MainActivity.DATE_PATTERN;

public class ArticleArrayAdapter extends ArrayAdapter<Article> implements View.OnClickListener {

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
        }
        this.position = position;
        this.convertView = convertView;

        setUpArticleView();

        return convertView;
    }

    private void setUpArticleView() {
        setCreationDate();
        setArticleRating();

        ((TextView) convertView.findViewById(R.id.article_author_label)).setText(articles.get(position).getAuthorUsername());
        ((TextView) convertView.findViewById(R.id.article_title_label)).setText(articles.get(position).getTitle());
        ((TextView) convertView.findViewById(R.id.article_hub_label)).setText(articles.get(position).getHubName());
        ((TextView) convertView.findViewById(R.id.article_views_label)).setText(String.valueOf(articles.get(position).getViews()));
        setUpCommentsView();
    }

    private void setUpCommentsView() {
        TextView commentsTextView = (TextView) convertView.findViewById(R.id.article_comments_label);
        commentsTextView.setText(String.valueOf(articles.get(position).getCommentCount()));
        commentsTextView.setTag(position);
        commentsTextView.setOnClickListener(this);
    }

    private void setCreationDate() {
        ((TextView) convertView.findViewById(R.id.article_date_label)).setText(
                DateFormat.format(DATE_PATTERN, new Date(articles.get(position).getCreationDate())));
    }

    private void setArticleRating() {
        TextView ratingTextView = (TextView) convertView.findViewById(R.id.article_rating_label);
        int rating = articles.get(position).getRating();
        ratingTextView.setText(rating > 0 ? "+" + rating : "" + rating);
        ratingTextView.setTextColor(rating >= 0 ? (rating == 0 ? Color.LTGRAY : Color.GREEN) : Color.RED);
    }

    @Override
    public void onClick(View v) {
        getContext().startActivity(new Intent(getContext(), DisplayCommentsActivity.class)
                .putExtra(ARTICLE_ID_ATTRIBUTE, articles.get((int) v.getTag()).getId()));
    }
}
