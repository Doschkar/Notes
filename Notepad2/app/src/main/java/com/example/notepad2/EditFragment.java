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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    MainViewModel model;

    TextInputEditText title;
    TextInputEditText description;
    TextInputEditText note;
    Switch done;

    public EditFragment() {}

    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    long date = 0;
    int hour = 0;
    int minute = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        title = view.findViewById(R.id.tiTitle);
        description = view.findViewById(R.id.tiDescription);
        note = view.findViewById(R.id.tiNote);
        done = view.findViewById(R.id.switchDone);

        view.findViewById(R.id.fabCalender7).setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build();

            datePicker.show(requireActivity().getSupportFragmentManager(), "");
            datePicker.addOnPositiveButtonClickListener(b -> {
                date = datePicker.getSelection();
            });
        });

        view.findViewById(R.id.fabTime7).setOnClickListener(v -> {
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
        });

        view.findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            model.notes.remove(model.notes.indexOf(model.editNote));
            Gson gson = new Gson();
            String json = gson.toJson(model.notes);
            JsonHandler.write(requireContext(), json);
        });

        view.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            model.notes.get(model.notes.indexOf(model.editNote)).setOis(title.getText().toString(), description.getText().toString(), note.getText().toString(), date, hour, minute, done.isChecked());
            Gson gson = new Gson();
            String json = gson.toJson(model.notes);
            JsonHandler.write(requireContext(), json);
        });

        return view;
    }
}