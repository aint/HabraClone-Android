package com.github.aint.habraclone.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.github.aint.habraclone.android.R;
import com.github.aint.habraclone.android.app.Application;
import com.github.aint.habraclone.android.rest.HabraCloneRestClient;
import com.github.aint.habraclone.android.rest.model.Token;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<Token> {

    private static final String TAG = LoginActivity.class.getName();

    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int PASSWORD_MIN_LENGTH = 4;

    private AutoCompleteTextView usernameView;
    private ShowHidePasswordEditText passwordView;
    private View progressView;
    private View loginFormView;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordView = (ShowHidePasswordEditText) findViewById(R.id.password);

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    private void getCredentials() {
        username = usernameView.getText().toString();
        password = passwordView.getText().toString();
    }

    private void resetErrors() {
        usernameView.setError(null);
        passwordView.setError(null);
    }

    private void attemptLogin() {
        resetErrors();
        getCredentials();

        if ((isUsernameValid() && isPasswordValid())) {
            showProgress(true);
            HabraCloneRestClient.getInstance().authenticate(username, password).enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        showProgress(false);
        if (response.isSuccessful()) {
            Application.setToken(response.body().getMessage());
            finish();
        } else if (response.code() == 400) {
            setUsernameError();
        } else if (response.code() == 401) {
            setPasswordError();
        } else {
            Toast.makeText(this, R.string.error_authenticate_toast, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        showProgress(false);
        Log.e(TAG, "Authorization failure: ", t);
        Toast.makeText(this, R.string.error_authenticate_toast, Toast.LENGTH_LONG).show();

    }

    private void setPasswordError() {
        passwordView.setError(getString(R.string.error_incorrect_password));
        passwordView.requestFocus();
    }

    private void setUsernameError() {
        usernameView.setError(getString(R.string.error_incorrect_username));
        usernameView.requestFocus();
    }

    private boolean isUsernameValid() {
        if (TextUtils.isEmpty(username) && username.length() < USERNAME_MIN_LENGTH) {
            usernameView.setError(getString(R.string.error_invalid_username));
            usernameView.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isPasswordValid() {
        if (!TextUtils.isEmpty(password) && password.length() < PASSWORD_MIN_LENGTH) {
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.requestFocus();
            return false;
        }
        return true;
    }

    private void showProgress(boolean show) {
        loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void onSignInButtonClick(View view) {
        attemptLogin();
    }
}

