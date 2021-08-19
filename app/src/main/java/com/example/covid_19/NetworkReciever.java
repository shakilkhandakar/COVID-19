package com.example.covid_19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       try {
            if (isOnline(context)){
                Toast.makeText(context, "Network Connected", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
            }
       }catch (NullPointerException e){
           e.printStackTrace();
       }
    }
    public boolean isOnline(Context context){
        try {
            ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network=cm.getActiveNetworkInfo();
            return (network!=null && network.isConnected());
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}