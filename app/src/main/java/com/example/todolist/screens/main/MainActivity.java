package com.example.todolist.screens.main;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    public static MainFragment.MyTimerTask mMyTimerTask = null;
    private static SharedPreferences prefs = null;
    private Note note = new Note();
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        prefs = getSharedPreferences("com.example.todolist", MODE_PRIVATE);

        if (savedInstanceState == null) {
            Fragment frag1 = new MainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_frame, frag1);
            ft.commit();
        }
    }

    public void startAlarm(){

        Calendar notifyTime = Calendar.getInstance();
                        notifyTime.add(Calendar.MINUTE, 5);
//        notifyTime.add(Calendar.SECOND, 10);
//                notifyTime.set(Calendar.SECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm", Locale.getDefault());
        String str =  sdf.format(notifyTime.getTimeInMillis());

//        prefs = getSharedPreferences("com.example.todolist", MODE_PRIVATE);
        prefs.edit().putLong("time", notifyTime.getTimeInMillis()).commit();

        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, notifyTime.getTimeInMillis(), pendingIntent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        TimerCountDown.setTimeNull();
        if (mMyTimerTask != null) {
            mMyTimerTask.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        SharedPreferences prefs = getSharedPreferences("com.example.todolist", MODE_PRIVATE);
        long l = prefs.getLong("time", new Date().getTime());
        Date currentDate = new Date();
        if (l > currentDate.getTime()) {
            Long time = (long) Math.round((l - currentDate.getTime()) / 1000);
            TimerCountDown timer = new TimerCountDown(time * 1000, 1000L);
            timer.start();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // При первом запуске (или если юзер удалял все данные приложения)
            // мы попадаем сюда. Делаем что-то
            //и после действия записывам false в переменную firstrun
            //Итого при следующих запусках этот код не вызывается.
            String[] arrayString = this.getResources().getStringArray(R.array.motivate);
            for (String str:
                    arrayString) {
                note.text = str;
                App.getInstance().getNoteDao().insert(note);
            }

            prefs.edit().putLong("time", 0).commit();
            prefs.edit().putBoolean("firstrun", false).commit();
        }

    }
}