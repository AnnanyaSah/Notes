package com.annanya.notes.classes;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.annanya.notes.MainActivity;
import com.annanya.notes.R;
import com.annanya.notes.fragments.Note;

import java.util.Collections;
import java.util.List;



public class AllNotesRecyclerAdapter extends RecyclerView.Adapter<AllNotesViewHolder> {

    public interface onSeletedNoteListener{
        public void onNoteSelected(int id);
    }

    Button editNote;

    List<NoteData> list = Collections.emptyList();
    Context context;
    DatabaseManager dm=null;
    public AllNotesRecyclerAdapter(List<NoteData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AllNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        TextView text=(TextView)v.findViewById(R.id.textView2);
        EditText edit=(EditText)v.findViewById(R.id.editText);
        edit.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);

        TextView textView=(TextView)v.findViewById(R.id.textView);
        EditText edittoolbar=(EditText)v.findViewById(R.id.edittoolbar);

        edittoolbar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);

        editNote=(Button)v.findViewById(R.id.button);
        editNote.setVisibility(View.VISIBLE);


        AllNotesViewHolder holder = new AllNotesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AllNotesViewHolder holder, final int position) {
       final MainActivity mc=(MainActivity)context;
        dm=new DatabaseManager(mc);
        holder.title1.setText(list.get(position).title);
        holder.id1.setText(list.get(position).text);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.id=String.valueOf(list.get(position).id);
                Note obj=new Note();
                android.support.v4.app.FragmentManager manager=mc.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.fragment_container,obj);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dm.OpenDb();
                dm.deleteData(list.get(position).id);
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });

    }

    @Override
    public int getItemCount() {

        if(list==null)
            return 0;
        else
        return list.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, NoteData data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(NoteData data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    //recycler item touch
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }





}