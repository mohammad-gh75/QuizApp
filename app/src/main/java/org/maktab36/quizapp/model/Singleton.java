package org.maktab36.quizapp.model;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton INSTANCE = null;
    private static List<LoginModel> users = new ArrayList<>();
    private LoginModel mCurrentUser;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return (INSTANCE);
    }

    public void addUser(LoginModel user) {
        if (!containUser(user)) {
            users.add(user);
        }
    }

    public boolean containUser(LoginModel user) {
        return users.contains(user);
    }

    public LoginModel getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(LoginModel currentUser) {
        mCurrentUser = currentUser;
    }
}

