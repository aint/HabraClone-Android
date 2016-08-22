package com.github.aint.habraclone.android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.aint.habraclone.android.model.Comment;

import java.util.List;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    private final List<Comment> comments;
    private final LayoutInflater inflater;

    private int position;
    private View convertView;

    public CommentArrayAdapter(Context context, List<Comment> comments) {
        super(context, R.layout.listview_comment_layout, comments);
        this.comments = comments;

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
        ((TextView) convertView.findViewById(R.id.comment_author_label)).setText(comments.get(position).getAuthor());
        ((TextView) convertView.findViewById(R.id.comment_date_label)).setText(String.valueOf(comments.get(position).getCreationDate()));

        setCommentRating();

        ((TextView) convertView.findViewById(R.id.comment_body_label)).setText(comments.get(position).getBody());
    }

    private void setCommentRating() {
        TextView ratingTextView = (TextView) convertView.findViewById(R.id.comment_rating_label);
        int rating = comments.get(position).getRating();
        ratingTextView.setText(rating >= 0 ? "+" + rating : "-" + rating);
        ratingTextView.setTextColor(rating >= 0 ? Color.GREEN : Color.RED);
    }

}