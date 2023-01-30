package com.example.notepad2;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    public static final int DEFAULT = 0;
    public static final int SHOW_MAIN = 1;
    public static final int SHOW_ADD = 2;
    public static final int SHOW_NOTE = 3;
    public static final int SHOW_SETTING = 4;
    public static final int SHOW_EDIT = 5;
    public static final int BACK = 100;

    public Note editNote;
    public boolean settingDate;
    public boolean settingExpired;
    public boolean settingDone;

    public List<Note> notes = new ArrayList<>();
    private MutableLiveData<Integer> _state = new MutableLiveData<>(SHOW_MAIN);
    public LiveData<Integer> state = _state;

    public void showMainScreen(){
        _state.postValue(SHOW_MAIN);
    }

    public void showAddScreen(){
        _state.postValue(SHOW_ADD);
    }

    public void showNoteScreen(){
        _state.postValue(SHOW_NOTE);
    }

    public void showSettings() { _state.postValue(SHOW_SETTING); }

    public void showEdit() {
        _state.postValue(SHOW_EDIT);
    }

    public void back(){
        _state.postValue(BACK);
    }

    public void readJson(Context context){
        Gson gson = new Gson();
        String json = JsonHandler.read(context);
        TypeToken<List<Note>> collectionType = new TypeToken<List<Note>>(){};
        if(gson.fromJson(json, collectionType) != null){
            notes = gson.fromJson(json, collectionType);
        }
    }


}
