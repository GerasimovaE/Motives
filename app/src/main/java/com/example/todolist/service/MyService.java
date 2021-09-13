package com.example.todolist.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todolist.R;
import com.example.todolist.screens.main.MainActivity;

import java.util.concurrent.TimeUnit;


public class MyService extends Service {
    public MyService() {
    }

    NotificationManager nm;
    private MediaPlayer player;
    final String LOG_TAG = "myLogs";

        @Override
        public void onCreate() {
            super.onCreate();
            NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        }

        public int onStartCommand(Intent intent, int flags, int startId) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            Log.d(LOG_TAG, "WEEEE1");
            sendNotif();
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true);
            player.start();
            return START_STICKY;
        }

        void sendNotif() {
//        // 1-я часть
//        Notification notif = new android.app.Notification();
//
//        // 3-я часть
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(MainActivity.FILE_INTEGRITY_SERVICE, "somefile");
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        // 2-я часть
//        notif.setLatestEventInfo(this, "Notification's title", "Notification's text", pIntent);
//
//        // ставим флаг, чтобы уведомление пропало после нажатия
//        notif.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // отправляем
//        nm.notify(1, notif);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My notification")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Напоминание")
                    .setContentText("Пора покормить кота")
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat= NotificationManagerCompat.from(this);


           try {
               managerCompat.notify(1, builder.build());
           }catch (Exception e){
               Log.d(LOG_TAG, e.getMessage());
           }
            Log.d(LOG_TAG, "WEEEE");
        }

        public IBinder onBind(Intent arg0) {
            return null;
        }

//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
    }
}