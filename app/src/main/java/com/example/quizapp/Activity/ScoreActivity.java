package com.example.quizapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quizapp.MainActivity;
import com.example.quizapp.R;
import com.example.quizapp.databinding.ActivityScoreBinding;
import com.google.gson.Gson;

public class ScoreActivity extends AppCompatActivity {

    ActivityScoreBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = ScoreActivity.this.getSharedPreferences("main", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        int totalScore = sharedPreferences.getInt("total",0);
        int correctAns = sharedPreferences.getInt("score",0);

        int wrong =  totalScore - correctAns;

        binding.totalQustions.setText(String.valueOf(totalScore));
        binding.rightAnswer.setText(String.valueOf(correctAns));

        binding.wrongAnswer.setText(String.valueOf(wrong));
        binding.retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this,SetsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.quitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}