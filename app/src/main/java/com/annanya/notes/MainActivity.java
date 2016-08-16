package com.annanya.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.annanya.notes.classes.AllNotesRecyclerAdapter;
import com.annanya.notes.classes.Config;
import com.annanya.notes.fragments.AllNotes;
import com.annanya.notes.fragments.SignIn;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements AllNotesRecyclerAdapter.onSeletedNoteListener, SignIn.OnLogin{
    GoogleApiClient mGoogleApiClient;

    public static String id=null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     setContentView(R.layout.activity_main);
     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     setSupportActionBar(toolbar);

     preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
     editor = preferences.edit();
        if(!preferences.getBoolean(Config.LOGGEDIN_SHARED_PREF,false)){
            openSignInFragment();
        }
        else{
            populateNote();
         }
        }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
        if (id == R.id.power) {
            if(mGoogleApiClient!=null) {
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
               }
            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
            openSignInFragment();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void isLoggedIn(boolean val) {
        if(val)
        populateNote();

    }




    @Override
    public GoogleApiClient setClient(GoogleApiClient g) {
        mGoogleApiClient=g;
        return g;
    }

    public void populateNote(){
        AllNotes firstFragment = new AllNotes();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fragment_container,firstFragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }

    }


    public void openSignInFragment(){
        SignIn firstFragment = new SignIn();
        firstFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment).commit();
    }

    @Override
    public void onNoteSelected(int idn) {

    }


}
