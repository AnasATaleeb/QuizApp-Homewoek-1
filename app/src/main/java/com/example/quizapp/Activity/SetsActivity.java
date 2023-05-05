package com.example.quizapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.quizapp.Adapters.SetAdapter;
import com.example.quizapp.Models.SetModel;
import com.example.quizapp.R;
import com.example.quizapp.databinding.ActivitySetsBinding;

import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {

    ActivitySetsBinding binding;
    ArrayList<SetModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecy.setLayoutManager(linearLayoutManager);

        list.add(new SetModel("مجموعة - 1"));
        list.add(new SetModel("مجموعة - 2"));
        list.add(new SetModel("مجموعة - 3"));
        list.add(new SetModel("مجموعة - 4"));
        list.add(new SetModel("مجموعة - 5"));
        list.add(new SetModel("مجموعة - 6"));
        list.add(new SetModel("مجموعة - 7"));
        list.add(new SetModel("مجموعة - 8"));
        list.add(new SetModel("مجموعة - 9"));
        list.add(new SetModel("مجموعة - 10"));



        SetAdapter adapter = new SetAdapter(this,list);
        binding.setsRecy.setAdapter(adapter);
    }
}