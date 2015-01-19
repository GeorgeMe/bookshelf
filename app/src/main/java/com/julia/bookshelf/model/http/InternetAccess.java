package com.julia.bookshelf.model.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.julia.bookshelf.R;

/**
 * Created by Julia on 16.01.2015.
 */
public class InternetAccess {
    public static boolean isInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
    public static void showNoInternetConnection(Context context){
        Toast.makeText(context,R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
    }
}