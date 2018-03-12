package com.example.helper.abstraction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.helper.R;
import com.example.helper.Vendor_Home_API_Client;
import com.example.helper.Vendor_Register_Interface;
import com.example.helper.user_section.fragments.NotificationToVendorFragment;
import com.example.helper.user_section.home_data.UserRequestResponse;
import com.example.helper.utils.MySharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID_FOR_USER;

/**
 * Created by HP on 03-03-2018.
 */

public abstract class VendorResponseData extends Fragment {
           Context context;
    Vendor_Register_Interface vendor_register_interface;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public void getVendorResponse() {
        try {
            fragmentManager = getFragmentManager();
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<UserRequestResponse> homeDataCall = vendor_register_interface.requestToVendor(MySharedData.getGeneralSaveSession(USER_ID),
                    MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID_FOR_USER),MySharedData.getGeneralSaveSession(LATTITUDE),
                    MySharedData.getGeneralSaveSession(LONGITUDE));
            homeDataCall.enqueue(new Callback<UserRequestResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserRequestResponse> call, @NonNull Response<UserRequestResponse> response) {
//                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                Exception e)
        {
        }

    }

}
