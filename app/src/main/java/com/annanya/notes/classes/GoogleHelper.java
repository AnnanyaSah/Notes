package com.annanya.notes.classes;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleHelper implements GoogleApiClient.OnConnectionFailedListener {
	public static GoogleSignInOptions gso;
	public static GoogleApiClient mGoogleApiClient;

	public   static GoogleApiClient test(FragmentActivity fa){

		gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		mGoogleApiClient = new GoogleApiClient.Builder(fa)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

		return mGoogleApiClient;


	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}
}
