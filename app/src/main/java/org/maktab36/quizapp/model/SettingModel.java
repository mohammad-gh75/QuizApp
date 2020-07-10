package org.maktab36.quizapp.model;

import java.io.Serializable;

public class SettingModel implements Serializable {
    private int mButtonTrueVisibility;
    private int mButtonFalseVisibility;
    private int mButtonNextVisibility;
    private int mButtonPreviousVisibility;
    private int mButtonLastVisibility;
    private int mButtonFirstVisibility;
    private int mButtonCheatVisibility;

    private int mBackgroundColorId;

    private int mQuestionSize;

    private boolean mBackButtonEnable;

    private boolean mTimeOutEnable;

    private int mPositiveGrade;
    private int mNegativeGrade;

    public int getPositiveGrade() {
        return mPositiveGrade;
    }

    public void setPositiveGrade(int positiveGrade) {
        mPositiveGrade = positiveGrade;
    }

    public int getNegativeGrade() {
        return mNegativeGrade;
    }

    public void setNegativeGrade(int negativeGrade) {
        mNegativeGrade = negativeGrade;
    }

    public boolean isTimeOutEnable() {
        return mTimeOutEnable;
    }

    public void setTimeOutEnable(boolean timeOutEnable) {
        mTimeOutEnable = timeOutEnable;
    }

    public boolean isBackButtonEnable() {
        return mBackButtonEnable;
    }

    public void setBackButtonEnable(boolean backButtonEnable) {
        mBackButtonEnable = backButtonEnable;
    }

    public int getQuestionSize() {
        return mQuestionSize;
    }

    public void setQuestionSize(int questionSize) {
        mQuestionSize = questionSize;
    }

    public int getBackgroundColorId() {
        return mBackgroundColorId;
    }

    public void setBackgroundColorId(int backgroundColorId) {
        mBackgroundColorId = backgroundColorId;
    }

    public int getButtonTrueVisibility() {
        return mButtonTrueVisibility;
    }

    public void setButtonTrueVisibility(int buttonTrueVisibility) {
        mButtonTrueVisibility = buttonTrueVisibility;
    }

    public int getButtonFalseVisibility() {
        return mButtonFalseVisibility;
    }

    public void setButtonFalseVisibility(int buttonFalseVisibility) {
        mButtonFalseVisibility = buttonFalseVisibility;
    }

    public int getButtonNextVisibility() {
        return mButtonNextVisibility;
    }

    public void setButtonNextVisibility(int buttonNextVisibility) {
        mButtonNextVisibility = buttonNextVisibility;
    }

    public int getButtonPreviousVisibility() {
        return mButtonPreviousVisibility;
    }

    public void setButtonPreviousVisibility(int buttonPreviousVisibility) {
        mButtonPreviousVisibility = buttonPreviousVisibility;
    }

    public int getButtonLastVisibility() {
        return mButtonLastVisibility;
    }

    public void setButtonLastVisibility(int buttonLastVisibility) {
        mButtonLastVisibility = buttonLastVisibility;
    }

    public int getButtonFirstVisibility() {
        return mButtonFirstVisibility;
    }

    public void setButtonFirstVisibility(int buttonFirstVisibility) {
        mButtonFirstVisibility = buttonFirstVisibility;
    }

    public int getButtonCheatVisibility() {
        return mButtonCheatVisibility;
    }

    public void setButtonCheatVisibility(int buttonCheatVisibility) {
        mButtonCheatVisibility = buttonCheatVisibility;
    }
}
