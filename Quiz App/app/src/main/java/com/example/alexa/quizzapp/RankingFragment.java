package com.example.alexa.quizzapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexa.quizzapp.Common.Common;
import com.example.alexa.quizzapp.Interface.ItemClickListener;
import com.example.alexa.quizzapp.Interface.RankingCallBack;
import com.example.alexa.quizzapp.Model.QuestionScore;
import com.example.alexa.quizzapp.Model.Ranking;
import com.example.alexa.quizzapp.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RankingFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference questScore, rankTbl;

    View myFragment;

    RecyclerView rankList;
    LinearLayoutManager layoutManagr;
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder> adapter;

    int sum=0;

    public static RankingFragment newInstance() {
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questScore = database.getReference("Question_Score");
        rankTbl = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking,container,false);

        //Init View
        rankList = (RecyclerView) myFragment.findViewById(R.id.rankingList);
        layoutManagr = new LinearLayoutManager(getActivity());
        rankList.setHasFixedSize(true);
        //Because OrderByChild sorts the list in a ascending order
        //we need to reverse the Recycler data with LayoutManager
        layoutManagr.setReverseLayout(true);
        layoutManagr.setStackFromEnd(true);
        rankList.setLayoutManager(layoutManagr);

        //Now implementing callback
        updateScore(Common.currentUser1.getUserName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                //Update to Ranking table
                rankTbl.child(ranking.getUserName())
                .setValue(ranking);
                //showRanking(); //After the upload we will just have to sort the ranking and
                //display the result
            }
        });

        //set Adapter
        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankTbl.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {
                viewHolder.txt_name.setText(model.getUserName());
                viewHolder.txt_score.setText(String.valueOf(model.getScore()));

                //Fixed crash when item is clicked
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        rankList.setAdapter(adapter);

        return myFragment;
    }

    private void showRanking() {
        //print the Log to see the score
        rankTbl.orderByChild("score")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            Ranking local = data.getValue(Ranking.class);
                            Log.d("DEBUG", local.getUserName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void updateScore (final String userName, final RankingCallBack<Ranking> callback)
    {
        questScore.orderByChild("user").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            QuestionScore ques = data.getValue(QuestionScore.class);
                            sum +=Integer.parseInt(ques.getScore());
                        }
                        //After summary of all score, we should process the sum as a variable here
                        //Since FireBase is a asynchronus database we can not process the sum
                        //outside or it's value will get reset to 0
                        Ranking ranking = new Ranking(userName,sum);
                        callback.callBack(ranking);
                    }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                }
                );
    }

}
