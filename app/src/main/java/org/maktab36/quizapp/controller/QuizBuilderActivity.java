package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;

import java.util.regex.Pattern;

public class QuizBuilderActivity extends AppCompatActivity {
    private Button mButtonStart;
    private TextView mTextInput;
    public static final String EXTRA_INPUT = "org.maktab36.quizapp.input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);

        findAllViews();
        setClickListener();
    }

    private void findAllViews() {
        mButtonStart = findViewById(R.id.button_start);
        mTextInput = findViewById(R.id.edit_text_input);
    }

    private void setClickListener() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mTextInput.getText().toString();
                if (checkValidation(input)) {
                    Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                    intent.putExtra(EXTRA_INPUT, input);
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
        Pattern p=Pattern.compile("[\\s]*[,]");
        return true;
    }
}
/*
{[{“Tehran in iran”}, {true}, {false}, {green}],
[{“iran language is english”}, {false} {true}, {red}],
[{“England is in usa”}, {false}, {false}, {black}]} ,
 {30}
 */