package com.weatherapp.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weatherapp.R;
import com.weatherapp.data.models.zip.CurrentWeatherResponse;
import com.weatherapp.databinding.FragmentCurrentWeatherBinding;
import com.weatherapp.views.ActivityHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {


    private ActivityHandler activityHandler;
    private CurrentWeatherResponse result;

    public static CurrentWeatherFragment newInstance(ActivityHandler activityHandler, CurrentWeatherResponse result) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_ITEM", result);
        fragment.setArguments(bundle);
        fragment.activityHandler = activityHandler;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCurrentWeatherBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_current_weather, container, false);

        if (getArguments() != null) {
            result = getArguments().getParcelable("KEY_ITEM");
            binding.setDataModel(result);
        }
        return binding.getRoot();
    }
}
