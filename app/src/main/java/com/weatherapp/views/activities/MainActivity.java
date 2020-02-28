package com.weatherapp.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.weatherapp.R;
import com.weatherapp.views.ActivityHandler;
import com.weatherapp.views.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements ActivityHandler {

    private RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.include_progressbar);
        // adding DeliveryList fragment as our first fragment
        addFragment(WeatherFragment.newInstance(this), false);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateTitle(String title) {

    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        addReplaceFragment(false, fragment, addToBackStack);
    }


    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack) {
        addReplaceFragment(true, fragment, addToBackStack);
    }

    /**
     * Method which adds or replaces fragment
     *
     * @param add            boolean variable to decide whether to add or replace a fragment
     * @param fragment       is object of the fragment to be added
     * @param addToBackStack boolean to decide whether we have to add fragment to backstack or not
     */
    private void addReplaceFragment(boolean add, Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isFragmentInBackstack(fragmentManager, fragment.getClass().getSimpleName())) {
            fragmentManager.popBackStack(fragment.getClass().getSimpleName(), 0);
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (add) {
                fragmentTransaction.add(R.id.fl_container, fragment, fragment.getClass().getSimpleName());
            } else {
                fragmentTransaction.replace(R.id.fl_container, fragment, fragment.getClass().getSimpleName());
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * Method to check if the given fragment is already in backstack or not.
     *
     * @param fragmentManager object of fragment manager
     * @param tag             is the tag geiven to fragment
     * @return true if fragment exists in backstack
     */
    private boolean isFragmentInBackstack(FragmentManager fragmentManager, String tag) {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            if (tag.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overridden  method for device back press
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            showExitDialog();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method to seek confirmation before exit app
     */
    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_title))
                .setMessage(getString(R.string.exit_body))
                .setPositiveButton(getString(R.string.exit_yes), (dialog, which) -> {
                    // Exit Application
                    this.finish();
                })
                .setNegativeButton(getString(R.string.exit_No), (dialog, which) -> {
                    // just dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }
}
