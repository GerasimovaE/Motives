package com.example.todolist.screens.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Note;

import java.util.List;
import java.util.Random;

public class MotivesFragment extends Fragment {

    TextView textViewMotives;
    Button buttonMain;
    final Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_motives, container, false);

        textViewMotives = view.findViewById(R.id.textMotives);
        List<Note> list = App.getInstance().getNoteDao().getAll();
        Note notNew = App.getInstance().getNoteDao().findById(random.nextInt(10) + 1);
        textViewMotives.setText(notNew.text);
        buttonMain = view.findViewById(R.id.buttonMainMenu);
        TimerCountDown timer = new TimerCountDown(21_600_000L, 1000L);
//        TimerCountDown timer = new TimerCountDown(70_000L, 1000L);
        timer.start();

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment nextFrag= new MainFragment(true);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (isRemoving()) {
            Fragment nextFrag= new MainFragment(true);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, nextFrag,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
