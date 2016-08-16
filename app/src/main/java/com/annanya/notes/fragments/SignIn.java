package com.annanya.notes.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annanya.notes.R;
import com.annanya.notes.classes.Config;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class SignIn extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    private SignInButton signInButton;
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 100;


    OnLogin mCallback;
    public interface OnLogin {
        void isLoggedIn(boolean val);
        GoogleApiClient setClient(GoogleApiClient g);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mGoogleApiClient.isConnected())
        Toast.makeText(getActivity(),"here",Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sign_in, container, false);
        signInButton=(SignInButton)rootView.findViewById(R.id.sign_in_button);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return rootView;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())

            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
    /*if(mGoogleApiClient.isConnected())
    mGoogleApiClient.clearDefaultAccountAndReconnect();
*/

        signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else{
            Toast.makeText(getActivity(),"Login unsucessful",Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnLogin) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLogin");
        }


    }


    private void handleSignInResult(GoogleSignInResult result) {
                if (result.isSuccess()) {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.commit();

                    mCallback.isLoggedIn(true);
                    mCallback.setClient(mGoogleApiClient);

        } else {
            Toast.makeText(getActivity(),"Sign in failed! Please try again later",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    @Override
    public void onPause() {
        super.onPause();
       mGoogleApiClient.disconnect();
    }

}
