package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.model.LoginModel;
import org.maktab36.quizapp.model.Singleton;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_LOGIN_ACTIVITY = 2;
    public static final String EXTRA_LOGIN_INFO = "org.maktab36.quizapp.loginInfo";
    public static final String EXTRA_USERNAME = "org.maktab36.quizapp.username";
    public static final String EXTRA_PASSWORD = "org.maktab36.quizapp.password";
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private LoginModel mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findAllViews();
        if (mInfo == null) {
            loadCurrentUserInfo();
        }
        setListeners();
    }

    private void loadCurrentUserInfo() {
        mInfo = (LoginModel) getIntent().getSerializableExtra(QuizActivity.EXTRA_CURRENT_INFO);
        if (mInfo != null) {
            mEditTextUsername.setText(mInfo.getUsername());
            mEditTextPassword.setText(mInfo.getPassword());
        }
    }

    private void findAllViews() {
        mEditTextUsername = findViewById(R.id.editTextLoginUsername);
        mEditTextPassword = findViewById(R.id.editTextLoginNumberPassword);
        mButtonLogin = findViewById(R.id.loginButtonLogin);
        mButtonSignUp = findViewById(R.id.loginButtonSignUp);
    }

    private void setListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInfo()) {
                    showSnackBar(view);
                    new CountDownTimer(5000, 10000) {

                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            startQuizBuilderActivity();
                            finish();
                        }
                    }.start();
                } else {
                    Toast.makeText(LoginActivity.this,
                            R.string.toast_incorrect_info, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUpActivity();
            }
        });
    }

    private boolean checkInfo() {
        return mInfo != null && Singleton.getInstance().containUser(mInfo);
    }

    private void showSnackBar(View view) {
        String username = getString(R.string.snack_bar_welcome, mInfo.getUsername());
        Snackbar snackbar = Snackbar
                .make(view, username, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.DKGRAY)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.show();
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra(EXTRA_USERNAME, mEditTextUsername.getText().toString());
        intent.putExtra(EXTRA_PASSWORD, mEditTextPassword.getText().toString());
        startActivityForResult(intent, REQUEST_CODE_LOGIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            mInfo = (LoginModel) data.getSerializableExtra(SignUpActivity.EXTRA_SIGN_INFO);
            mEditTextUsername.setText(mInfo.getUsername());
            mEditTextPassword.setText(mInfo.getPassword());
        }
    }

    private void startQuizBuilderActivity() {
        Intent intent = new Intent(LoginActivity.this, QuizBuilderActivity.class);
        intent.putExtra(EXTRA_LOGIN_INFO, mInfo);
        startActivity(intent);
    }
}