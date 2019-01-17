    package com.example.alexa.quizzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexa.quizzapp.Common.Common;
import com.example.alexa.quizzapp.Model.User;
import com.example.alexa.quizzapp.ViewHolder.UserDatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

    public class MainActivity extends AppCompatActivity {
        EditText userName, pass; //for sign in
        EditText newUsrName, newPass, newGmail, newOccupation; //for sign up


        FirebaseDatabase databaseF;
        DatabaseReference usersData;
        UserDatabaseHelper helper = new UserDatabaseHelper(this);

        Button btnSignUp, btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText)findViewById(R.id.userName);
        pass=(EditText)findViewById(R.id.password);

        //firebase part
        databaseF = FirebaseDatabase.getInstance();
        usersData= databaseF.getReference("Users");

        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        btnSignIn= (Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(userName.getText().toString(),
                        pass.getText().toString() );
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpDialog();
            }


        });
    }

        private void signIn(final String userN, final String password) {
            usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(userN).exists())
                    {
                        if(!userN.isEmpty()){
                            User logIn = dataSnapshot.child(userN).getValue(User.class);
                            if(logIn.getPassword().equals(password)) {
                                //Toast.makeText(MainActivity.this, "Login successfull!", Toast.LENGTH_SHORT).show();
                                Intent homeActvty = new Intent(MainActivity.this,Home.class);
                                Common.currentUser1 = logIn;
                                startActivity(homeActvty);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "The password is incorrect!", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Enter your username, please.", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else{
                        Toast.makeText(MainActivity.this, "The user doesn't exist!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError dataErr) {

                }
            });
            usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(userN).exists())
                    {
                        if(!userN.isEmpty()){
                            User logIn = dataSnapshot.child(userN).getValue(User.class);
                            if(logIn.getPassword().equals(password)) {
                                //Toast.makeText(MainActivity.this, "Login successfull!", Toast.LENGTH_SHORT).show();
                                Intent homeActvty = new Intent(MainActivity.this,Home.class);
                                Common.currentUser1 = logIn;
                                startActivity(homeActvty);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "The password is incorrect!", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Enter your username, please.", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else{
                        Toast.makeText(MainActivity.this, "The user doesn't exist!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError dataErr) {

                }
            });
        }

        private void signUpDialog() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Sign Up");
            alertDialog.setMessage("Please fill!");

            LayoutInflater inflater =this.getLayoutInflater();
            View sign_up_layout=inflater.inflate(R.layout.sign_up_layout,null);

            newUsrName=(EditText)sign_up_layout.findViewById(R.id.newUserName);
            newGmail=(EditText)sign_up_layout.findViewById(R.id.newEmail);
            newPass=(EditText)sign_up_layout.findViewById(R.id.newPassword);

            newOccupation=(EditText)sign_up_layout.findViewById(R.id.newOccupation);


            alertDialog.setView(sign_up_layout);
            alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final User users = new User(newUsrName.getText().toString(),
                            newPass.getText().toString(),
                            newGmail.getText().toString(),
                            newOccupation.getText().toString());

                    usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(users.getUserName()).exists())
                                Toast.makeText(MainActivity.this, "This user already exists!", Toast.LENGTH_SHORT).show();
                            else{
                                usersData.child(users.getUserName()).setValue(users);
                                Toast.makeText(MainActivity.this, "Successful registration!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }
    }

