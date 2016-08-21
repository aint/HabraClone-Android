package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.model.Article;
import com.github.aint.habraclone.android.service.HabraCloneService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String HABRA_CLONE_API_URL = "http://192.168.0.100:9090/api/";
    
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .baseUrl(HABRA_CLONE_API_URL)
            .build();

    private HabraCloneService habraCloneService = retrofit.create(HabraCloneService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habraCloneService.getArticleById(3L).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    ((TextView) findViewById(R.id.text)).setText(response.body().getBody());
                    Log.e(TAG, "onResponse: " + response.body());
                    return;
                }
                ((TextView) findViewById(R.id.text)).setText("Error" + response.code());
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Failure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
