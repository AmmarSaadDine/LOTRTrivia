package com.example.androind.lortrivia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // A map representing the answers for the multiple choice questions (radio buttons groups questions)
    private Map<Integer, Integer> radioButtonsQA = new HashMap<>();

    // A map representing the answers for the check boxes questions (multiple answers are right)
    private Map<Integer, List<Integer>> checkBoxQA = new HashMap<>();

    // A map representing the answers for the free text questions (edit text questions)
    private Map<Integer, String> freeTextQA = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init question/answers maps
        initRadioButtonsQAMap();
        initCheckBoxQAMap();
        initFreeTextQAMap();

        // Prepare all the images for the questions
        final ImageView introImageView = findViewById(R.id.introImage);
        final ImageView q1ImageView = findViewById(R.id.q1Image);
        final ImageView q2ImageView = findViewById(R.id.q2Image);
        final ImageView q3ImageView = findViewById(R.id.q3Image);
        final ImageView q4a1ImageView = findViewById(R.id.q4a1Image);
        final ImageView q4a2ImageView = findViewById(R.id.q4a2Image);
        final ImageView q5a1ImageView = findViewById(R.id.q5a1Image);
        final ImageView q5a2ImageView = findViewById(R.id.q5a2Image);

        // The following block of code is to update the sizes of the images properly at run time depending on the current screen size
        introImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) introImageView.getLayoutParams();
                params.height = introImageView.getMeasuredWidth();
                introImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q1ImageView.getLayoutParams();
                params.height = q1ImageView.getMeasuredWidth() / 2;
                q1ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q2ImageView.getLayoutParams();
                params.height = q2ImageView.getMeasuredWidth() / 2;
                q2ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q3ImageView.getLayoutParams();
                params.height = q3ImageView.getMeasuredWidth() / 2;
                q3ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q4a1ImageView.getLayoutParams();
                params.height = q4a1ImageView.getMeasuredWidth();
                q4a1ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q4a2ImageView.getLayoutParams();
                params.height = q4a2ImageView.getMeasuredWidth();
                q4a2ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q5a1ImageView.getLayoutParams();
                params.height = q5a1ImageView.getMeasuredWidth();
                q5a1ImageView.setLayoutParams(params);

                params = (LinearLayout.LayoutParams) q5a2ImageView.getLayoutParams();
                params.height = q5a2ImageView.getMeasuredWidth();
                q5a2ImageView.setLayoutParams(params);
            }
        });

    }

    private void initRadioButtonsQAMap() {
        radioButtonsQA = new HashMap<>();
        radioButtonsQA.put(R.id.q1, R.id.q1a2);
        radioButtonsQA.put(R.id.q2, R.id.q2a1);
        radioButtonsQA.put(R.id.q3, R.id.q3a2);
        radioButtonsQA.put(R.id.q4, R.id.q4a1);
        radioButtonsQA.put(R.id.q5, R.id.q5a1);
    }

    private void initCheckBoxQAMap() {
        checkBoxQA = new HashMap<>();
        List<Integer> list1 = Arrays.asList(R.id.cbq1a1, R.id.cbq1a2, R.id.cbq1a4);
        checkBoxQA.put(R.id.cbq1, list1);
    }

    private void initFreeTextQAMap() {
        freeTextQA = new HashMap<>();
        freeTextQA.put(R.id.tq1, getString(R.string.tq1a1).toLowerCase());
    }

    private int getScore() {
        return getScoreForFreeTexQuestions()
                + getScoreForCheckBoxesQuestions()
                + getScoreForRadioButtonsQuestions();
    }

    private int getScoreForRadioButtonsQuestions() {
        int score = 0;

        // loop through all radio groups questions
        for (Map.Entry<Integer, Integer> entry : radioButtonsQA.entrySet()) {
            RadioGroup questionGroup = findViewById(entry.getKey());
            if (questionGroup.getCheckedRadioButtonId() == entry.getValue()) {
                score++;
            }
        }
        return score;
    }

    private int getScoreForCheckBoxesQuestions() {
        int score = 0;

        // loop through all check boxs questions
        for (Map.Entry<Integer, List<Integer>> entry : checkBoxQA.entrySet()) {
            LinearLayout questionGroupView = findViewById(entry.getKey());
            List<Integer> rightAnswers = entry.getValue();
            List<Integer> currentAnswers = new ArrayList<>();
            for (int i = 0; i < questionGroupView.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) questionGroupView.getChildAt(i);
                if (checkBox.isChecked()) {
                    currentAnswers.add(checkBox.getId());
                }
            }
            if (currentAnswers.equals(rightAnswers)) {
                score++;
            }
        }
        return score;
    }

    private int getScoreForFreeTexQuestions() {
        int score = 0;

        // loop through all free text questions
        for (Map.Entry<Integer, String> entry : freeTextQA.entrySet()) {
            EditText editText = findViewById(entry.getKey());
            String currentAnswer = editText.getText().toString().toLowerCase();
            if (currentAnswer.equals(entry.getValue())) {
                score++;
            }
        }
        return score;
    }

    private String getUserNickname() {
        EditText editText = findViewById(R.id.editText);
        return editText.getText().toString().trim();
    }

    private int getQuestionsCount() {
        return radioButtonsQA.size() + checkBoxQA.size() + freeTextQA.size();
    }

    public void submit(View view) {
        int score = getScore();
        String nickname = getUserNickname();
        String message;

        int maxScore = getQuestionsCount();
        if (score == maxScore) {
            message = getString(R.string.responsePerfect, nickname, score, maxScore);
        } else if (score == maxScore - 1) {
            message = getString(R.string.responseAlmostThere, nickname, score, maxScore);
        } else {
            message = getString(R.string.responseTryAgain, nickname, score, maxScore);
        }

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, text, duration).show();
    }
}
