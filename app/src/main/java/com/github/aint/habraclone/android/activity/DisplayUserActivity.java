package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.rest.HabraCloneRestClient;
import com.github.aint.habraclone.android.rest.model.User;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.aint.habraclone.android.activity.MainActivity.DATE_PATTERN;
import static com.github.aint.habraclone.android.activity.MainActivity.USERNAME_ATTRIBUTE;

public class DisplayUserActivity extends AppCompatActivity implements Callback<User> {

    private static final String TAG = DisplayUserActivity.class.getName();

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        HabraCloneRestClient.getInstance().getUser(getIntent().getStringExtra(USERNAME_ATTRIBUTE)).enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            setUserView(response.body());
        } else {
            Toast.makeText(DisplayUserActivity.this, OOPS_ERROR_TOAST + response.code(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(DisplayUserActivity.this, OOPS_ERROR_TOAST + t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(TAG, t.getMessage());
    }

    private void setUserView(User user) {
        ((TextView) findViewById(R.id.user_karma)).setText(String.valueOf(user.getRating() / 2));
        ((TextView) findViewById(R.id.user_rating)).setText(String.valueOf(user.getRating()));
        ((TextView) findViewById(R.id.user_username)).setText(user.getUsername());
        ((TextView) findViewById(R.id.user_fullname)).setText(user.getFullName());
        ((TextView) findViewById(R.id.user_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.user_reg_date)).setText(
                DateFormat.format(DATE_PATTERN, new Date(user.getRegistrationDate())));
    }
}
