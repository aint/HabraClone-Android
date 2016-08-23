package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.model.User;
import com.github.aint.habraclone.android.service.HabraCloneService;
import com.google.gson.Gson;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayUserActivity extends AppCompatActivity {

    private static final String TAG = DisplayUserActivity.class.getName();

    private static final String HABRA_CLONE_API_URL = "http://192.168.0.100:9090/api/";

    private static final String OOPS_ERROR_TOAST = "Oops... Error ";

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .baseUrl(HABRA_CLONE_API_URL)
            .build();

    private HabraCloneService habraCloneService = retrofit.create(HabraCloneService.class);

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        username = getIntent().getStringExtra(MainActivity.USERNAME_ATTRIBUTE);

        setUpListView();
    }

    private void setUpListView() {
        habraCloneService.getUser(username).enqueue(new Callback<User>() {
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
        });
    }

    private void setUserView(User user) {
        ((TextView) findViewById(R.id.user_karma)).setText(String.valueOf(user.getRating() / 2));
        ((TextView) findViewById(R.id.user_rating)).setText(String.valueOf(user.getRating()));
        ((TextView) findViewById(R.id.user_username)).setText(user.getUsername());
        ((TextView) findViewById(R.id.user_fullname)).setText(user.getFullName());
        ((TextView) findViewById(R.id.user_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.user_reg_date)).setText(
                DateFormat.format("dd.MM.yyyy hh:mm", new Date(user.getRegistrationDate())));
    }

}