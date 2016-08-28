package com.github.aint.habraclone.android.app;

public class App extends android.app.Application {

    private static String token = "";

    private static boolean isAuthorized() {
        return (token != null && !token.isEmpty());
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        App.token = token;
    }
}
