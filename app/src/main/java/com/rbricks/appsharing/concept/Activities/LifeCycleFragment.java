package com.rbricks.appsharing.concept.Activities;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gopikrishna on 6/6/16.
 */
public class LifeCycleFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("LifeCycleFragment.onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("LifeCycleFragment.onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("LifeCycleFragment.onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("LifeCycleFragment.onActivityCreated");
        String sampleFragmentSave = savedInstanceState.getString("sampleFragmentSave");
        System.out.println("sampleFragmentSave in Fragment from onSaveInstanceState of Fragment = " + sampleFragmentSave);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sampleFragmentSave", "sampleFragmentSave");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("LifeCycleFragment.onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("LifeCycleFragment.onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("LifeCycleFragment.onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("LifeCycleFragment.onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LifeCycleFragment.onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("LifeCycleFragment.onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("LifeCycleFragment.onDestroyView");
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }
}
