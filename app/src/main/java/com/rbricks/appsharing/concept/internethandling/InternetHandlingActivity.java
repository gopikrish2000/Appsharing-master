package com.rbricks.appsharing.concept.internethandling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.Application.AppSharingApplication;

public class InternetHandlingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_handling);
        isNetworkConnectivity();
    }

    public static boolean isNetworkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) AppSharingApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
