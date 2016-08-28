package com.github.aint.habraclone.android.rest.service;

import com.github.aint.habraclone.android.rest.model.Article;
import com.github.aint.habraclone.android.rest.model.Comment;
import com.github.aint.habraclone.android.rest.model.Token;
import com.github.aint.habraclone.android.rest.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HabraCloneService {

    @GET("articles/{id}")
    Call<Article> getArticleById(@Path("id") Long id);

    @GET("articles/{id}/comments")
    Call<List<Comment>> getCommentsOfArticle(@Path("id") Long articleId);

    @GET("articles/top")
    Call<List<Article>> getTopArticles();

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("authenticate")
    Call<Token> authenticate(@Query("username") String username, @Query("password") String password);
}
