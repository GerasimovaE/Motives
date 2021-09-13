package com.example.todolist.screens.main;


import android.os.Bundle;

import com.example.todolist.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Fragment frag1 = new MainFragment(false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_frame, frag1);
        ft.commit();
    }
}