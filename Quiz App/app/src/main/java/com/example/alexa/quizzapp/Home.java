package com.example.alexa.quizzapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFrag = null;
                switch(menuItem.getItemId()){
                    case R.id.action_category:
                        selectedFrag = CategoryFragment.newInstance();
                        break;
                    case R.id.action_ranking:
                        selectedFrag = RankingFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,selectedFrag);
                transaction.commit();

                return true;
            }
        });
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,CategoryFragment.newInstance());
        transaction.commit();
    }
}
