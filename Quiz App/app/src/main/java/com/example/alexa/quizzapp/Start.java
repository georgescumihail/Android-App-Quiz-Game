package com.example.alexa.quizzapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexa.quizzapp.Common.Common;
import com.example.alexa.quizzapp.Model.Question;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button buttonPlay;
    FirebaseDatabase database;
    DatabaseReference questns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database = FirebaseDatabase.getInstance();
        questns = database.getReference().child("Questions");
        
        loadQuestion(Common.categoryID);

        buttonPlay = (Button)findViewById(R.id.btnPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(final String categoryID) {

        //cica trb curatata lista de intrebari folosite
        if(Common.questionList.size()>0)
            Common.questionList.clear();

        final Query query = questns;

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                if(dataSnapshot.getValue() != null) {

                    Question que = dataSnapshot.getValue(Question.class);

                        String questionId = que.getCategoryId();
                        int copie1 = Integer.parseInt(questionId);
                        int copie2 = Integer.parseInt(categoryID);
                        if(copie1 == copie2) {
                            Common.questionList.add(que);
                        }


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //amestecator de intrebari
        Collections.shuffle(Common.questionList);
    }
}















