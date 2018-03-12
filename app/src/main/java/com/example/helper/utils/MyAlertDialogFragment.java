//package com.example.helper.utils;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
//
//import com.example.helper.R;
//
///**
// * Created by HP on 08-03-2018.
// */
//
//public static class MyAlertDialogFragment extends DialogFragment {
//
//    public static MyAlertDialogFragment newInstance(int title) {
//        MyAlertDialogFragment frag = new MyAlertDialogFragment();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
//        return frag;
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        int title = getArguments().getInt("title");
//        return new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.custom_notification)
//                .setTitle(title)
//                .setPositiveButton(R.string.alert_dialog_ok,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                ((MyAlertDialogFragment)getActivity()).doPositiveClick();
//                            }
//                        }
//                )
//                .setNegativeButton(R.string.alert_dialog_cancel,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                ((FragmentAlertDialog)getActivity()).doNegativeClick();
//                            }
//                        }
//                )
//                .create();
//    }
//
//
//
//
//}
