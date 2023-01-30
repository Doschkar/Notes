package com.example.notepad2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {
    MainViewModel model;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_list_list, container, false);

        view.findViewById(R.id.fabAdd2).setOnClickListener(v -> {
            model.showAddScreen();
        });

        view.findViewById(R.id.fabSettings).setOnClickListener(v -> {
            model.showSettings();
        });

        view.findViewById(R.id.list).setOnClickListener(v -> {
            model.showNoteScreen();
        });

        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<Note> notesList = new ArrayList<>();

            for(int i = 0; i < model.notes.size(); i++){
                notesList.add(model.notes.get(i));
            }

            if(model.settingExpired){
                for(int i = 0; i < notesList.size(); i++){
                    LocalDateTime localDateTimeNote = LocalDateTime.of(Instant.ofEpochMilli(notesList.get(i).getDate()).atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.of(notesList.get(i).getHours(), notesList.get(i).getMinutes()));
                    if(LocalDateTime.now().isAfter(localDateTimeNote)){
                        notesList.remove(i);
                    }
                }
            }else if(model.settingDone) {
                for(int i = 0; i < notesList.size(); i++){
                    if(notesList.get(i).isDone()){
                        notesList.remove(i);
                    }
                }
            }
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(notesList, new MyItemRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(Note note) {
                    model.editNote = note;
                    model.showEdit();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}