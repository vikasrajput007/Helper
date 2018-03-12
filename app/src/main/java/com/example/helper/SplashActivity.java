package com.example.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.basic_iterfaces.IntentInterfaces;
import com.example.helper.models.DeviceRegisterResponse;
import com.example.helper.user_section.UserHomeActivity;
import com.example.helper.user_section.UserRegistraion;
import com.example.helper.utils.ConnectionDetector;
import com.example.helper.utils.ConnectivityReceiver;
import com.example.helper.utils.MySharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.DEVICE_UNIQUE_ID;
import static com.example.helper.utils.Constants.FIREBASE_TOKEN;
import static com.example.helper.utils.Constants.MOBILE_NUMBER;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID;

public class SplashActivity extends AppCompatActivity implements IntentInterfaces, IntentInterfaces.InnerInterface,ConnectivityReceiver.ConnectivityReceiverListener {
    Snackbar snackbar;
    ConnectionDetector connectionDetector;
    ConnectivityReceiver connectivityReceiver;
//    ProgressBar splash_progressBar;
    Handler handler;
    String device_unique_id;
    Vendor_Register_Interface vendor_register_interface;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        splash_progressBar = findViewById(R.id.splash_progressBar);
        connectionDetector = new ConnectionDetector(SplashActivity.this);
        handler = new Handler();
        device_unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        MySharedData.setGeneralSaveSession(DEVICE_UNIQUE_ID, device_unique_id);
        System.out.println("device_id" + device_unique_id);
//        switchActivity();
    }

    @Override
    public void switchActivity() {
        if (MySharedData.getGeneralSaveSession(DEVICE_UNIQUE_ID).equals("")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendDeviceInfo();
                }
            }, 4000);
        } else if (MySharedData.getGeneralSaveSession(MOBILE_NUMBER).equals("")) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToActivity(SplashActivity.this, UserRegistraion.class);
                }
            }, 2500);
        } else {

            String mobile_number = MySharedData.getGeneralSaveSession(MOBILE_NUMBER);
            String vendor_service_id = MySharedData.getGeneralSaveSession(VENDOR_ID);
            if (!MySharedData.getGeneralSaveSession(MOBILE_NUMBER).equals("") && MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID).equals("")) {

//                userid  and otp  validation is here
                if (MySharedData.getGeneralSaveSession(USER_ID).equals("")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goToActivity(SplashActivity.this, OtpScreen.class);
                        }
                    }, 2500);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goToActivity(SplashActivity.this, UserHomeActivity.class);
                        }
                    }, 2500);

                }

            } else if (!MySharedData.getGeneralSaveSession(MOBILE_NUMBER).equals("") && !MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID).equals("")) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        goToActivity(SplashActivity.this, Home_Screen_Vendor.class);
//
                    }
                }, 2500);
            }
        }

    }

    private void sendDeviceInfo() {
        try {
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            String token = MySharedData.getGeneralSaveSession(FIREBASE_TOKEN);

            Call<DeviceRegisterResponse> homeDataCall = vendor_register_interface.regosterDevice("0", device_unique_id, MySharedData.getGeneralSaveSession(FIREBASE_TOKEN), "0");
            homeDataCall.enqueue(new Callback<DeviceRegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeviceRegisterResponse> call, @NonNull Response<DeviceRegisterResponse> response) {

                    if (response.isSuccessful()) {
                        MySharedData.setGeneralSaveSession(DEVICE_UNIQUE_ID, device_unique_id);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goToActivity(SplashActivity.this, UserRegistraion.class);
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(SplashActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeviceRegisterResponse> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void goToActivity(Activity activity, Class reference_activity) {
        Intent i = new Intent(activity, reference_activity);
        startActivity(i);
        finish();
    }

    private void noInternetMethod() {
        showSnack("INTERNET IS NOT AVAILABLE", "Connect", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
    }

    private void showSnack(String msg, String actionString, View.OnClickListener onClickListener) {
        snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), msg, Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        textView.setTextSize(14);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "roboto.medium-italic.ttf");
        textView.setTypeface(custom_font);
        snackbar.setAction(actionString, onClickListener);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registeredNetBroadcast();
    }
    
    private void registeredNetBroadcast() {
        connectivityReceiver = new ConnectivityReceiver();
        ConnectivityReceiver.connectivityReceiverListener = SplashActivity.this;
        IntentFilter fp = new IntentFilter();
        fp.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        fp.setPriority(28);
        registerReceiver(connectivityReceiver, fp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityReceiver);
    }
    
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            if (snackbar != null) {
                snackbar.dismiss();
                switchActivity();
            }
            else{
                switchActivity();
            }
        } else {
            noInternetMethod();
        }
    }

}
