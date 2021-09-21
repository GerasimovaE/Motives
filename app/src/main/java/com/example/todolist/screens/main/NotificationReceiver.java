package com.example.todolist.screens.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.todolist.service.MyService;


public class NotificationReceiver extends BroadcastReceiver {

    final String LOG_TAG = "myLogs";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"NotificationReceiver start!",Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "NotificationReceiver onReceive");
        Intent intentService = new Intent(context, MyService.class);
        Log.d(LOG_TAG, "NotificationReceiver start");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(LOG_TAG, "NotificationReceiver startForegroundService");
            context.startForegroundService(intentService);
        } else {
            Log.d(LOG_TAG, "NotificationReceiver startService");
            context.startService(intentService);
        }
    }

}
