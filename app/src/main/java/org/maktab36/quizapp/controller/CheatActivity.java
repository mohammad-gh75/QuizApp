package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;

public class CheatActivity extends AppCompatActivity {
    private static final String BUNDLE_KEY_CURRENT_ANSWER = "currentAnswer";
    private static final String BUNDLE_KEY_IS_CHEATED = "isCheated";
    public static final String EXTRA_IS_CHEATED = "org.maktab36.quizapp.isCheated";
    private TextView mTextViewAnswer;
    private Button mButtonCheat;
    private Button mButtonCheatBack;
    private boolean mCurrentAnswer;
    private boolean mIsCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        findAllViews();
        loadState(savedInstanceState);
        setClickListener();
        extractAnswer();
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTextViewAnswer.setText(savedInstanceState.getCharSequence(BUNDLE_KEY_CURRENT_ANSWER));
            mIsCheated = savedInstanceState.getBoolean(BUNDLE_KEY_IS_CHEATED, false);
        }
    }

    private void extractAnswer() {
        Intent intent = getIntent();
        mCurrentAnswer = intent.getBooleanExtra(QuizActivity.EXTRA_CURRENT_ANSWER, false);
    }

    private void findAllViews() {
        mTextViewAnswer = findViewById(R.id.text_view_show_answer);
        mButtonCheat = findViewById(R.id.button_show_cheat);
        mButtonCheatBack = findViewById(R.id.button_cheat_back);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(BUNDLE_KEY_CURRENT_ANSWER, mTextViewAnswer.getText());
        outState.putBoolean(BUNDLE_KEY_IS_CHEATED, mIsCheated);
    }

    private void setClickListener() {
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnswer) {
                    mTextViewAnswer.setText(R.string.button_true);
                } else {
                    mTextViewAnswer.setText(R.string.button_false);
                }
                mIsCheated = true;
//                saveResult();
            }
        });

        mButtonCheatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResult();
                finish();
            }
        });
    }

    private void saveResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEATED, mIsCheated);

        setResult(RESULT_OK, intent);
    }
}