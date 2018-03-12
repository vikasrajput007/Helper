package com.example.helper.user_section.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.helper.basic_iterfaces.VendorResponse;
import com.example.helper.user_section.AllServiceListFragment;
import com.example.helper.user_section.home_data.HomeData;
import com.example.helper.user_section.home_data.Service;
import com.example.helper.user_section.home_data.Service_;
import com.example.helper.user_section.home_data.Servicetype;
import com.example.helper.user_section.home_data.Slider;
import com.example.helper.user_section.home_data.UserRequestResponse;
import com.example.helper.utils.DeclineType;
import com.example.helper.utils.MySharedData;
import com.example.helper.utils.SampleView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helper.utils.Constants.LATTITUDE;
import static com.example.helper.utils.Constants.LONGITUDE;
import static com.example.helper.utils.Constants.SERVICE_ICON_BASE_URL;
import static com.example.helper.utils.Constants.SERVICE_THUMBNAIL_BASE_URL;
import static com.example.helper.utils.Constants.SLIDER_BASE_URL;
import static com.example.helper.utils.Constants.USER_ID;
import static com.example.helper.utils.Constants.VENDOR_SERVICE_ID_FOR_USER;

/**
 * Created by HP on 23-02-2018.
 */


public class UserHomeFragment extends Fragment implements VendorResponse{
    Vendor_Register_Interface vendor_register_interface;
    RecyclerView services_recycler, service_type_recycler_view;
    List<Service> serviceList;
    TextView count_down_timer;
    List<Servicetype> serviceTypeList;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ViewPager viewPager;
    TextView helper_logo;
    EditText search_field;
    SampleView gif_view;
    LinearLayout loader_layout;
    CountDownTimer countDownTimer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_user_home, container, false);
        initView();
        return view;
    }

    private void initView() {
        loader_layout = view.findViewById(R.id.loader_layout);
        helper_logo = ((AppCompatActivity) getActivity()).findViewById(R.id.helper_name);
        search_field = ((AppCompatActivity) getActivity()).findViewById(R.id.search_field);
        gif_view = view.findViewById(R.id.gif_view);
        viewPager = view.findViewById(R.id.view_pager);

        count_down_timer = view.findViewById(R.id.count_down_timer);



        viewPager.setClipToPadding(false);
        viewPager.setPadding(28,0,28,0);
        viewPager.setPageMargin(10);
        fragmentManager = getActivity().getSupportFragmentManager();
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                progress_constraint = view.findViewById(R.id.progress_constraint);
        services_recycler = view.findViewById(R.id.services_recycler);
        service_type_recycler_view = view.findViewById(R.id.service_type_recycler_view);

        serviceList = new ArrayList<>();
        serviceTypeList = new ArrayList<>();
        getHomeData();

    }

    /**
     * To get all dash board data
     */
    private void getHomeData() {
        try {
//            progress_constraint.setVisibility(View.VISIBLE);
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<HomeData> homeDataCall = vendor_register_interface.getHomeData();
            homeDataCall.enqueue(new Callback<HomeData>() {
                @Override
                public void onResponse(@NonNull Call<HomeData> call, @NonNull Response<HomeData> response) {
//                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        serviceList = response.body().getServices();
                        serviceTypeList = response.body().getServicetypes();
                        services_recycler.setLayoutManager(gridLayoutManager);
                        viewPager.setAdapter(new ViewPager_Image_Adapter(getActivity(), response.body().getSliders()));
                        ViewCompat.setNestedScrollingEnabled(services_recycler, false);
                        service_type_recycler_view.setLayoutManager(linearLayoutManager);
                        services_recycler.setAdapter(new ServiceAdapter());

                        service_type_recycler_view.setAdapter(new ServiceTypeAdapter());
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HomeData> call, Throwable t) {
//                    progress_constraint.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void getVendorResponse() {
        try {
            loader_layout.setVisibility(View.VISIBLE);
            vendor_register_interface = Vendor_Home_API_Client.getUserClient().create(Vendor_Register_Interface.class);
            Call<UserRequestResponse> homeDataCall = vendor_register_interface.requestToVendor(MySharedData.getGeneralSaveSession(USER_ID),
                    MySharedData.getGeneralSaveSession(VENDOR_SERVICE_ID_FOR_USER), MySharedData.getGeneralSaveSession(LATTITUDE),
                    MySharedData.getGeneralSaveSession(LONGITUDE));
            homeDataCall.enqueue(new Callback<UserRequestResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserRequestResponse> call, @NonNull Response<UserRequestResponse> response) {
//                    progress_constraint.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.body().getMessage().equals(getString(R.string.waiting_for_three_minute)) && response.body().getStatus() == 201) {
                            Toast.makeText(getActivity(), "" + (getString(R.string.waiting_for_three_minute)), Toast.LENGTH_SHORT).show();
                            loader_layout.setVisibility(View.GONE);

                        } else {
                            loader_layout.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            NotificationToVendorFragment notificationToVendorFragment = new NotificationToVendorFragment();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.container_layout, notificationToVendorFragment, "NotificationToVendorFragment");
                            fragmentTransaction.addToBackStack("NotificationToVendorFragment");
                            fragmentTransaction.commit();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Something went wrong in request to vendor", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserRequestResponse> call, Throwable t) {
                    loader_layout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something went wrong with on failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (
                Exception e) {
        }

    }

    // this adapter is for top 6 items
    private class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

        @Override
        public ServiceAdapter.ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.service_list_item_design, parent, false);
            return new ServiceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ServiceAdapter.ServiceViewHolder holder, int position) {
            holder.service_name.setText(serviceList.get(position).getName());
            // glide for cache and disk memory to load images
//                                String service_id = serviceList.get(position).getServiceid();

            holder.constraintLayout12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySharedData.setGeneralSaveSession(VENDOR_SERVICE_ID_FOR_USER, serviceList.get(holder.getAdapterPosition()).getServiceid());
                    getVendorResponse();
                }
            });
            Glide.with(getActivity())
                    .load(SERVICE_ICON_BASE_URL + serviceList.get(position).getIcon())
                    .into(holder.service_icon);

        }

        @Override
        public int getItemCount() {
            if (serviceList == null) {
                return 0;
            } else {
                return serviceList.size();
            }
        }


        class ServiceViewHolder extends RecyclerView.ViewHolder {
            ImageView service_icon;
            TextView service_name;
            LinearLayout constraintLayout12;

            ServiceViewHolder(View itemView) {
                super(itemView);
                service_icon = itemView.findViewById(R.id.service_icon);
                service_name = itemView.findViewById(R.id.service_name);
                constraintLayout12 = itemView.findViewById(R.id.constraintLayout12);
            }
        }
    }

    //      horizontal list class adapter
    private class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ServiceTypeViewHolder> {

        @Override
        public ServiceTypeAdapter.ServiceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.service_type_list_design, parent, false);
            return new ServiceTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ServiceTypeAdapter.ServiceTypeViewHolder holder, final int position) {

            switch (holder.getItemViewType()) {
                default: {
                    holder.service_type_name.setText(serviceTypeList.get(position).getName());
                    holder.inner_service_type_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    holder.inner_service_type_recycler.setAdapter(new InnerServiceTypeAdapter(serviceTypeList.get(position).getServices()));

                    holder.see_all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle agus = new Bundle();
                            agus.putString("service_id", serviceTypeList.get(position).getId());
                            AllServiceListFragment allServiceActivity = new AllServiceListFragment();
                            allServiceActivity.setArguments(agus);
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.container_layout, allServiceActivity, "AllServiceListFragment");
                            fragmentTransaction.addToBackStack("AllServiceListFragment");
                            fragmentTransaction.commit();
                        }
                    });
                    if (serviceTypeList.get(position).getServices() == null) {
                        holder.see_all.setVisibility(View.GONE);
                        holder.service_type_name.setVisibility(View.GONE);
                        holder.inner_service_type_recycler.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if (serviceTypeList == null) {
                return 0;
            } else {
                return serviceTypeList.size();
            }
        }

        class ServiceTypeViewHolder extends RecyclerView.ViewHolder {
            RecyclerView inner_service_type_recycler;
            TextView service_type_name, see_all;

            ServiceTypeViewHolder(View itemView) {
                super(itemView);
                inner_service_type_recycler = itemView.findViewById(R.id.inner_service_type_recycler);
//                inner_service_type_recycler.setNestedScrollingEnabled(false);
                ViewCompat.setNestedScrollingEnabled(inner_service_type_recycler, false);

                service_type_name = itemView.findViewById(R.id.service_type_name);
                see_all = itemView.findViewById(R.id.see_all);
            }
        }
    }

        //  horizontal inner list adapter
    private class InnerServiceTypeAdapter extends RecyclerView.Adapter<InnerServiceTypeAdapter.InnerServiceTypeViewHolder> {
        List<Service_> innerServiceTypeList = new ArrayList<>();

        InnerServiceTypeAdapter(List<Service_> inner_service_list) {
            innerServiceTypeList = inner_service_list;
        }

        @Override
        public InnerServiceTypeAdapter.InnerServiceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.inner_service_type_list_item, parent, false);
            return new InnerServiceTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InnerServiceTypeViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                default: {
                    holder.inner_service_type_name.setText(innerServiceTypeList.get(position).getName());
                    holder.inner_service_item_listener.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            countDownTimer = new CountDownTimer(180000, 1000) {
//
//                                public void onTick(long millisUntilFinished) {
//
//                                }
//
//                                public void onFinish() {
//                                    count_down_timer.setVisibility(View.GONE);
//                                }
//
//                            }.start();
//
//                            if(){

                                getVendorResponse();

//                            }
//                            count_down_timer.setText("Please Wait :" + millisUntilFinished / 1000);


                        }
                    });
                    Glide.with(getActivity())
                            .load(SERVICE_THUMBNAIL_BASE_URL + innerServiceTypeList.get(position).getThumbnails())
                            .placeholder(R.drawable.custom_notification)
                            .into(holder.inner_service_type_image);
                    System.out.println("inner list image url is :" + SERVICE_THUMBNAIL_BASE_URL + innerServiceTypeList.get(position).getThumbnails());
                }
            }
        }

        @Override
        public int getItemCount() {
            if (innerServiceTypeList == null) {
                return 0;
            } else {
                return innerServiceTypeList.size();
            }
        }

        class InnerServiceTypeViewHolder extends RecyclerView.ViewHolder {
            ImageView inner_service_type_image;
            TextView inner_service_type_name;
             ConstraintLayout inner_service_item_listener;
            InnerServiceTypeViewHolder(View itemView) {
                super(itemView);
                inner_service_type_image = itemView.findViewById(R.id.inner_service_type_image);
                inner_service_type_name = itemView.findViewById(R.id.inner_service_type_name);
                inner_service_item_listener = itemView.findViewById(R.id.inner_service_item_listener);
            }
        }
    }

//    implements LoopingPagerAdapter
    private class ViewPager_Image_Adapter extends PagerAdapter {
        Context context;
        private List<Slider> sliderList;
        private LayoutInflater inflater;

        private ViewPager_Image_Adapter(Context context, List<Slider> sliderList) {
            this.context = context;
            this.sliderList = sliderList;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return sliderList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imagelayout = inflater.inflate(R.layout.viewpager_item_layout, container, false);
            assert imagelayout != null;
            ImageView image = imagelayout.findViewById(R.id.cool);
            String image_url = sliderList.get(position).getImgurl();
            Glide.with(getActivity())
                    .load(SLIDER_BASE_URL + image_url)
                    .placeholder(R.drawable.custom_notification)
                    .into(image);
            container.addView(imagelayout, 0);
            return imagelayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }


    }

}
