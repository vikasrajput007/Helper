package com.example.helper.user_section;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.OtpScreen;
import com.example.helper.R;
import com.example.helper.VendorRegistraion;
import com.example.helper.Vendor_Home_API_Client;
import com.example.helper.Vendor_Register_Interface;
import com.example.helper.basic_iterfaces.IntentInterfaces;
import com.example.helper.models.RegisterResponse;
import com.example.helper.utils.Constants;
import com.example.helper.utils.GPSTracker;
import com.example.helper.utils.LocationReceiver;
import com.example.helper.utils.MySharedData;
import com.google.android.gms.location.FusedLocationProviderClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.MOBILE_NUMBER;

public class UserRegistraion extends AppCompatActivity implements View.OnClickListener, IntentInterfaces, LocationReceiver.LocationReceiverListener {
    private static final int EXTERNAL_LOCATION_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Context context;
    ConstraintLayout progress_constraint;
    Vendor_Register_Interface apiInterface;
    EditText et_name, et_mobile;
    Button register_as_vendor, register_as_user;
    String lattitude, longitude;

//    boolean gps_status = false;
    Snackbar snackbar;
    //    ConnectionDetector connectionDetector;
    LocationReceiver locationReceiver;

    FusedLocationProviderClient mFusedLocationClient;
    GPSTracker gps;
    //    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registraion);
        context = UserRegistraion.this;
        gps = new GPSTracker(context);

        initView();
    }


    private void initView() {
        progress_constraint = findViewById(R.id.progress_constraint);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        register_as_user = findViewById(R.id.register_as_user);
        register_as_vendor = findViewById(R.id.register_as_vendor);
        register_as_user.setOnClickListener(this);
        register_as_vendor.setOnClickListener(this);

        if (!gps.canGetLocation()) {
            noInternetMethod();
        }


        // Runtime permission for Location for above api level 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            getLocatinPermission();
        }

    }

    private void getLocatinPermission() {


        if (ActivityCompat.checkSelfPermission(UserRegistraion.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserRegistraion.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(UserRegistraion.this);
                builder.setTitle("Need Location Permission");
                builder.setMessage("This app needs location permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(UserRegistraion.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, EXTERNAL_LOCATION_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (MySharedData.getGeneralSaveSession2(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(UserRegistraion.this);
                builder.setTitle("Need Location Permission");
                builder.setMessage("This app needs location permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        ActivityCompat.requestPermissions(UserRegistraion.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, EXTERNAL_LOCATION_PERMISSION_CONSTANT);

                        /**
                         *    this code is use to show the appllication permission setting screen
                         */

//                                                sentToSettings = true;
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(UserRegistraion.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, EXTERNAL_LOCATION_PERMISSION_CONSTANT);
            }

            MySharedData.setGeneralSaveSession2(Manifest.permission.ACCESS_FINE_LOCATION, true);

        }
    }

    //    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_LOCATION_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Now, We can access your location", Toast.LENGTH_SHORT).show();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(UserRegistraion.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegistraion.this);
                    builder.setTitle("Need Location Permission");
                    builder.setMessage("This app needs location permission.");

                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(UserRegistraion.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, EXTERNAL_LOCATION_PERMISSION_CONSTANT);
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(UserRegistraion.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
//                proceedAfterPermission();
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void registerUser() {
        progress_constraint.setVisibility(View.VISIBLE);
        register_as_user.setVisibility(View.GONE);
        register_as_vendor.setVisibility(View.GONE);
        apiInterface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);


        Call<RegisterResponse> registerResponseCall = apiInterface.registerUser(et_name.getText().toString().trim(), et_mobile.getText().toString().trim(), lattitude, longitude);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progress_constraint.setVisibility(View.GONE);
                register_as_user.setVisibility(View.VISIBLE);
                register_as_vendor.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {

                    MySharedData.setGeneralSaveSession(MOBILE_NUMBER, et_mobile.getText().toString().trim());

                    if (response.body().getMessage().equals("New User Registered Successfully")) {
                        Toast.makeText(context, "Registered Successfully, Please wait for verification", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body().getMessage().equals("User Information Updated Successfully")) {
                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(UserRegistraion.this, OtpScreen.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progress_constraint.setVisibility(View.GONE);
                register_as_user.setVisibility(View.VISIBLE);
                register_as_vendor.setVisibility(View.VISIBLE);
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_as_user:
                if (et_name.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Username", Toast.LENGTH_SHORT).show();
                } else if (et_mobile.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Your Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (!et_mobile.getText().toString().matches(Constants.MobilePattern)) {
                    Toast.makeText(this, "Please Type Correct Mobile Number", Toast.LENGTH_SHORT).show();
                } else {

                    if (gps.canGetLocation()) {
                        Location location = gps.getLocation();
                        lattitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());

                        MySharedData.setGeneralSaveSession(LATTITUDE, lattitude);
                        MySharedData.setGeneralSaveSession(LONGITUDE, longitude);
                        registerUser();
                    } else {
                        Toast.makeText(this, "Please Enable your GPS, To Access Your Location", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.register_as_vendor:
                switchActivity();
                break;

            default:
                break;
        }
    }

    @Override
    public void switchActivity() {
        Intent intent = new Intent(UserRegistraion.this, VendorRegistraion.class);
        startActivity(intent);
        finish();
    }

    private void noInternetMethod() {
        showSnack("GPS IS NOT ENABLED !", "ENABLE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
    }

    private void showSnack(String msg, String actionString, View.OnClickListener onClickListener) {
        snackbar = Snackbar.make(findViewById(R.id.register_co_ordinator_layout), msg, Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#2665AD"));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(14);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "roboto.medium-italic.ttf");
        textView.setTypeface(custom_font);
        snackbar.setAction(actionString, onClickListener);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registeredGpsBroadcast();
    }

    private void registeredGpsBroadcast() {
        locationReceiver = new LocationReceiver();
        LocationReceiver.locationReceiverListener = UserRegistraion.this;
        IntentFilter fp = new IntentFilter();
        fp.addAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        fp.setPriority(28);
        registerReceiver(locationReceiver, fp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(locationReceiver);
//        stopUsingGPS();
    }


    @Override
    public void onLocationGetting(boolean gps_enabled) {
//        gps_status = gps_enabled;
        if (gps_enabled) {
            if (snackbar != null) {
                snackbar.dismiss();
            }
        } else {
            noInternetMethod();
        }
    }
}
