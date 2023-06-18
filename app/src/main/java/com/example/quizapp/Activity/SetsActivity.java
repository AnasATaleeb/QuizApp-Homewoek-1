package com.example.quizapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.quizapp.Adapters.SetAdapter;
import com.example.quizapp.Models.SetModel;
import com.example.quizapp.R;
import com.example.quizapp.databinding.ActivitySetsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ActivitySetsBinding binding;
    SetModel[] list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        list = new SetModel[10];

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecy.setLayoutManager(linearLayoutManager);

        list[0] = (new SetModel("مجموعة - 1"));
        list[1] = (new SetModel("مجموعة - 2"));
        list[2] = (new SetModel("مجموعة - 3"));
        list[3] = (new SetModel("مجموعة - 4"));
        list[4] = (new SetModel("مجموعة - 5"));
        list[5] = (new SetModel("مجموعة - 6"));
        list[6] = (new SetModel("مجموعة - 7"));
        list[7] = (new SetModel("مجموعة - 8"));
        list[8] = (new SetModel("مجموعة - 9"));
        list[9] = (new SetModel("مجموعة - 10"));

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        editor.putString("sets", gson.toJson(list));
        editor.commit();



        String s = sharedPreferences.getString("sets",null);

        list = gson.fromJson(s,SetModel[].class);


        SetAdapter adapter = new SetAdapter(this, list);
        binding.setsRecy.setAdapter(adapter);
    }
}