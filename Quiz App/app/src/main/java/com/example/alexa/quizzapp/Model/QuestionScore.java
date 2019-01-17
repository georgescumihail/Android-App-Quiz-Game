package com.example.alexa.quizzapp.Model;

public class QuestionScore {
    private String Question_Score;
    private String User;
    private String Score;

    public QuestionScore() {
    }

    public QuestionScore(String question_Score, String user, String score) {
        Question_Score = question_Score;
        User = user;
        Score = score;
    }

    public String getQuestion_Score() {
        return Question_Score;
    }

    public void setQuestion_Score(String question_Score) {
        Question_Score = question_Score;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
