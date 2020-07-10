package org.maktab36.quizapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import org.maktab36.quizapp.R;
import org.maktab36.quizapp.model.SettingModel;

import java.io.Serializable;

public class SettingActivity extends AppCompatActivity {
    public static final String EXTRA_SETTING = "org.maktab36.quizapp.setting";

    private Switch mSwitchTrue;
    private Switch mSwitchFalse;
    private Switch mSwitchNext;
    private Switch mSwitchPrevious;
    private Switch mSwitchLast;
    private Switch mSwitchFirst;
    private Switch mSwitchCheat;
    private Switch mSwitchBack;
    private Switch mSwitchTime;
    private Button mButtonSave;
    private Button mButtonDiscard;
    private RadioGroup mRadioGroupColors;
    private RadioGroup mRadioGroupSizes;
    private EditText mEditTextPositiveGrade;
    private EditText mEditTextNegativeGrade;

    private SettingModel mModel = new SettingModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findAllViews();
        loadSetting();
        setClickListener();
    }

    private void loadSetting() {
        Serializable extra = getIntent().getSerializableExtra(QuizActivity.EXTRA_CURRENT_SETTING);
        if (extra != null) {
            mModel = (SettingModel) extra;
            getColorSetting();
            getSizeSetting();
            getBackButtonSetting();
            getTimeOutSetting();
            getButtonsVisibilitySetting();
            getGradesSetting();
        }
    }

    private void findAllViews() {
        mSwitchTrue = findViewById(R.id.switch_hide_true);
        mSwitchFalse = findViewById(R.id.switch_hide_false);
        mSwitchNext = findViewById(R.id.switch_hide_next);
        mSwitchPrevious = findViewById(R.id.switch_hide_previous);
        mSwitchLast = findViewById(R.id.switch_hide_last);
        mSwitchFirst = findViewById(R.id.switch_hide_first);
        mSwitchCheat = findViewById(R.id.switch_hide_cheat);
        mSwitchBack = findViewById(R.id.switchDisableBack);
        mSwitchTime = findViewById(R.id.switchDisableTimeOut);
        mButtonSave = findViewById(R.id.button_setting_save);
        mButtonDiscard = findViewById(R.id.button_setting_discard);
        mRadioGroupColors = findViewById(R.id.radioGroupColors);
        mRadioGroupSizes = findViewById(R.id.radioGroupSizes);
        mEditTextPositiveGrade = findViewById(R.id.editTextNumberSignedPositiveGrade);
        mEditTextNegativeGrade = findViewById(R.id.editTextNumberSignedNegativeGrade);
    }

    private void setClickListener() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResult();
                finish();
            }
        });
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveResult() {
        Intent intent = new Intent();
        setBackgroundColor();
        setQuestionSize();
        setBackButtonEnabling();
        setTimeEnabling();
        setButtonsVisibility();
        setGrades();
        intent.putExtra(EXTRA_SETTING, mModel);
        setResult(RESULT_OK, intent);
    }

    private void getSizeSetting() {
        switch (mModel.getQuestionSize()) {
            case 14:
                mRadioGroupSizes.check(R.id.radioButtonSmall);
                break;
            case 18:
                mRadioGroupSizes.check(R.id.radioButtonMedium);
                break;
            case 24:
                mRadioGroupSizes.check(R.id.radioButtonLarge);
                break;
        }
    }

    private void setQuestionSize() {
        RadioButton sizeButton = findViewById(mRadioGroupSizes.getCheckedRadioButtonId());
        String small = getString(R.string.radio_button_size_small);
        String medium = getString(R.string.radio_button_size_medium);
        String large = getString(R.string.radio_button_size_large);
        String currentSize = sizeButton.getText().toString();

        if (currentSize.equals(small)) {
            mModel.setQuestionSize(14);
        } else if (currentSize.equals(medium)) {
            mModel.setQuestionSize(18);
        } else {
            mModel.setQuestionSize(24);
        }
    }

    private void getColorSetting() {
        switch (mModel.getBackgroundColorId()) {
            case R.color.colorLightRed:
                mRadioGroupColors.check(R.id.radioButtonLightRed);
                break;
            case R.color.colorLightBlue:
                mRadioGroupColors.check(R.id.radioButtonLightBlue);
                break;
            case R.color.colorLightGreen:
                mRadioGroupColors.check(R.id.radioButtonLightGreen);
                break;
            default:
                mRadioGroupColors.check(R.id.radioButtonWhite);
                break;
        }
    }

    private void setBackgroundColor() {
        RadioButton colorButton = findViewById(mRadioGroupColors.getCheckedRadioButtonId());
        String lightRed = getString(R.string.radio_button_color_lightRed);
        String lightBlue = getString(R.string.radio_button_color_lightBlue);
        String lightGreen = getString(R.string.radio_button_color_lightGreen);
        String white = getString(R.string.radio_button_color_white);
        String currentColorText = colorButton.getText().toString();

        if (currentColorText.equals(lightRed)) {
            mModel.setBackgroundColorId(R.color.colorLightRed);
        } else if (currentColorText.equals(lightBlue)) {
            mModel.setBackgroundColorId(R.color.colorLightBlue);
        } else if (currentColorText.equals(lightGreen)) {
            mModel.setBackgroundColorId(R.color.colorLightGreen);
        } else {
            mModel.setBackgroundColorId(R.color.colorWhite);
        }
    }

    private void getBackButtonSetting() {
        mSwitchBack.setChecked(mModel.isBackButtonEnable());
    }

    private void setBackButtonEnabling() {
        mModel.setBackButtonEnable(mSwitchBack.isChecked());
    }

    private void getTimeOutSetting() {
        mSwitchTime.setChecked(mModel.isTimeOutEnable());
    }

    private void setTimeEnabling() {
        mModel.setTimeOutEnable(mSwitchTime.isChecked());
    }

    private void getGradesSetting() {
        mEditTextPositiveGrade.setText(String.valueOf(mModel.getPositiveGrade()));
        mEditTextNegativeGrade.setText(String.valueOf(mModel.getNegativeGrade()));
    }

    private void setGrades() {
        mModel.setPositiveGrade(Integer.parseInt(mEditTextPositiveGrade.getText().toString()));
        mModel.setNegativeGrade(Integer.parseInt(mEditTextNegativeGrade.getText().toString()));
    }

    private void getButtonsVisibilitySetting() {
        mSwitchTrue.setChecked(mModel.getButtonTrueVisibility() == View.VISIBLE);
        mSwitchFalse.setChecked(mModel.getButtonFalseVisibility() == View.VISIBLE);
        mSwitchNext.setChecked(mModel.getButtonNextVisibility() == View.VISIBLE);
        mSwitchPrevious.setChecked(mModel.getButtonPreviousVisibility() == View.VISIBLE);
        mSwitchFirst.setChecked(mModel.getButtonFirstVisibility() == View.VISIBLE);
        mSwitchLast.setChecked(mModel.getButtonLastVisibility() == View.VISIBLE);
        mSwitchCheat.setChecked(mModel.getButtonCheatVisibility() == View.VISIBLE);
    }

    private void setButtonsVisibility() {
        mModel.setButtonTrueVisibility(buttonVisibility(mSwitchTrue));
        mModel.setButtonFalseVisibility(buttonVisibility(mSwitchFalse));
        mModel.setButtonNextVisibility(buttonVisibility(mSwitchNext));
        mModel.setButtonPreviousVisibility(buttonVisibility(mSwitchPrevious));
        mModel.setButtonFirstVisibility(buttonVisibility(mSwitchFirst));
        mModel.setButtonLastVisibility(buttonVisibility(mSwitchLast));
        mModel.setButtonCheatVisibility(buttonVisibility(mSwitchCheat));
    }

    private int buttonVisibility(Switch s) {
        if (s.isChecked()) {
            return View.VISIBLE;
        } else {
            return View.INVISIBLE;
        }
    }

}