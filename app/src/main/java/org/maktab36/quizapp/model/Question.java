package org.maktab36.quizapp.model;

public class Question {
    private String mQuestionText;
    private boolean mAnswerTrue;
    private boolean mAnswered = false;
    private boolean mIsCheat;
    private boolean mCheated = false;
    private int mColor;

    public boolean isCheated() {
        return mCheated;
    }

    public void setCheated(boolean cheated) {
        mCheated = cheated;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(boolean answered) {
        this.mAnswered = answered;
    }

    public boolean isCheat() {
        return mIsCheat;
    }

    public void setCheat(boolean cheat) {
        mIsCheat = cheat;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public Question(String questionText, boolean answerTrue, boolean isCheat, int color) {
        mQuestionText = questionText;
        mAnswerTrue = answerTrue;
        this.mIsCheat = isCheat;
        this.mColor = color;
    }
}
