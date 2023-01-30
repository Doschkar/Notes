package com.example.notepad2;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.notepad2.databinding.FragmentListBinding;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    MainViewModel model;
    private final List<Note> mValues;
    private ItemClickListener mItemClickListener;

    public MyItemRecyclerViewAdapter(List<Note> items, ItemClickListener mItemClickListener) {
        mValues = items;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle());
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mTextView.setText(mValues.get(position).getNote());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

        /*
        if(model.settingDate){
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        }
        */

        holder.mDateTime.setText(LocalDateTime.of(Instant.ofEpochMilli(mValues.get(position).getDate()).atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.of(mValues.get(position).getHours(), mValues.get(position).getMinutes())).format(formatter));

        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(mValues.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemClickListener{
        void onItemClick(Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mTextView;
        public final TextView mDateTime;

        public Note mItem;

        public ViewHolder(FragmentListBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mTextView = binding.text3;
            mDateTime = binding.dateTime;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}