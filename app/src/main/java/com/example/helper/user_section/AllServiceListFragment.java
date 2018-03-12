package com.example.helper.user_section;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.helper.R;
import com.example.helper.Vendor_Home_API_Client;
import com.example.helper.Vendor_Register_Interface;
import com.example.helper.user_section.fragments.NotificationToVendorFragment;
import com.example.helper.user_section.home_data.UserRequestResponse;
import com.example.helper.user_section.home_data.allservices.AllServices;
import com.example.helper.user_section.home_data.allservices.Servicelist;
import com.example.helper.utils.MySharedData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.SERVICE_ICON_BASE_URL;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID_FOR_USER;

public class AllServiceListFragment extends Fragment {
    Vendor_Register_Interface vendor_register_interface;
    RecyclerView all_service_recycler_view;
    GridLayoutManager gridLayoutManager;
    List<Servicelist> allServiceList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    View view;
    String service_id;
    TextView helper_logo;
    EditText search_field;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_all_service, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        allServiceList = new ArrayList<>();
        service_id = getArguments().getString("service_id");
        initView();

        return view;
    }

    private void initView() {

        helper_logo = ((AppCompatActivity) getActivity()).findViewById(R.id.helper_name);
        search_field = ((AppCompatActivity) getActivity()).findViewById(R.id.search_field);

        if (helper_logo.getVisibility() == View.VISIBLE) {
            search_field.setVisibility(View.GONE);
            helper_logo.setVisibility(View.GONE);
        }
//        service_id = getArguments().getString("service_id");

        all_service_recycler_view = view.findViewById(R.id.all_service_recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        getAllData();

    }

    /**
     * To get all dash board data
     */
    private void getAllData() {
        try {
//            progress_constraint.setVisibility(View.VISIBLE);
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<AllServices> homeDataCall = vendor_register_interface.getAllServices(service_id);
            homeDataCall.enqueue(new Callback<AllServices>() {
                @Override
                public void onResponse(@NonNull Call<AllServices> call, @NonNull Response<AllServices> response) {
//                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        allServiceList = response.body().getServicelist();
                        all_service_recycler_view.setLayoutManager(gridLayoutManager);
                        all_service_recycler_view.setAdapter(new ServiceTypeAdapter());
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AllServices> call, Throwable t) {
//                    progress_constraint.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }


    private class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ServiceTypeViewHolder> {
        @Override
        public ServiceTypeAdapter.ServiceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.all_service_list_item_design, parent, false);
            return new ServiceTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ServiceTypeAdapter.ServiceTypeViewHolder holder, final int position) {

            switch (holder.getItemViewType()) {
                default: {
                    holder.all_service_name.setText(allServiceList.get(position).getServicename());
                    Glide.with(getActivity())
                            .load(SERVICE_ICON_BASE_URL + allServiceList.get(position).getIcons())
                            .placeholder(R.drawable.custom_notification)
                            .into(holder.all_service_icon);


                    holder.vendor_service.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                           getVendorResponse();




                        }
                    });

                }
            }
        }


        private void getVendorResponse() {
            try {
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

                        }

                        else {
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

        @Override
        public int getItemCount() {
            if (allServiceList == null) {
                return 0;
            } else {
                return allServiceList.size();
            }
        }

        class ServiceTypeViewHolder extends RecyclerView.ViewHolder {
            ImageView all_service_icon;
            TextView all_service_name;
            LinearLayout vendor_service;

            ServiceTypeViewHolder(View itemView) {
                super(itemView);
                all_service_icon = itemView.findViewById(R.id.all_service_icon);
                all_service_name = itemView.findViewById(R.id.all_service_name);
                vendor_service = itemView.findViewById(R.id.vendor_service);
            }
        }
    }

}
