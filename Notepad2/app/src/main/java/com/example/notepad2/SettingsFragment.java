package com.example.notepad2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment{
    MainViewModel model;

    Switch date;
    Switch expired;
    Switch done;

    public SettingsFragment() {}

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        super.onCreate(savedInstanceState);

        date = view.findViewById(R.id.dateSwitch);
        expired = view.findViewById(R.id.expiredSwitch);
        done = view.findViewById(R.id.doneSwitch);

        date.setChecked(model.settingDate);
        expired.setChecked(model.settingExpired);
        done.setChecked(model.settingDone);

        view.findViewById(R.id.settingsSave).setOnClickListener(v -> {
            model.settingDate = date.isChecked();
            model.settingExpired = expired.isChecked();
            model.settingDone = done.isChecked();

            boolean[] settings = {date.isChecked(), expired.isChecked(), done.isChecked()};
            SettingsHandler handler = new SettingsHandler();
            handler.writeSettings(requireContext(), settings);
        });

        return view;
    }
}