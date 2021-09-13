package com.example.todolist.screens.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.todolist.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";
    final String LOG_TAG = "myLogs";
    final String LOG_MAIN_FRAGMENT= "LOG_MAIN_FRAGMENT";

    private Button buttonMotives;
    private Boolean timer;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    private NotificationManager mNotificationManager;

    public MainFragment(Boolean timer) {
        this.timer = timer;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        buttonMotives = view.findViewById(R.id.buttonMotives);
        // TimerCountDown.getTime()

        if (!TimerCountDown.getTime().equals("00 : 00")){
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fon2));
            buttonMotives.setText(R.string.nextMotiv);
            buttonMotives.setEnabled(false);
            buttonMotives.setBackgroundColor(getResources().getColor(R.color.pink));
            buttonMotives.setTextColor(getResources().getColor(R.color.white));

            if (mTimer != null) {
                mTimer.cancel();
            }

            mTimer = new Timer();
            mMyTimerTask = new MyTimerTask();
            mTimer.schedule(mMyTimerTask, 0L, 1000L);

        }

        buttonMotives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frag1 = new MotivesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, frag1, LOG_MAIN_FRAGMENT);
//                .addToBackStack(LOG_MAIN_FRAGMENT);
                ft.commit();


            }
        });

        return view;
    }


    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    buttonMotives.setText(TimerCountDown.getTime() + System.getProperty ("line.separator") + "след. мотивация");

                    if (TimerCountDown.getTime().equals("00 : 01")){
                        mTimer.cancel();
                        buttonMotives.setText(R.string.get_motivated);
                        buttonMotives.setEnabled(true);

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "My notification")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Напоминание")
                                .setContentText("Получите мотивацию")
                                .setAutoCancel(true);

                        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(getContext());
                        managerCompat.notify(1, builder.build());
                    }

                }
            });
        }
    }
}
