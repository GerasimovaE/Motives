package com.example.todolist.screens.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Note;
import com.example.todolist.service.MyService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class MotivesFragment extends Fragment {

    TextView textViewMotives;
    Button buttonMain;
    final Random random = new Random();
    final String LOG_TAG = "myLogs";
    MediaPlayer mp = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_motives, container, false);

        textViewMotives = view.findViewById(R.id.textMotives);
        List<Note> list = App.getInstance().getNoteDao().getAll();
        Note notNew = App.getInstance().getNoteDao().findById(random.nextInt(10) + 1);
        textViewMotives.setText(notNew.text);
        buttonMain = view.findViewById(R.id.buttonMainMenu);
        final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
        mp = MediaPlayer.create(getContext(), R.raw.button);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);
                mp.start();

                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Fragment nextFrag = new MainFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, nextFrag, "MainFragment")
//                                .addToBackStack("Main")
                                .commit();
                    }
                },300);
            }
        });

        return view;
    }

}
