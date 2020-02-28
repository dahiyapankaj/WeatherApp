package com.weatherapp.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.weatherapp.R;
import com.weatherapp.data.models.weekly.WeatherForecastResponse;
import com.weatherapp.data.models.zip.CurrentWeatherResponse;
import com.weatherapp.data.network.NetworkState;
import com.weatherapp.data.utils.KeyboardUtils;
import com.weatherapp.viewmodels.WeatherFragmentViewModel;
import com.weatherapp.views.ActivityHandler;

import static com.weatherapp.data.network.NetworkState.FAILED;
import static com.weatherapp.data.network.NetworkState.LOADING;

public class WeatherFragment extends Fragment {


    private ActivityHandler activityHandler;
    private Button buttonCurrent;
    private Button buttonWeekly;
    private EditText etZip;
    private WeatherFragmentViewModel mViewModel;

    public static WeatherFragment newInstance(ActivityHandler activityHandler) {
        WeatherFragment fragment = new WeatherFragment();
        fragment.activityHandler = activityHandler;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, parent, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeatherFragmentViewModel.class);
        initViews();
        initObservers();
    }

    @Override
    public void onStop() {
        super.onStop();
        KeyboardUtils.hideKeyboard(getActivity());
    }

    private void initObservers() {

        etZip.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 6) {
                    KeyboardUtils.hideKeyboard(getActivity());
                    etZip.clearFocus();
                }
            }
        });

        mViewModel.weeklyWeatherResponseMutableLiveData.observe(getActivity(), new Observer<WeatherForecastResponse>() {
            @Override
            public void onChanged(WeatherForecastResponse result) {
                activityHandler.addFragment(ForecastFragment.newInstance(activityHandler, result), true);
                KeyboardUtils.hideKeyboard(getActivity());
            }
        });

        mViewModel.currentWeatherResponseMutableLiveData.observe(getActivity(), new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse result) {
                activityHandler.addFragment(CurrentWeatherFragment.newInstance(activityHandler, result), true);
                KeyboardUtils.hideKeyboard(getActivity());
            }
        });
        mViewModel.loadingMutableLiveData.observe(getActivity(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if (networkState.equals(LOADING)) {
                    activityHandler.showProgressDialog();
                } else if (networkState.equals(FAILED)) {
                    activityHandler.hideProgressDialog();
                    activityHandler.showToast("Something went wrong!!");
                    // maybe we can implement retry functionality here in future
                } else {
                    activityHandler.hideProgressDialog();
                }
            }
        });
    }



    private void initViews() {
        buttonCurrent = getView().findViewById(R.id.btn_current);
        buttonWeekly = getView().findViewById(R.id.btn_forecast);
        etZip = getView().findViewById(R.id.etZipCode);

        buttonCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getCurrentWeather(etZip.getText().toString());
            }
        });
        buttonWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getWeeklyWeatherForecast(etZip.getText().toString());
            }
        });
    }
}
