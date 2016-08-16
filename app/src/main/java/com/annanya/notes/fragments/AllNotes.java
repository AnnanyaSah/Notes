package com.annanya.notes.fragments;


import android.app.Activity;

import android.support.v4.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.annanya.notes.R;
import com.annanya.notes.classes.AllNotesRecyclerAdapter;
import com.annanya.notes.classes.Config;
import com.annanya.notes.classes.DatabaseManager;
import com.annanya.notes.classes.NoteData;

import java.util.ArrayList;
import java.util.List;

public class AllNotes extends Fragment {
    RecyclerView mRecyclerView;
    List<NoteData> data;
    DatabaseManager dm=null;

    AllNotesRecyclerAdapter.onSeletedNoteListener mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_notes, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        TextView text=(TextView)rootView.findViewById(R.id.textrecyclerview);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        filldata();
        if(data==null){
            text.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else{
            text.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        AllNotesRecyclerAdapter mAdapter = new AllNotesRecyclerAdapter(data, getActivity());
        mRecyclerView.setAdapter(mAdapter);


         FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note obj=new Note();
                FragmentManager manager= getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.fragment_container,obj);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;

    }


    public void filldata() {
        data= new ArrayList<>();
        dm=new DatabaseManager(getActivity());
        dm.OpenDb();
        Cursor c=dm.viewData();
        while(c.moveToNext())
        {
            int id=c.getInt(c.getColumnIndex(Config.PID));
            String name=c.getString(c.getColumnIndex(Config.PTITLE));
            String cost=c.getString(c.getColumnIndex(Config.PTEXT));
            data.add(new NoteData(name,cost,id));
        }

    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AllNotesRecyclerAdapter.onSeletedNoteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
