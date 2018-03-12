package com.example.helper;

import android.util.Log;

import com.example.helper.utils.MySharedData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.example.helper.utils.Constants.FIREBASE_TOKEN;

/**
 * Created by payal on 10/12/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService
{
    private static final String TAG = "FirebaseIDService";
    String refreshedToken;
    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
         refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        MySharedData.setGeneralSaveSession(FIREBASE_TOKEN,refreshedToken);
       // getToken();

        // TODO: Implement this method to send any registration to your app's servers.
       // sendRegistrationToServer(refreshedToken);
    }

//    @Override
//    public void getToken() {
//        MySharedData.setGeneralSaveSession(FIREBASE_TOKEN,refreshedToken);
//    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//    }
}
