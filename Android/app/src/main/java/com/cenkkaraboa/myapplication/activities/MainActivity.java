package com.cenkkaraboa.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ExpandableListView;

import com.cenkkaraboa.myapplication.Utils.Utils;
import com.cenkkaraboa.myapplication.adapters.ExpandableListAdapter;
import com.cenkkaraboa.myapplication.adapters.ProductAdapter;
import com.cenkkaraboa.myapplication.fragments.BottomFragment;
import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.fragments.SearchFragment;
import com.cenkkaraboa.myapplication.models.MenuModel;
import com.cenkkaraboa.myapplication.models.ProfileModel;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.createAPI();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();
        moveToFragment(new BottomFragment());
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mail=preferences.getString("mail","null");
        if(!mail.equals("null")){
            loadProfile(mail);
        }
    }
    public void loadProfile(String username){
        Callback<ProfileModel> listCallBack = new Callback<ProfileModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("product", response.body().getProductId());
                    editor.putString("user_id", response.body().getId());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
            }
        };
        apiService.getProfile(username).enqueue(listCallBack);
    }
    public void moveToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_activity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public  void openDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawer.openDrawer(GravityCompat.START);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void prepareMenuData() {

        Drawable myDrawable=null;

        MenuModel menuModel = new MenuModel("Ana Sayfa", true, false, null, myDrawable); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Mesaj", true, false, null, myDrawable); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if (groupPosition == 0) {
                            moveToFragment(new BottomFragment());
                        } else if (groupPosition == 1) {
                            moveToFragment(new SearchFragment());
                        }
                    }
                }
                return false;
            }
        });

    }

}