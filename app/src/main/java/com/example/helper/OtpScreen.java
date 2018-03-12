package com.example.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.basic_iterfaces.IntentInterfaces;
import com.example.helper.models.DeviceRegisterResponse;
import com.example.helper.models.OtpResponse;
import com.example.helper.user_section.UserHomeActivity;
import com.example.helper.user_section.fragments.WhereYouWantServiceFragment;
import com.example.helper.utils.MySharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.DEVICE_UNIQUE_ID;
import static com.example.helper.utils.Constants.FIREBASE_TOKEN;
import static com.example.helper.utils.Constants.MOBILE_NUMBER;
import static com.example.helper.utils.Constants.MY_LOCATION;
import static com.example.helper.utils.Constants.USER_ID;

public class OtpScreen extends AppCompatActivity implements View.OnClickListener, IntentInterfaces.InnerInterface {
    EditText otpDigitOne, otpDigitTwo, otpDigitThree, otpDigitFour;
    Button verify_me;
    StringBuilder sb;
    String otp_is;
    TextView search_service_location;
    ConstraintLayout my_current_location;
    ConstraintLayout progress_constraint;
    Vendor_Register_Interface apiInterface;
    Handler handler;
    AlertDialog alertDialog;
    String device_unique_id;
    String user_id;
    boolean my_location = false;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        handler = new Handler();
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        search_service_location = findViewById(R.id.search_service_location);
        my_current_location = findViewById(R.id.my_current_location);
        progress_constraint = findViewById(R.id.progress_constraint);
        otpDigitOne = findViewById(R.id.otpDigitOne);
        otpDigitTwo = findViewById(R.id.otpDigitTwo);
        otpDigitThree = findViewById(R.id.otpDigitThree);
        otpDigitFour = findViewById(R.id.otpDigitFour);
        verify_me = findViewById(R.id.verify_me);
        verify_me.setOnClickListener(this);
        otpDigitOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                otpDigitTwo.requestFocus();
            }
        });
        otpDigitTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                otpDigitThree.requestFocus();
            }
        });
        otpDigitThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                otpDigitFour.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_me:

                if (otpDigitOne.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (otpDigitTwo.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (otpDigitThree.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (otpDigitFour.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Please Type Complete OTP", Toast.LENGTH_SHORT).show();
                }

//                else if (!(networkInfo == null) && !(networkInfo.isConnected()) && !(networkInfo.isAvailable())) {
//                    Toast.makeText(OtpScreen.this, "Please Check Internet Connection !", Toast.LENGTH_SHORT).show();
//                }

                else {

                    sb = new StringBuilder();
                    sb.append(otpDigitOne.getText().toString().trim());
                    sb.append(otpDigitTwo.getText().toString().trim());
                    sb.append(otpDigitThree.getText().toString().trim());
                    sb.append(otpDigitFour.getText().toString().trim());
                    otp_is = sb.toString();
                    getVerified(otp_is);
                }
        }
    }

    private void getVerified(String otp) {
        progress_constraint.setVisibility(View.VISIBLE);
        verify_me.setVisibility(View.GONE);
        try {
            apiInterface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<OtpResponse> OtpResponseCall = apiInterface.getVerified(MySharedData.getGeneralSaveSession(MOBILE_NUMBER), otp);
            OtpResponseCall.enqueue(new Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                    if (response.isSuccessful()) {
                        verify_me.setVisibility(View.VISIBLE);
                        if (response.body().getStatus() == 200) {
                            user_id = response.body().getUserid();
                            MySharedData.setGeneralSaveSession(USER_ID, response.body().getUserid());

                            sendDeviceInfo(user_id);

                        } else {

                            // to do for another check
                            Toast.makeText(OtpScreen.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {
                    progress_constraint.setVisibility(View.GONE);
                    verify_me.setVisibility(View.VISIBLE);
                    Toast.makeText(OtpScreen.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }
    }

    private void sendDeviceInfo(String userid) {
        try {
            apiInterface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
//            String token = MySharedData.getGeneralSaveSession(FIREBASE_TOKEN);
//            System.out.println("value of token :" + token);
            Call<DeviceRegisterResponse> homeDataCall = apiInterface.regosterDevice(userid, MySharedData.getGeneralSaveSession(DEVICE_UNIQUE_ID),
                    MySharedData.getGeneralSaveSession(FIREBASE_TOKEN), "3");
            homeDataCall.enqueue(new Callback<DeviceRegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeviceRegisterResponse> call, @NonNull Response<DeviceRegisterResponse> response) {
                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        MySharedData.setGeneralSaveSession(DEVICE_UNIQUE_ID, device_unique_id);

                        if (response.body().getStatus() == 200) {

                            my_location = MySharedData.getGeneralSaveSession2(MY_LOCATION);

//                            if (my_location) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        goToActivity(OtpScreen.this, UserHomeActivity.class);
                                    }
                                }, 1000);

//                            } else {
//
//                                WhereYouWantServiceFragment whereYouWantServiceFragment = new WhereYouWantServiceFragment(OtpScreen.this);
//                                whereYouWantServiceFragment.show();
//                            }

                        }


                    } else {
                        Toast.makeText(OtpScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeviceRegisterResponse> call, Throwable t) {
                    Toast.makeText(OtpScreen.this, "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void goToActivity(Activity activity, Class refrence_activity) {
        Intent i = new Intent(activity, refrence_activity);
        startActivity(i);
        finish();
    }


}