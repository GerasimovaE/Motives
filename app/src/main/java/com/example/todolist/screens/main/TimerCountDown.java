package com.example.todolist.screens.main;
import android.os.CountDownTimer;

public class TimerCountDown extends CountDownTimer{

    static long timeleft;

    public static String formatTime(long millis) {
        String output = "00:00:00";
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;
        if (hours < 10)
            hoursD = "0" + hours;

        output = hoursD + " : " + minutesD + " : " + secondsD;
        return output;
    }

    public TimerCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public static void setTimeNull(){
        timeleft = 0L;
    }

    public static String getTime(){
        return formatTime(timeleft);
    }

    @Override
    public void onTick(long l) {
        timeleft = l;
    }

    @Override
    public void onFinish() {

    }
}
