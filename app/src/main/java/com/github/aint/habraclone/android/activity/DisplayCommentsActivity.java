package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.adapter.CommentArrayAdapter;
import com.github.aint.habraclone.android.rest.HabraCloneRestClient;
import com.github.aint.habraclone.android.rest.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayCommentsActivity extends AppCompatActivity implements Callback<List<Comment>> {

    private static final String TAG = DisplayCommentsActivity.class.getName();

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);

        setUpListView();
    }

    private void setUpListView() {
        listView = ((ListView) findViewById(R.id.comment_list_view));
        Long articleId = getIntent().getLongExtra(DisplayArticleActivity.ARTICLE_ID_ATTRIBUTE, -1);
        HabraCloneRestClient.getInstance().getCommentsOfArticle(articleId).enqueue(this);
    }

    private void ifNoComments() {
        if (listView.getAdapter().isEmpty()) {
            TextView emptyTextView = ((TextView) findViewById(R.id.empty));
            emptyTextView.setText(getResources().getText(R.string.no_comments_textview));
            listView.setEmptyView(emptyTextView);
        }
    }

    @Override
    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        if (response.isSuccessful()) {
            listView.setAdapter(new CommentArrayAdapter(DisplayCommentsActivity.this, response.body()));
            ifNoComments();
        } else {
            Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + response.code(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(TAG, t.getMessage());
    }

}
