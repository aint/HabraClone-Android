package com.github.aint.habraclone.android.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.adapter.CommentArrayAdapter;
import com.github.aint.habraclone.android.app.App;
import com.github.aint.habraclone.android.rest.HabraCloneRestClient;
import com.github.aint.habraclone.android.rest.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class DisplayCommentsActivity extends AppCompatActivity implements Callback<List<Comment>>, OnClickListener {

    private static final String TAG = DisplayCommentsActivity.class.getName();

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    private static final String BUTTON_OK = "Post";
    private static final String BUTTON_CANCEL = "Cancel";

    private ListView listView;
    private Long articleId;
    private TextView commentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);

        setUpListView();
    }

    private void setUpListView() {
        listView = ((ListView) findViewById(R.id.comment_list_view));
        articleId = getIntent().getLongExtra(DisplayArticleActivity.ARTICLE_ID_ATTRIBUTE, -1);
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
            Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + response.code(), LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + t.getMessage(), LENGTH_LONG).show();
        Log.e(TAG, t.getMessage());
    }

    public void onAddCommentButtonClick(View view) {
        View addCommentView = getLayoutInflater().inflate(R.layout.activity_post_comment, null);
        commentTextView = (TextView) addCommentView.findViewById(R.id.comment_body);
        new AlertDialog.Builder(this)
                .setView(addCommentView)
                .setTitle(getString(R.string.title_add_comment))
                .setIcon(R.drawable.post_comment)
                .setCancelable(false)
                .setPositiveButton(BUTTON_OK, this)
                .setNegativeButton(BUTTON_CANCEL, this)
                .create()
                .show();

    }

    @Override
    public void onClick(final DialogInterface dialog, int which) {
        if (which == -1) {
            String comment = commentTextView.getText().toString();
            HabraCloneRestClient.getInstance().addComment(comment, articleId, App.getToken()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
//                        dialog.dismiss();
                        setUpListView();
                    } else {
                        Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + response.code(), LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(DisplayCommentsActivity.this, OOPS_ERROR_TOAST + t.getMessage(), LENGTH_LONG).show();
                    Log.e(TAG, t.getMessage());
                }
            });
        } else if (which == -2) {
            dialog.dismiss();
        }
    }
}
