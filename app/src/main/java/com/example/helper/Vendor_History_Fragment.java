package com.example.helper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helper.adapters.History_View_Adapter;


public class Vendor_History_Fragment extends Fragment
{
    Context context;
    RecyclerView recycler_View ;
    View view;
    RecyclerView.Adapter recyclerView_adapter;
    RecyclerView.LayoutManager recyclerView_LayoutManager;
    String[] vendor_name = {"ASHOK SINGLA","ASHOK MINGLA","ASHOK KINGLA","ASHOK AINGLA","ASHOK PINGLA"};

    private OnFragmentInteractionListener mListener;

    public Vendor_History_Fragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_vendor__history_, container, false);
        context = getContext();
        recycler_View = view.findViewById(R.id.recyclerView);
        recyclerView_LayoutManager = new GridLayoutManager(context,1);
        recycler_View.setLayoutManager(recyclerView_LayoutManager);
        recyclerView_adapter = new History_View_Adapter(context,vendor_name);
        recycler_View.setAdapter(recyclerView_adapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
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


    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
