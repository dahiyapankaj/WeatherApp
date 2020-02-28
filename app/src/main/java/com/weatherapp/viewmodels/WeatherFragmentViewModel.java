package com.weatherapp.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.weatherapp.BuildConfig;
import com.weatherapp.data.models.weekly.WeatherForecastResponse;
import com.weatherapp.data.models.zip.CurrentWeatherResponse;
import com.weatherapp.data.network.ApiRepository;
import com.weatherapp.data.network.NetworkState;
import com.weatherapp.data.network.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherFragmentViewModel extends ViewModel {
    public MutableLiveData<CurrentWeatherResponse> currentWeatherResponseMutableLiveData;
    public MutableLiveData<WeatherForecastResponse> weeklyWeatherResponseMutableLiveData;
    public MutableLiveData<String> errorMutableLiveData;
    public MutableLiveData<NetworkState> loadingMutableLiveData;

    public WeatherFragmentViewModel() {
        currentWeatherResponseMutableLiveData = new MutableLiveData<>();
        weeklyWeatherResponseMutableLiveData = new MutableLiveData<>();
        errorMutableLiveData = new MutableLiveData<>();
        loadingMutableLiveData = new MutableLiveData<>();
        loadingMutableLiveData.setValue(NetworkState.DATA_LOADED);
    }

    @SuppressLint("CheckResult")
    public void getCurrentWeather(String zipCode) {
        loadingMutableLiveData.setValue(NetworkState.LOADING);
        ApiRepository apiRepository = RetrofitClient.getClient().create(ApiRepository.class);
        // Fetching Current Weather
        apiRepository.getCurrentWeather(zipCode + ",IN", BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CurrentWeatherResponse>() {
                    @Override
                    public void onSuccess(CurrentWeatherResponse notes) {
                        // Received all notes
                        currentWeatherResponseMutableLiveData.setValue(notes);
                        loadingMutableLiveData.setValue(NetworkState.DATA_LOADED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error
                        Log.e("TAG", "Failed to get data");
                        loadingMutableLiveData.setValue(NetworkState.FAILED);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getWeeklyWeatherForecast(String zipCode) {
        loadingMutableLiveData.setValue(NetworkState.LOADING);
        ApiRepository apiRepository = RetrofitClient.getClient().create(ApiRepository.class);
        // Fetching Current Weather
        apiRepository.getWeeklyWeather(zipCode + ",IN", BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherForecastResponse>() {
                    @Override
                    public void onSuccess(WeatherForecastResponse notes) {
                        // Received all notes
                        weeklyWeatherResponseMutableLiveData.setValue(notes);
                        loadingMutableLiveData.setValue(NetworkState.DATA_LOADED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error
                        Log.e("TAG", "Failed to get data");
                        loadingMutableLiveData.setValue(NetworkState.FAILED);
                    }
                });
    }
}
