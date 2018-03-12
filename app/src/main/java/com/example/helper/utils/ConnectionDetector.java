package com.example.helper.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.example.helper.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectionDetector
{
	private Context context;
	
	public ConnectionDetector(Context context)
	{
		this.context = context;
	}

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean getNetAvailabilityWithDialog() {
        boolean b = !isNetworkAvailable();
        if (b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View v = ((Activity) context).getLayoutInflater().inflate(R.layout.connectivity_dialog, null);
            TextView try_again = v.findViewById(R.id.try_again);
            builder.setView(v);
            final AlertDialog dialog = builder.create();
            dialog.show();
            try_again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return b;
    }
}