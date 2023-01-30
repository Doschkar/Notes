package com.example.notepad2;

public class Note {
    private String title;
    private String description;
    private String note;
    private long date;
    private int hours;
    private int minutes;
    private boolean done;

    public Note(String title, String description, String note, long date, int hours, int minutes, boolean done){
        this.title = title;
        this.description = description;
        this.note = note;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
    }

    public long getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean isDone() {
        return done;
    }

    public void setOis(String title, String description, String note, long date, int hours, int minutes, boolean done){
        setTitle(title);
        setDescription(description);
        setNoteEdit(note);
        setDate(date);
        setHours(hours);
        setMinutes(minutes);
        setDone(done);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNoteEdit(String note) {
        this.note = note;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return title + " - " + description + " - " + note + " - " + date + " - " + hours + " - " + minutes + " - " + done;
    }
}
