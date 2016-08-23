package com.github.aint.habraclone.android.service;

import com.github.aint.habraclone.android.model.Article;
import com.github.aint.habraclone.android.model.Comment;
import com.github.aint.habraclone.android.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HabraCloneService {

    @GET("articles/{id}")
    Call<Article> getArticleById(@Path("id") Long id);

    @GET("articles/{id}/comments")
    Call<List<Comment>> getCommentsOfArticle(@Path("id") Long articleId);

    @GET("articles/top")
    Call<List<Article>> getTopArticles();

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
}
