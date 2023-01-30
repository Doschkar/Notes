package com.example.notepad2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        SettingsHandler handler = new SettingsHandler();
        boolean[] settings = handler.readSettings(getApplicationContext());
        model.settingDate = settings[0];
        model.settingExpired = settings[1];
        model.settingDone = settings[2];
        model.readJson(getApplicationContext());
        model.state.observe(this, state -> {
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            if(state == MainViewModel.SHOW_MAIN){
                fragTransaction.replace(R.id.cl_main, ListFragment.newInstance(1) , "Main");
            } else if(state == MainViewModel.SHOW_ADD) {
                fragTransaction.replace(R.id.cl_main, AddFragment.newInstance("", "") , "Add");
                fragTransaction.addToBackStack("Main");
            } else if(state == MainViewModel.SHOW_NOTE) {
                fragTransaction.replace(R.id.cl_main, NoteFragment.newInstance("", "") , "Note");
                fragTransaction.addToBackStack("Main");
            } else if(state == MainViewModel.SHOW_SETTING){
                fragTransaction.replace(R.id.cl_main, SettingsFragment.newInstance("", ""), "Settings");
                fragTransaction.addToBackStack("Main");
            } else if(state == MainViewModel.SHOW_EDIT) {
                fragTransaction.replace(R.id.cl_main, EditFragment.newInstance(), "Edit");
                fragTransaction.addToBackStack("Main");
            }else if(state == MainViewModel.BACK){
                getSupportFragmentManager().popBackStack();
            }
            fragTransaction.commit();
        });
    }
}