package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.model.LoginModel;

public class QuizBuilderActivity extends AppCompatActivity {
    private Button mButtonStart;
    private TextView mTextInput;
    private TextView mTextViewUsername;
    private LoginModel mInfo;
    public static final String EXTRA_INPUT = "org.maktab36.quizapp.input";
    public static final String EXTRA_LOGIN_INFO = "org.maktab36.quizapp.Builder.loginInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);

        findAllViews();
        loadInfo();
        setClickListener();
    }

    private void loadInfo() {
        mInfo = (LoginModel) getIntent().getSerializableExtra(LoginActivity.EXTRA_LOGIN_INFO);
        String username = getString(R.string.show_username, mInfo.getUsername());
        mTextViewUsername.setText(username);
    }

    private void findAllViews() {
        mButtonStart = findViewById(R.id.button_start);
        mTextInput = findViewById(R.id.edit_text_input);
        mTextViewUsername = findViewById(R.id.text_view_builder_username);
    }

    private void setClickListener() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mTextInput.getText().toString();
                if (checkValidation(input)) {
                    Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                    intent.putExtra(EXTRA_INPUT, input);
                    intent.putExtra(EXTRA_LOGIN_INFO, mInfo);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(QuizBuilderActivity.this, R.string.input_wrong_format,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkValidation(String input) {
        String regex = "(\\{" +
                "(\\[(\\{“.*”\\})(\\s*,\\s*)((\\{(true|false)\\}(\\s*,\\s*)){2})(\\{(green|red|black|blue)\\})](\\s*,\\s*))+" +
                "(\\[(\\{“.*”\\})(\\s*,\\s*)((\\{(true|false)\\}(\\s*,\\s*)){2})(\\{(green|red|black|blue)\\})])" +
                "\\})" +
                "(\\s*,\\s*)" +
                "(\\{\\d+\\})";

        return input.matches(regex);
    }
}