package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    List<Note> noteList = new ArrayList<>();
//    private final ListItemClickListener itemClickListener;

//    public interface ListItemClickListener {
//        void onListItemClick(int position);
//    }
//
//    public RecyclerViewAdapter(ListItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.textViewText.setText(note.getNoteText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setData(List<Note> data) {
        this.noteList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.noteList.clear();
        notifyDataSetChanged();
    }

    public Note getItemPosition(int position) {
        return this.noteList.get(position);
    }

    public List<Note> getItemList() {
        return this.noteList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        TextView textViewText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewText = itemView.findViewById(R.id.item_text);
//            itemView.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            itemClickListener.onListItemClick(position);
//
//        }
    }

}
