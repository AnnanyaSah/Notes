package com.annanya.notes.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.annanya.notes.MainActivity;
import com.annanya.notes.R;
import com.annanya.notes.classes.DatabaseManager;
import com.annanya.notes.classes.NoteData;

public class Note extends Fragment {

    Button delete;
    Button save;
    Button editbt;
    EditText edit;
    TextView text;
    EditText edittoolbar;
    TextView textView;
    TextView text2;

    DatabaseManager dm=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dm=new DatabaseManager(getActivity());
        dm.OpenDb();

        View v=inflater.inflate(R.layout.note, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        getActivity().invalidateOptionsMenu();
        edit=(EditText)v.findViewById(R.id.editText);
        text=(TextView)v.findViewById(R.id.textView2);
        edittoolbar=(EditText)v.findViewById(R.id.edittoolbar);
        textView=(TextView)v.findViewById(R.id.textView);
        text2=(TextView)v.findViewById(R.id.textView);

        edit.setVisibility(View.VISIBLE);
        text.setVisibility(View.GONE);
        edittoolbar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        save=(Button)v.findViewById(R.id.button);
        delete=(Button)v.findViewById(R.id.button2);
        editbt=(Button)v.findViewById(R.id.buttonedit);
        save.setBackgroundResource(R.drawable.content_save);

        if(MainActivity.id !=null){

                NoteData n=dm.getNote(String.valueOf(MainActivity.id));
                edit.setText(n.text);
                edittoolbar.setText(n.title);

            editbt.setVisibility(View.VISIBLE);

            save.setVisibility(View.GONE);

        }
        else{
            editbt.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        }

        editbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l=dm.updateData(String.valueOf(MainActivity.id), edit.getText().toString(),edittoolbar.getText().toString());
                if(l>0)
                {
                    Toast.makeText(getActivity(), "Updated",Toast.LENGTH_LONG).show();
                    MainActivity.id=null;
                    getFragmentManager().popBackStack();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l=dm.insertData(edittoolbar.getText().toString(), edit.getText().toString());
                if(l>0)
                {
                    Toast.makeText(getActivity(), "Saved",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Discarded",Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        });
        return v;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();    //remove all items

    }

    @Override
    public void onResume() {

        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getFragmentManager();
        Toast.makeText(getActivity(),"Discarded",Toast.LENGTH_SHORT).show();
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        fm.popBackStack();
    }
}
