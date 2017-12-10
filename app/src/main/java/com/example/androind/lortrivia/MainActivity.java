package com.example.androind.lortrivia;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Integer> questionsAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initQAMap();

        final ImageView introImageView = findViewById(R.id.introImage);
        final ImageView q1ImageView = findViewById(R.id.q1Image);
        final ImageView q2ImageView = findViewById(R.id.q2Image);
        final ImageView q3ImageView = findViewById(R.id.q3Image);
        final ImageView q4a1ImageView = findViewById(R.id.q4a1Image);
        final ImageView q4a2ImageView = findViewById(R.id.q4a2Image);
        final ImageView q5a1ImageView = findViewById(R.id.q5a1Image);
        final ImageView q5a2ImageView = findViewById(R.id.q5a2Image);

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initQAMap() {
        questionsAnswers = new HashMap<>();
        questionsAnswers.put(R.id.q1, R.id.q1a2);
        questionsAnswers.put(R.id.q2, R.id.q2a1);
        questionsAnswers.put(R.id.q3, R.id.q3a2);
        questionsAnswers.put(R.id.q4, R.id.q4a1);
        questionsAnswers.put(R.id.q5, R.id.q5a1);
    }

    private int getScore() {
        int score = 0;

        for (Map.Entry<Integer, Integer> entry : questionsAnswers.entrySet()) {
            RadioGroup questionGroup = findViewById(entry.getKey());
            if (questionGroup.getCheckedRadioButtonId() == entry.getValue()) {
                score++;
            }
        }
        return score;
    }

    public void submit(View view) {
        int score = getScore();
        String title = "";
        String bodyMessage = "";
        if (score == questionsAnswers.size()) {
            title = "Congratulations!!";
            bodyMessage = "Perfect score " + score + "/" + questionsAnswers.size();
        } else if (score == questionsAnswers.size() - 1) {
            title = "Almost there!";
            bodyMessage = "You scored " + score + "/" + questionsAnswers.size();
        } else {
            title = "Try again!";
            bodyMessage = "You scored " + score + "/" + questionsAnswers.size();
        }

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(bodyMessage)
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }
}
