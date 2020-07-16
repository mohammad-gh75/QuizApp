package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.model.LoginModel;
import org.maktab36.quizapp.model.Singleton;

public class SignUpActivity extends AppCompatActivity {
    public static final String EXTRA_SIGN_INFO = "org.maktab36.quizapp.signInfo";
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonSignUp;
    private LoginModel mInfo = new LoginModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findAllViews();
        loadInfo();
        setListeners();
    }

    private void loadInfo() {
        String username = getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME);
        String password = getIntent().getStringExtra(LoginActivity.EXTRA_PASSWORD);
        mEditTextUsername.setText(username);
        mEditTextPassword.setText(password);
    }

    private void findAllViews() {
        mEditTextUsername = findViewById(R.id.editTextSignUpUsername);
        mEditTextPassword = findViewById(R.id.editTextSignUpNumberPassword);
        mButtonSignUp = findViewById(R.id.signUpButtonSignUp);
    }

    private void setListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputValidity()) {
                    saveInfo();
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this,
                            R.string.input_wrong_format, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveInfo() {
        Intent intent = new Intent();
        mInfo.setUsername(mEditTextUsername.getText().toString());
        mInfo.setPassword(mEditTextPassword.getText().toString());
        Singleton.getInstance().addUser(mInfo);
        intent.putExtra(EXTRA_SIGN_INFO, mInfo);
        setResult(RESULT_OK, intent);
    }

    private boolean checkInputValidity() {
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();
        return !username.equals("") && !password.equals("");
    }

}