package com.example.helper.user_section.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.R;
import com.example.helper.Vendor_Home_API_Client;
import com.example.helper.Vendor_Register_Interface;
import com.example.helper.user_section.home_data.UserRequestResponse;
import com.example.helper.utils.MySharedData;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID_FOR_USER;

//import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by HP on 27-02-2018.
 */

public class NotificationToVendorFragment extends Fragment implements View.OnClickListener {
    View view;
    Vendor_Register_Interface vendor_register_interface;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RelativeLayout map_layout;
    TextView count_down_timer, cancel_call, call_to_vendor;
    CountDownTimer countDownTimer;
    CircleProgressbar circleProgressbar;
    TextView request_to_different_vendor;
    UserHomeFragment userHomeFragment;
//    SupportMapFragment mapFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vendor_detail_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        initView();
        return view;
    }


    private void initView() {
//                   getVendorResponse();

//        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);

        call_to_vendor = view.findViewById(R.id.call_to_vendor);
        cancel_call = view.findViewById(R.id.cancel_call);

        cancel_call.setOnClickListener(this);
        call_to_vendor.setOnClickListener(this);

        circleProgressbar = view.findViewById(R.id.waiting_for_vendor_Progressbar);
        count_down_timer = view.findViewById(R.id.count_down_timer);
        request_to_different_vendor = view.findViewById(R.id.request_to_different_vendor);
        count_down_timer.setVisibility(View.VISIBLE);
        circleProgressbar.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                count_down_timer.setText("" + millisUntilFinished / 1000);
                circleProgressbar.setForegroundProgressColor(Color.GREEN);
                circleProgressbar.setBackgroundColor(Color.parseColor("#ffffff"));
                circleProgressbar.setBackgroundProgressWidth(12);
                circleProgressbar.setForegroundProgressWidth(14);
                circleProgressbar.enabledTouch(true);
                circleProgressbar.setRoundedCorner(true);
                circleProgressbar.setClockwise(true);
                int animationDuration = 60000 * 3; // 2500ms = 2,5s
                circleProgressbar.setProgressWithAnimation(0, animationDuration); // Default duration = 1500ms
            }

            public void onFinish() {
                count_down_timer.setVisibility(View.GONE);
                circleProgressbar.setVisibility(View.GONE);
                request_to_different_vendor.setVisibility(View.VISIBLE);
                request_to_different_vendor.setText(R.string.request_again_to_different_vendor);
                request_to_different_vendor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userHomeFragment == null) {
                            userHomeFragment = new UserHomeFragment();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.container_layout, userHomeFragment, "UserHomeFragment");
//                        fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                            fragmentTransaction.commit();

                        } else {
//                            userHomeFragment = new UserHomeFragment();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.container_layout, userHomeFragment, "UserHomeFragment");
//                        fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                            fragmentTransaction.commit();
                        }

                    }
                });
//                declineRequest(DeclineType.auto);
            }
        }.start();

    }


    private void getVendorResponse() {
        try {
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<UserRequestResponse> homeDataCall = vendor_register_interface.requestToVendor(MySharedData.getGeneralSaveSession(USER_ID),
                    MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID_FOR_USER), MySharedData.getGeneralSaveSession(LATTITUDE),
                    MySharedData.getGeneralSaveSession(LONGITUDE));
            homeDataCall.enqueue(new Callback<UserRequestResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserRequestResponse> call, @NonNull Response<UserRequestResponse> response) {
//                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        NotificationToVendorFragment notificationToVendorFragment = new NotificationToVendorFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.container_layout, notificationToVendorFragment, "NotificationToVendorFragment");
                        fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                        fragmentTransaction.commit();

                    } else {
                        Toast.makeText(getActivity(), "Something went wrong in request to vendor", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserRequestResponse> call, Throwable t) {
//                    progress_constraint.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (
                Exception e) {
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_to_vendor:
                // To Do For calling funciton
                break;
            case R.id.cancel_call:
                if (userHomeFragment == null) {
                    userHomeFragment = new UserHomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container_layout, userHomeFragment, "UserHomeFragment");
//                        fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                    fragmentTransaction.commit();

                } else {
//                            userHomeFragment = new UserHomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container_layout, userHomeFragment, "UserHomeFragment");
//                        fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                    fragmentTransaction.commit();
                }
                // To Do for cancel the call
                break;

            default:
                break;
        }
    }
}
