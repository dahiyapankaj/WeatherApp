package com.weatherapp.views;

import androidx.fragment.app.Fragment;

public interface ActivityHandler {

    void showProgressDialog();

    void hideProgressDialog();

    void showToast(String message);

    void updateTitle(String title);

    void showSnackBar(String message);

    void replaceFragment(Fragment fragment, boolean addToBackStack);

    void addFragment(Fragment fragment, boolean addToBackStack);
}
