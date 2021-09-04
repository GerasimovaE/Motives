package com.example.todolist.screens.main;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.data.AppDatabase;
import com.example.todolist.model.Note;
import com.example.todolist.screens.details.NoteDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Note note = new Note();
    SharedPreferences prefs = null;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        prefs = getSharedPreferences("com.example.todolist", MODE_PRIVATE);

        setContentView(R.layout.activity_main);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }

//        button = findViewById(R.id.button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My notification")
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("Напоминание")
//                        .setContentText("Пора покормить кота")
//                        .setAutoCancel(true);
//
//                NotificationManagerCompat managerCompat= NotificationManagerCompat.from(MainActivity.this);
//                managerCompat.notify(1, builder.build());
//
//            }
//        });



        Fragment frag1 = new MainFragment(false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_frame, frag1);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        //ignore
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

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}