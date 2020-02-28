package com.weatherapp.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.R;
import com.weatherapp.data.adapters.ListAdapter;
import com.weatherapp.data.models.weekly.List;
import com.weatherapp.data.models.weekly.WeatherForecastResponse;
import com.weatherapp.views.ActivityHandler;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements ListAdapter.ListItemClickListener {

    private TextView tvCity;
    private RecyclerView rv;
    private ListAdapter adapter;
    private WeatherForecastResponse resultList;
    private ActivityHandler activityHandler;


    public static ForecastFragment newInstance(ActivityHandler activityHandler, WeatherForecastResponse result) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_ITEM", result);
        fragment.setArguments(bundle);
        fragment.activityHandler = activityHandler;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            resultList = getArguments().getParcelable("KEY_ITEM");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        tvCity = getView().findViewById(R.id.tv_city_name);
        tvCity.setText(resultList.getCity().getName());
        rv = getView().findViewById(R.id.rv_items);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        //creating the Adapter
        adapter = new ListAdapter(filterListForSingleDateOnly(resultList.getList()), this);
        rv.setAdapter(adapter);
    }

    /**
     * @param resultList is the list of forecast for every three hours
     * @return a list with only 1 forecast per day after filtering data
     */
    private ArrayList<List> filterListForSingleDateOnly(java.util.List<List> resultList) {
        ArrayList<List> finalList = new ArrayList<>();
        String dummyDate = "";
        Iterator<List> itr = resultList.iterator();
        while (itr.hasNext()) {
            List item = itr.next();
            String[] separated = item.getDtTxt().split(" ");
            String[] separated1 = separated[0].split("-");
            if (!dummyDate.equals(separated1[2])) {
                finalList.add(item);
                dummyDate = separated1[2];
            }
        }
        return finalList;
    }

    @Override
    public void onItemClick(com.weatherapp.data.models.weekly.List result) {
        activityHandler.showToast("We can get all the required data here  like wind speed : " + result.getWind().getSpeed());
    }
}

