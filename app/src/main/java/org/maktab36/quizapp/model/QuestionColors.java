package org.maktab36.quizapp.model;

import android.util.Log;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.controller.QuizActivity;

public enum QuestionColors {
    BLACK(R.color.colorBlack),
    RED(R.color.colorRed),
    BLUE(R.color.colorBlue),
    GREEN(R.color.colorGreen);

    private int mColor;

    public int getColor() {
        Log.d(QuizActivity.TAG, "questionColor getColor: ");
        return mColor;
    }

    QuestionColors(int color){
        mColor=color;
    }
}
