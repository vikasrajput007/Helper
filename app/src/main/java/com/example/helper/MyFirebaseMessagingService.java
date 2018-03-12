package com.example.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.helper.user_section.UserHomeActivity;
import com.example.helper.utils.MySharedData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.example.helper.utils.Constants.TASK_ID;


/**
 * Created by payal on 10/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    PendingIntent resultPendingIntent;
    String name, address, mobileno, vendor_lattitude, vendor_longitude, title, text, taskid;
    String userid = "";
    String vendorid = "";
    Intent resIntent_vendor, resIntent_user;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) {
            return;
        }

        if (remoteMessage.getData().size() > 0) {
            handleNotification(remoteMessage);
        } else {
            Log.e(TAG, "No Data Received:");
        }
    }

    @SuppressWarnings("deprecation")
    private void handleNotification(RemoteMessage message) {
        try {

            Map<String, String> remote_map = message.getData();

//            if ( remote_map.get("mobileno").equals("")) {
            String request_user_id = remote_map.get("userid");
//                 MySharedData.setGeneralSaveSession(USER_ID,userid);
            MySharedData.setGeneralSaveSession("request_user_id", request_user_id);
            name = remote_map.get("name");
            address = remote_map.get("address");
            mobileno = remote_map.get("mobileno");
            vendorid = remote_map.get("vendorid");
//                taskid = Integer.parseInt(remote_map.get("taskid"));
            title = remote_map.get("title");
            text = remote_map.get("text");

//            } else {

            taskid = remote_map.get("taskid");
            vendor_lattitude = remote_map.get("lattitude");
            vendor_longitude = remote_map.get("longitude");
//            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);

            mBuilder.setContentTitle(title);
            mBuilder.setContentText(text);


            mBuilder.setTicker("Read");
            mBuilder.setAutoCancel(true);
            mBuilder.setLights(Color.BLUE, 500, 500);
            mBuilder.setOngoing(true);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            long[] pattern = {400, 400, 400, 400, 400, 400, 400, 400, 400};
            mBuilder.setVibrate(pattern);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);

            if (taskid != null) {

                resIntent_vendor = new Intent(this, Home_Screen_Vendor.class);
                resIntent_vendor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                resIntent_vendor.putExtra("name", name);
                resIntent_vendor.putExtra("address", address);
                resIntent_vendor.putExtra("mobileno", mobileno);
                MySharedData.setGeneralSaveSession(TASK_ID, taskid);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(Home_Screen_Vendor.class);
                stackBuilder.addNextIntent(resIntent_vendor);
                resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resIntent_vendor, PendingIntent.FLAG_UPDATE_CURRENT);

//                Toast.makeText(this, "this is notification", Toast.LENGTH_SHORT).show();

            }

            else {
                resIntent_user = new Intent(this, UserHomeActivity.class);
                resIntent_user.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                resIntent_user.putExtra("name", name);
                resIntent_user.putExtra("address", address);
                resIntent_user.putExtra("mobileno", mobileno);
                MySharedData.setGeneralSaveSession(TASK_ID, taskid);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(UserHomeActivity.class);
                stackBuilder.addNextIntent(resIntent_user);
                resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resIntent_user, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert mNotificationManager != null;
            mNotificationManager.notify(0, mBuilder.build());

        } catch (Exception e) {
            System.out.println("what is exception :" + e.getMessage());
        }
    }

}
