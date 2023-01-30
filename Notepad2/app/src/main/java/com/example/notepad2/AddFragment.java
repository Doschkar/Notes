package com.example.notepad2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements View.OnClickListener{
    MainViewModel model;

    TextInputEditText titleInputText;
    TextInputEditText descriptionInputText;
    TextInputEditText noteInputText;
    Button saveButton;
    Button goBackButton;
    FloatingActionButton calender;
    FloatingActionButton time;

    public AddFragment() {
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        super.onCreate(savedInstanceState);


        titleInputText = view.findViewById(R.id.tiTitle);
        descriptionInputText = view.findViewById(R.id.tiDescription);
        noteInputText = view.findViewById(R.id.tiNote);
        saveButton = view.findViewById(R.id.bSave);
        saveButton.setOnClickListener(this);
        goBackButton = view.findViewById(R.id.bGoBack);
        goBackButton.setOnClickListener(this);
        calender = view.findViewById(R.id.fabCalender);
        calender.setOnClickListener(this);
        time = view.findViewById(R.id.fabTime);
        time.setOnClickListener(this);

        return view;
    }

    long date = 0;
    int hour = 0;
    int minute = 0;

    @Override
    public void onClick(View view) {
        //AtomicLong date = new AtomicLong();
        //AtomicInteger hour = new AtomicInteger();
        //AtomicInteger minute = new AtomicInteger();

        if(view.getId() == R.id.bGoBack){
            model.showMainScreen();
        }
        if(view.getId() == R.id.fabCalender){
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build();

            datePicker.show(requireActivity().getSupportFragmentManager(), "");
            datePicker.addOnPositiveButtonClickListener(b -> {
                date = datePicker.getSelection();
            });
        }
        if(view.getId() == R.id.fabTime){
            MaterialTimePicker picker =
                    new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(12)
                            .setMinute(10)
                            .setTitleText("Select time")
                            .build();

            picker.show(requireActivity().getSupportFragmentManager(), "");
            picker.addOnPositiveButtonClickListener(b -> {
                hour = picker.getHour();
                minute = picker.getMinute();
            });
        }

        if(view.getId() == R.id.bSave){
            model.notes.add(new Note(titleInputText.getText().toString(), descriptionInputText.getText().toString(), noteInputText.getText().toString(), date, hour, minute, false));
            Gson gson = new Gson();
            String json = gson.toJson(model.notes);
            JsonHandler.write(requireContext(), json);
        }
    }
}