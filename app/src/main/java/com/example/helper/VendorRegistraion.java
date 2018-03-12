package com.example.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.helper.basic_iterfaces.IntentInterfaces;
import com.example.helper.models.DeviceRegisterResponse;
import com.example.helper.models.VendorResponseBean;
import com.example.helper.user_section.UserRegistraion;
import com.example.helper.utils.GPSTracker;
import com.example.helper.utils.MySharedData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.DEVICE_UNIQUE_ID;
import static com.example.helper.utils.Constants.FIREBASE_TOKEN;
import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.MOBILE_NUMBER;
import static com.example.helper.utils.Constants.VENDOR_ID;
import static com.example.helper.utils.Constants.VENDOR_NAME;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID;

public class VendorRegistraion extends AppCompatActivity implements View.OnClickListener, IntentInterfaces.InnerInterface {

    Button buttoncontinue;
    EditText vendor_name, vendor_mobile_no;
    Spinner spinner_services;
    ConstraintLayout progress_constraint;
    ArrayAdapter<String> servicelist_adapter;
    String serviceposition, serviceids;
    String name, mobileno;
    ArrayList<String> service_list;
    Vendor_Register_Interface api_interface;
    Handler handler;
    String lattitude, longitude;
    GPSTracker gps;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_registraion);
        context = VendorRegistraion.this;


        gps = new GPSTracker(context);

        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        }
        progress_constraint = findViewById(R.id.progress_constraint);
        handler = new Handler();
        spinner_services = findViewById(R.id.spinner_servicename);
        vendor_mobile_no = findViewById(R.id.et_mobile);
        vendor_name = findViewById(R.id.et_name);
        buttoncontinue = findViewById(R.id.submit_vendor_detail);
        buttoncontinue.setOnClickListener(this);
        gps = new GPSTracker(this);
        service_list = new ArrayList<>();
        service_list.add("Service Name");
        service_list.add("Electrician");
        service_list.add("Plumber");
        service_list.add("Carpenter");
        service_list.add("Dry Cleaning");
        service_list.add("Water Purifying");
        service_list.add("Hire Taxi");

        servicelist_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, service_list);

        servicelist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_services.setAdapter(servicelist_adapter);
        servicelist_adapter.notifyDataSetChanged();
        spinner_services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String spinnerValue = String.valueOf(spinner_services.getSelectedItem().toString());

                if (position == 0) {
//                    System.out.println("Position is" + position);

//                    Toast.makeText(VendorRegistraion.this, "Please Select Your Service Type", Toast.LENGTH_SHORT).show();
                } else {
//                    System.out.println("Position is" + position);
                    serviceposition = String.valueOf(position);
                    MySharedData.setGeneralSaveSession("Service_position", serviceposition);
//                    Toast.makeText(VendorRegistraion.this, "position" + serviceposition, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttoncontinue) {
            name = vendor_name.getText().toString().trim();
            mobileno = vendor_mobile_no.getText().toString().trim();


            if (name.equals("") && mobileno.equals("")) {
                Toast.makeText(VendorRegistraion.this, "Please fill all values", Toast.LENGTH_SHORT).show();
            } else if (name.equals("")) {
                vendor_name.setError("Please enter Name");
                requestFocus(vendor_name);
            } else if (mobileno.equals("")) {
                vendor_mobile_no.setError("Please Enter Contact No.");
                requestFocus(vendor_mobile_no);
            } else if (mobileno.length() != 10) {
                vendor_mobile_no.setError("Please enter Valid Contact No.");
                requestFocus(vendor_mobile_no);
            } else {
//                MySharedData.setGeneralSaveSession("vendor_name", name);
//                MySharedData.setGeneralSaveSession("vendor_mobile", mobileno);
                serviceids = String.valueOf(MySharedData.getGeneralSaveSession("Service_position"));
//                Toast.makeText(this, "position is" + serviceids, Toast.LENGTH_SHORT).show();
                register_Vendor();
            }
        }

    }

    public void register_Vendor() {

        if (gps.canGetLocation()) {

            Location lcoation = gps.getLocation();
            lattitude = String.valueOf(lcoation.getLatitude());
            longitude = String.valueOf(lcoation.getLongitude());

            MySharedData.setGeneralSaveSession(LATTITUDE, lattitude);
            MySharedData.setGeneralSaveSession(LONGITUDE, longitude);

        }


//        String latttt =  MySharedData.getGeneralSaveSession(LATTITUDE);
//        String longgg =  MySharedData.getGeneralSaveSession(LONGITUDE);

        progress_constraint.setVisibility(View.VISIBLE);
        buttoncontinue.setVisibility(View.GONE);
        api_interface = Vendor_Home_API_Client.getApiClient().create(Vendor_Register_Interface.class);
        Call<VendorResponseBean> VendorResponseBeanCall = api_interface.saveData(mobileno, name, serviceids,MySharedData.getGeneralSaveSession(LATTITUDE),MySharedData.getGeneralSaveSession(LONGITUDE));
        VendorResponseBeanCall.enqueue(new Callback<VendorResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<VendorResponseBean> call, @NonNull Response<VendorResponseBean> response) {
                if (response.isSuccessful()) {

                    MySharedData.setGeneralSaveSession(MOBILE_NUMBER, mobileno);
                    MySharedData.setGeneralSaveSession(VENDOR_ID, response.body().getVendorid());
                    MySharedData.setGeneralSaveSession(VENDOR_NAME, response.body().getName());
                    MySharedData.setGeneralSaveSession(VENDOR_SERVICE_ID, serviceids);
                    sendDeviceInfo(response.body().getVendorid());
                }
            }

            @Override
            public void onFailure(Call<VendorResponseBean> call, Throwable t) {
//                String exception = t.getMessage();
                buttoncontinue.setVisibility(View.VISIBLE);
                progress_constraint.setVisibility(View.GONE);
            }
        });
    }

    private void sendDeviceInfo(String userid) {
        try {
            api_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
//            String token = MySharedData.getGeneralSaveSession(FIREBASE_TOKEN);

            Call<DeviceRegisterResponse> homeDataCall = api_interface.regosterDevicevendor(MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID),userid, MySharedData.getGeneralSaveSession(DEVICE_UNIQUE_ID),
                    MySharedData.getGeneralSaveSession(FIREBASE_TOKEN), "2");
            homeDataCall.enqueue(new Callback<DeviceRegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeviceRegisterResponse> call, @NonNull Response<DeviceRegisterResponse> response) {

                    if (response.isSuccessful()) {
                        MySharedData.setGeneralSaveSession(DEVICE_UNIQUE_ID, MySharedData.getGeneralSaveSession(DEVICE_UNIQUE_ID));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                buttoncontinue.setVisibility(View.VISIBLE);
                                progress_constraint.setVisibility(View.GONE);
                                goToActivity(VendorRegistraion.this, Home_Screen_Vendor.class);
                            }
                        }, 1000);
                    } else {
                        if(!(VendorRegistraion.this).isFinishing()) {
                            Toast.makeText(VendorRegistraion.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DeviceRegisterResponse> call, Throwable t) {
                    if(!( VendorRegistraion.this).isFinishing()){
                    Toast.makeText(VendorRegistraion.this, "Something went wrong with on failure", Toast.LENGTH_SHORT).show(); }
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void goToActivity(Activity activity, Class refrence_activity) {
        Intent intent = new Intent(activity, refrence_activity);
        startActivity(intent);
        finish();
    }
}
