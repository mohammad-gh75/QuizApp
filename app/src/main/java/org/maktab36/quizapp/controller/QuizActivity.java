package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.model.Question;
import org.maktab36.quizapp.model.SettingModel;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "quizApp";
    public static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    public static final String BUNDLE_KEY_SCORE = "score";
    public static final String BUNDLE_KEY_ANSWERED_ARRAY = "answeredArray";
    public static final String BUNDLE_KEY_START_VISIBILITY = "startLayoutVisibility";
    public static final String BUNDLE_KEY_END_VISIBILITY = "endLayoutVisibility";
    public static final String BUNDLE_KEY_CURRENT_TIME = "currentTime";
    public static final String BUNDLE_KEY_CURRENT_SETTING = "currentSetting";
    public static final String EXTRA_CURRENT_ANSWER = "org.maktab36.quizapp.currentAnswer";
    public static final String EXTRA_CURRENT_SETTING = "org.maktab36.quizapp.currentSetting";

    public static final int REQUEST_CODE_CHEAT_ACTIVITY = 0;
    public static final int REQUEST_CODE_SETTING_ACTIVITY = 1;

    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mButtonLast;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonFirst;
    private Button mButtonCheat;
    private Button mButtonSetting;
    private TextView mTextViewQuestion;
    private TextView mTextViewScore;
    private TextView mTextViewEndScore;
    private Button mButtonTryAgain;
    private ViewGroup mEndLayout;
    private ViewGroup mStartLayout;
    private ViewGroup mRootLayout;
    private int mScore = 0;
    private int mCurrentIndex = 0;
    private int mTimeOut;
    private int mCurrentTime;
    private TextView mTextViewTimer;
    private CountDownTimer mCountTime;
    private SettingModel mModel;
    /*private Question[] mQuestionBanks = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };*/
    private Question[] mQuestionBanks;
    private boolean[] mAnsweredArray;
    private int mPositiveGrade = 1;
    private int mNegativeGrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        findAllViews();
        mQuestionBanks = parseQuestions(getIntent().getStringExtra(QuizBuilderActivity.EXTRA_INPUT));
        mAnsweredArray = new boolean[mQuestionBanks.length];
        loadState(savedInstanceState);
        updateQuestion();
        setClickListener();
        startTimer(mTimeOut);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT_ACTIVITY) {
            mQuestionBanks[mCurrentIndex].
                    setCheated(data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEATED,
                            false));

        }
        if (requestCode == REQUEST_CODE_SETTING_ACTIVITY) {
            mModel = (SettingModel) data.getSerializableExtra(SettingActivity.EXTRA_SETTING);
            setSettings();
        }
    }

    private void setSettings() {
        setBackgroundColor();

        mTextViewQuestion.setTextSize(mModel.getQuestionSize());

        setTimeOutEnabling();
        setButtonsVisibility();

        setGrades();
    }

    private void setGrades() {
        mPositiveGrade = mModel.getPositiveGrade();
        mNegativeGrade = mModel.getNegativeGrade();
    }

    private void setTimeOutEnabling() {
        if (mModel.isTimeOutEnable()) {
            mCountTime.cancel();
            mTextViewTimer.setVisibility(View.VISIBLE);
            startTimer(mCurrentTime);
        } else {
            mCountTime.cancel();
            mTextViewTimer.setVisibility(View.INVISIBLE);
        }
    }

    private void setBackgroundColor() {
        mRootLayout.setBackgroundColor(getResources().getColor(mModel.getBackgroundColorId()));
    }

    @Override
    public void onBackPressed() {
        if (mModel != null) {
            if (mModel.isBackButtonEnable()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void setButtonsVisibility() {
        mButtonTrue.setVisibility(mModel.getButtonTrueVisibility());
        mButtonFalse.setVisibility(mModel.getButtonFalseVisibility());
        mButtonNext.setVisibility(mModel.getButtonNextVisibility());
        mButtonPrevious.setVisibility(mModel.getButtonPreviousVisibility());
        mButtonLast.setVisibility(mModel.getButtonLastVisibility());
        mButtonFirst.setVisibility(mModel.getButtonFirstVisibility());
        mButtonCheat.setVisibility(mModel.getButtonCheatVisibility());
    }

    private void startTimer(int timeOut) {
        mCountTime = new CountDownTimer(timeOut * 1000, 1000) {

            @Override
            public void onTick(long l) {
                mTextViewTimer.setText(getString(R.string.show_timer, l / 1000));
                mCurrentTime = (int) (l / 1000);
            }

            @Override
            public void onFinish() {
                endQuiz();
            }
        }.start();

    }

    private Question[] parseQuestions(String input) {
        Question[] questions = null;
        if (input != null) {
            String[] str = input.split("[{\\[,\\]}”“\"]");
            mTimeOut = Integer.parseInt(str[str.length - 1]);
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < str.length - 1; i++) {
                if (str[i].length() > 2 && !str[i].contains("  ")) {
                    temp.append(str[i]).append(",");
                }
            }
            String[] info = temp.toString().split(",");
            questions = new Question[info.length / 4];
            for (int i = 0; i < questions.length; i++) {
                String text = info[i * 4];
                boolean answerTrue = Boolean.parseBoolean(info[i * 4 + 1]);
                boolean isCheat = Boolean.parseBoolean(info[i * 4 + 2]);
                int color = Color.parseColor(info[i * 4 + 3]);
                questions[i] = new Question(text, answerTrue, isCheat, color);
            }
        }
        return questions;
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX);
            mScore = savedInstanceState.getInt(BUNDLE_KEY_SCORE);
            mAnsweredArray = savedInstanceState.getBooleanArray(BUNDLE_KEY_ANSWERED_ARRAY);
            if (mAnsweredArray != null) {
                for (int i = 0; i < mQuestionBanks.length; i++) {
                    mQuestionBanks[i].setAnswered(mAnsweredArray[i]);
                }
            }
            mTextViewEndScore.setText(getString(R.string.show_score, mScore));
            mStartLayout.setVisibility(savedInstanceState.getInt(BUNDLE_KEY_START_VISIBILITY));
            mEndLayout.setVisibility(savedInstanceState.getInt(BUNDLE_KEY_END_VISIBILITY));
            mTimeOut = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_TIME);

            mModel = (SettingModel) savedInstanceState.getSerializable(BUNDLE_KEY_CURRENT_SETTING);
        }
        if(mTimeOut!=0){
            startTimer(mTimeOut);
        }
        if (mModel != null) {
            setSettings();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    private void saveState(@NonNull Bundle outState) {
        getAnsweredArray(mQuestionBanks);
        outState.putBooleanArray(BUNDLE_KEY_ANSWERED_ARRAY, mAnsweredArray);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_KEY_SCORE, mScore);
        outState.putInt(BUNDLE_KEY_START_VISIBILITY, mStartLayout.getVisibility());
        outState.putInt(BUNDLE_KEY_END_VISIBILITY, mEndLayout.getVisibility());
        outState.putInt(BUNDLE_KEY_CURRENT_TIME, mCurrentTime);
        outState.putSerializable(BUNDLE_KEY_CURRENT_SETTING, mModel);
    }

    private void setClickListener() {
        mButtonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheatActivity();
            }
        });

        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingActivity();
            }
        });

        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                mQuestionBanks[mCurrentIndex].setAnswered(true);
                setButtonClickable(false);

                if (isAllAnswered()) {
                    endQuiz();
                }
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                mQuestionBanks[mCurrentIndex].setAnswered(true);
                setButtonClickable(false);
                if (isAllAnswered()) {
                    endQuiz();
                }
            }
        });

        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBanks.length;
                updateQuestion();
            }
        });

        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + mQuestionBanks.length) % mQuestionBanks.length;
                updateQuestion();
            }
        });

        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBanks.length - 1;
                updateQuestion();
            }
        });
    }

    private void startSettingActivity() {
        Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
        if (mModel != null) {
            intent.putExtra(EXTRA_CURRENT_SETTING, mModel);
        }
        startActivityForResult(intent, REQUEST_CODE_SETTING_ACTIVITY);
    }

    private void startCheatActivity() {
        boolean currentAnswer = mQuestionBanks[mCurrentIndex].isAnswerTrue();
        Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
        intent.putExtra(EXTRA_CURRENT_ANSWER, currentAnswer);
        startActivityForResult(intent, REQUEST_CODE_CHEAT_ACTIVITY);
        //startActivity(intent);
    }

    private void findAllViews() {
        mTextViewQuestion = findViewById(R.id.text_View_Question);
        mTextViewScore = findViewById(R.id.text_View_Scores);
        mTextViewEndScore = findViewById(R.id.text_View_End_Score);
        mButtonFalse = findViewById(R.id.button_false);
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFirst = findViewById(R.id.button_first);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonNext = findViewById(R.id.button_next);
        mButtonLast = findViewById(R.id.button_last);
        mButtonTryAgain = findViewById(R.id.button_Try_Again);
        mEndLayout = findViewById(R.id.end_layout);
        mStartLayout = findViewById(R.id.start_layout);
        mButtonCheat = findViewById(R.id.button_cheat);
        mTextViewTimer = findViewById(R.id.text_view_timer);
        mButtonSetting = findViewById(R.id.button_setting);
        mRootLayout = findViewById(R.id.quizActivityRootLayout);
    }

    private void updateQuestion() {
        Question currentQuestion = mQuestionBanks[mCurrentIndex];
        mTextViewQuestion.setText(currentQuestion.getQuestionText());
        mTextViewQuestion.setTextColor(currentQuestion.getColor());
        String scores = getString(R.string.show_score, mScore);
        mTextViewScore.setText(scores);
        setButtonClickable(!(mQuestionBanks[mCurrentIndex].isAnswered()));
        if (!currentQuestion.isCheat()) {
            mButtonCheat.setEnabled(false);
        } else {
            mButtonCheat.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBanks[mCurrentIndex].isAnswerTrue() == userPressed) {
            if (!mQuestionBanks[mCurrentIndex].isCheated()) {
                Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_LONG).show();
                mScore += mPositiveGrade;
            } else {
                Toast.makeText(this, R.string.toast_Cheated, Toast.LENGTH_LONG).show();
            }
        } else {
            mScore += mNegativeGrade;
            if (mScore < 0) {
                mScore = 0;
            }
            Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_LONG).show();
        }
        updateQuestion();
    }

    private void setButtonClickable(boolean clickable) {
        mButtonTrue.setEnabled(clickable);
        mButtonFalse.setEnabled(clickable);
    }

    private void getAnsweredArray(Question[] questions) {
        for (int i = 0; i < questions.length; i++) {
            mAnsweredArray[i] = questions[i].isAnswered();
        }
    }

    private void endQuiz() {
        if(mCountTime!=null) {
            mCountTime.cancel();
        }
        mStartLayout.setVisibility(View.GONE);
        mTextViewEndScore.setText(getString(R.string.show_score, mScore));
        mEndLayout.setVisibility(View.VISIBLE);
    }

    private void startQuiz() {
        mEndLayout.setVisibility(View.GONE);
        mCurrentIndex = 0;
        mScore = 0;
        setButtonClickable(true);
        for (Question questionBank : mQuestionBanks) {
            questionBank.setAnswered(false);
            questionBank.setCheated(false);
        }
        updateQuestion();
        mStartLayout.setVisibility(View.VISIBLE);
        startTimer(mTimeOut);
    }

    private boolean isAllAnswered() {
        for (Question questionBank : mQuestionBanks) {
            if (!questionBank.isAnswered())
                return false;
        }
        return true;
    }
}