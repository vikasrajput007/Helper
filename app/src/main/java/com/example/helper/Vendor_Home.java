package com.example.helper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Vendor_Home extends Fragment implements View.OnClickListener {
    ConstraintLayout Layout_profile, Layout_history;
    TextView busy;
    View view;
    String name, address,mobileno;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private OnFragmentInteractionListener mListener;
    Bundle bundle ;
    Context context;
    public Vendor_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_vendor__home, container, false);
            Layout_profile = view.findViewById(R.id.layout_profile);
            context = getActivity();
            bundle = new Bundle();
            Layout_profile.setOnClickListener(this);
            Layout_history = view.findViewById(R.id.layout_hitsory_home);
            Layout_history.setOnClickListener(this);
            busy = view.findViewById(R.id.link_busy);
            busy.setOnClickListener(this);
            fragmentManager = getActivity().getSupportFragmentManager();

            if (!getArguments().getString("name").equals("")) {

                name = getArguments().getString("name");
                address = getArguments().getString("address");
                mobileno = getArguments().getString("mobileno");

                bundle.putString("name",name);
                bundle.putString("address",address);
                bundle.putString("mobileno",mobileno);

                Fragment popupFragment = new PopupFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                popupFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.vendor_frame, popupFragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }catch (Exception e){
            
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v == busy) {
//            Toast.makeText(getActivity(), "User_Busy", Toast.LENGTH_SHORT).show();
//            Fragment fragment = new Vendor_Busy_Fragment();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.vendor_frame, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        }
        if (v == Layout_profile) {
            Fragment fragment = new Vendor_Profile_Fragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if (v == Layout_history) {
            Fragment fragment = new Vendor_History_Fragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
