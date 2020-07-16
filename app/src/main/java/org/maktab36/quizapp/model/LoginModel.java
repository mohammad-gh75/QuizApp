package org.maktab36.quizapp.model;

import java.io.Serializable;

public class LoginModel implements Serializable {
    private String mUsername;
    private String mPassword;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginModel that = (LoginModel) o;
        return mUsername.equals(that.mUsername) &&
                mPassword.equals(that.mPassword);
    }
}
