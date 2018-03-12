package com.example.helper;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.basic_iterfaces.AcceptDeclineRequest;
import com.example.helper.models.RegisterResponse;
import com.example.helper.user_section.UserRegistraion;
import com.example.helper.utils.DeclineType;
import com.example.helper.utils.MySharedData;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import java.util.Timer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.TASK_ID;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopupFragment extends Fragment implements AcceptDeclineRequest{
    View view;
    String name, address, mobileno;
    TextView count_down_timer;
    Vendor_Register_Interface apiInterface;
    private int counter;
    CountDownTimer countDownTimer;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment vendor_home;
    CircleProgressbar circleProgressbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.vendor_request_dialog, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        try {
            name = getArguments().getString("name");
            address = getArguments().getString("address");
            mobileno = getArguments().getString("mobileno");

        } catch (Exception e) {

        }
        initView();

        return view;

    }

    private void initView() {
        circleProgressbar = view.findViewById(R.id.yourCircularProgressbar);
        count_down_timer = view.findViewById(R.id.count_down_timer);
       countDownTimer =  new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                count_down_timer.setText("Please Wait :" + millisUntilFinished / 1000);
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
                count_down_timer.setText(R.string.request_again);
                declineRequest(DeclineType.auto);
            }
        }.start();


//        new CountDownTimer(180000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                count_down_timer.setText("" + String.valueOf(180000 / 60000));
//
//            }
//
//            public void onFinish() {
//            }
//        }.start();



        // set values for custom dialog components - text, image and button
        TextView name_text = view.findViewById(R.id.name);
        TextView address_text = view.findViewById(R.id.address);
        TextView mobile_no_text = view.findViewById(R.id.mobile_no);
        Button accept = view.findViewById(R.id.accept);
        Button decline = view.findViewById(R.id.decline);

        name_text.setText(name);
        address_text.setText(address);
        mobile_no_text.setText(mobileno);
//        dialog.show();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                acceptRequest();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                declineRequest(DeclineType.manual);
            }
        });
    }

    public void acceptRequest() {
         try{
             String task =  MySharedData.getGeneralSaveSession(TASK_ID);

             apiInterface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
             Call<ResponseBody> registerResponseCall = apiInterface.acceptRequest(MySharedData.getGeneralSaveSession(TASK_ID),MySharedData.getGeneralSaveSession(LATTITUDE),
                     MySharedData.getGeneralSaveSession(LONGITUDE));
             registerResponseCall.enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                     progress_constraint.setVisibility(View.GONE);
//                     register_as_user.setVisibility(View.VISIBLE);
//                     register_as_vendor.setVisibility(View.VISIBLE);
                     if (response.isSuccessful()) {
                         String message = response.body().toString();

                         if(vendor_home==null){
                             vendor_home = new Vendor_Home();
                             fragmentTransaction = fragmentManager.beginTransaction();
                             fragmentTransaction.add(R.id.vendor_frame, vendor_home);
                             fragmentTransaction.addToBackStack(null);
                             fragmentTransaction.commit();
                         }
                         else{
                             fragmentTransaction = fragmentManager.beginTransaction();
                             fragmentTransaction.add(R.id.vendor_frame, vendor_home);
                             fragmentTransaction.addToBackStack(null);
                             fragmentTransaction.commit();
                         }
                     }

                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                     Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }   catch (Exception e){

         }
    }


    @Override
    public void declineRequest(DeclineType type) {
        try{
        apiInterface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
        Call<ResponseBody> registerResponseCall = apiInterface.declineRequest(type,MySharedData.getGeneralSaveSession(TASK_ID),MySharedData.getGeneralSaveSession(LATTITUDE),
                MySharedData.getGeneralSaveSession(LONGITUDE),MySharedData.getGeneralSaveSession("request_user_id"),MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID),
                MySharedData.getGeneralSaveSession(VENDOR_ID));

        registerResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                     progress_constraint.setVisibility(View.GONE);
//                     register_as_user.setVisibility(View.VISIBLE);
//                     register_as_vendor.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {

                    String message = response.body().toString();
                    if(vendor_home==null){
                        vendor_home = new Vendor_Home();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.vendor_frame, vendor_home);
                        fragmentTransaction.commit();
                    }
                    else{
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.vendor_frame, vendor_home);
                        fragmentTransaction.commit();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }   catch (Exception e){

    }
    }
}
