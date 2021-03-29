package com.cenkkaraboa.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cenkkaraboa.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BottomFragment extends Fragment  implements  BottomNavigationView.OnNavigationItemSelectedListener {


    View view;
    BottomNavigationView navigation;
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_bottom, container, false);
        navigation=view.findViewById(R.id.nav_view);
        fab=view.findViewById(R.id.fab);
        navigation.getMenu().getItem(2).setEnabled(false);
        navigation.setBackground(null);
        navigation.setOnNavigationItemSelectedListener(this);
        moveToFragment(new HomeFragment());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToFragment(new AddFragment());
            }
        });
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        Fragment fragment=null;
        switch (id) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_notification:
                fragment = new NotificationFragment();
                break;
            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }
        moveToFragment(fragment);
        return false;
    }
    private void moveToFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        manager.popBackStack();
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}