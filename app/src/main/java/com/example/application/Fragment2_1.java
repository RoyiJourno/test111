package com.example.application;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment2_1 extends Fragment {
    //interface for the fragment
    callback callback;


    //connect the interface(callback) to the activity
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            callback = (callback) activity;
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2_1, container, false);
        (view.findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trigger the interface to execute the function
                callback.changeButton1Text("Shirley Cohen");

                //close all the fragment after we send data to the Activity.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for(Fragment fragment : getFragmentManager().getFragments()){
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.commit();

            }
        });
        return view;
    }

    //Interface
    public interface callback{
        public void changeButton1Text(String name);
        public void changeButton2Text(String name);
    }
}
