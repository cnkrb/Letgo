package com.cenkkaraboa.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cenkkaraboa.myapplication.fragments.ExpiredFragment;
import com.cenkkaraboa.myapplication.fragments.FavoriteFragment;
import com.cenkkaraboa.myapplication.fragments.SellingFragment;
import com.cenkkaraboa.myapplication.fragments.SoldFragment;


public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    public ViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FavoriteFragment();
           }
        else if (position == 1) {
            fragment = new SellingFragment();
           }
        else if (position == 2) {
            fragment = new ExpiredFragment();
        }
        else if (position == 3) {
            fragment = new SoldFragment();
        }

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 4;
    }



    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Favoriler";
        else if (position == 1)
            title = "Satıyor";
        else if (position == 2)
            title = "Süresi Dolmuş";
        else if (position == 3)
            title = "Satıldı";
        return title;
    }
}