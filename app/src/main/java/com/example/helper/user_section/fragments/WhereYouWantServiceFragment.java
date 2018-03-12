package com.example.helper.user_section.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.helper.R;


public class WhereYouWantServiceFragment extends Dialog implements View.OnClickListener {
    public Activity activity;
    View view;
    Dialog dialog;
    private ConstraintLayout my_current_location;
    private TextView search_service_location;

    public WhereYouWantServiceFragment(@NonNull Activity context) {
        super(context);
        activity = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.where_you_want_service);
        initView();

    }

    private void initView() {
        search_service_location = findViewById(R.id.search_service_location);
        my_current_location = findViewById(R.id.my_current_location);
        search_service_location.setOnClickListener(this);
        my_current_location.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_current_location:

                dialog.dismiss();
                break;
            case R.id.search_service_location:
                dialog.dismiss();
                break;

            default:
                break;


        }
    }
}
