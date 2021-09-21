package com.example.todolist.screens.main;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    final String LOG_MAIN_FRAGMENT= "LOG_MAIN_FRAGMENT";
    MediaPlayer mp = null;

    private Button buttonMotives;
    private Timer mTimer;
    Long time = 0L;
    View view = null;
    SharedPreferences prefs = null;
    private boolean firstVisit;

    public MainFragment() { }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        buttonMotives = view.findViewById(R.id.buttonMotives);
        final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
        mp = MediaPlayer.create(getContext(), R.raw.button);
        firstVisit = true;

        changeView();

        buttonMotives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);
                mp.start();

                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (getActivity() != null) {
                            MainActivity ma = (MainActivity) getActivity();
                            ma.startAlarm();
                        }

                        Fragment frag1 = new MotivesFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_frame, frag1, LOG_MAIN_FRAGMENT);
                        ft.commit();
                    }
                },300);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstVisit) {
            firstVisit = false;
        }else {
            startTimer();
            changeView();
        }
    }

    private void changeView(){
        if (time != 0L){
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fon2));
            buttonMotives.setText(R.string.nextMotiv);
            buttonMotives.setEnabled(false);
            buttonMotives.setBackgroundColor(getResources().getColor(R.color.pink));
            buttonMotives.setTextColor(getResources().getColor(R.color.white));

            if (mTimer != null) {
                mTimer.cancel();
            }

            mTimer = new Timer();
            MainActivity.mMyTimerTask = new MyTimerTask();
            mTimer.schedule(MainActivity.mMyTimerTask, 0L, 1000L);
        }else{
            buttonMotives.setText(R.string.get_motivated);
            buttonMotives.setEnabled(true);
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fon1));
        }
    }

    private void startTimer(){
        SharedPreferences prefs = getContext().getSharedPreferences("com.example.todolist", MODE_PRIVATE);
        long l = prefs.getLong("time", new Date().getTime());
        Date currentDate = new Date();
        if (l > currentDate.getTime()){
            time = (long)Math.round((l - currentDate.getTime())/1000);
            TimerCountDown timer = new TimerCountDown(time * 1000, 1000L);
            timer.start();
        }else {
            time=0L;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTimer();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            try {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        buttonMotives.setText(TimerCountDown.getTime() + System.getProperty("line.separator") + "след. мотивация");
//                        Toast.makeText(getActivity(),"Мы тут!",Toast.LENGTH_SHORT).show();

                        if (TimerCountDown.getTime().equals("00 : 00 : 00")) {
                            mTimer.cancel();
                            buttonMotives.setText(R.string.get_motivated);
                            buttonMotives.setEnabled(true);
                            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fon1));

                        }
                    }
                });
            } catch (Exception e) {
                //ignore
            }
        }
    }
}
