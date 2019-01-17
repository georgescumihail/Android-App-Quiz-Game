package com.example.alexa.quizzapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alexa.quizzapp.Common.Common;
import com.example.alexa.quizzapp.Model.QuestionScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtResultScore,getTxtResultQuestion;
    Button btnTryAgain;

    
    DatabaseReference question_scr;
    FirebaseDatabase database1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database1 = FirebaseDatabase.getInstance();
        question_scr = database1.getReference("Question_Score");

        txtResultScore = (TextView)findViewById(R.id.txtScore);
        getTxtResultQuestion = (TextView)findViewById(R.id.txtTotalQuestion);
        progressBar = (ProgressBar)findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button)findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this,Home.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d",score));
            getTxtResultQuestion.setText((String.format("PASSED: %d / %d",correctAnswer,totalQuestion)));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            //upload point to db
            question_scr.child(String.format("%s_%s", Common.currentUser1.getUserName(),
                                                        Common.categoryID))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser1.getUserName(),
                            Common.categoryID),
                            Common.currentUser1.getUserName(),
                            String.valueOf(score))
                    );
        }
    }
}
