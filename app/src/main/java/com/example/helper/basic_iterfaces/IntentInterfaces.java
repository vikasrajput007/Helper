package com.example.helper.basic_iterfaces;

import android.app.Activity;

/**
 * Created by HP on 16-02-2018.
 */

public interface IntentInterfaces {

    void switchActivity();

     interface  InnerInterface{
        void goToActivity(Activity activity, Class refrence_activity);
    }
}
