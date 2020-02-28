package com.weatherapp.data.network;

import com.weatherapp.data.models.weekly.WeatherForecastResponse;
import com.weatherapp.data.models.zip.CurrentWeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRepository {
    /**
     * Method to make request for getting current weather data
     */
    @GET("/data/2.5/weather")
    Single<CurrentWeatherResponse> getCurrentWeather(@Query(value = "zip") String zipCode,
                                                     @Query(value = "appid") String apiKey);

    /**
     * Method to make request for getting all weather data for week
     */
    @GET("/data/2.5/forecast")
    Single<WeatherForecastResponse> getWeeklyWeather(@Query(value = "zip") String zipCode,
                                                     @Query(value = "appid") String apiKey);
}
