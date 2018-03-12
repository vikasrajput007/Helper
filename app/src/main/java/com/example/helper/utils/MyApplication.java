package com.example.helper.utils;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;


/**
 * Created by Ragasoft on 4/10/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initializeSharedPreference();
    }



    // To initialise the connectivity listener


    private void initializeSharedPreference() {
        MySharedData.initializePreference(getApplicationContext());
    }


}
