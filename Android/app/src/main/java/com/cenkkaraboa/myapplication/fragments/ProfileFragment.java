package com.cenkkaraboa.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.activities.LoginActivity;
import com.cenkkaraboa.myapplication.adapters.ViewPagerAdapter;
import com.cenkkaraboa.myapplication.models.ProfileModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;


public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    View view;
    TextView nameSurname, registerDate;
    CircleImageView profile;
    ImageView profileSettings, exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        registerDate = view.findViewById(R.id.register_date);
        nameSurname = view.findViewById(R.id.name_surname);
        profile = view.findViewById(R.id.profile);
        exit = view.findViewById(R.id.profile_exit);
        profileSettings = view.findViewById(R.id.profile_settings);
        viewPagerAdapter = new ViewPagerAdapter(
                getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mail", "null");
                editor.putString("password", "null");
                editor.apply();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String mail = preferences.getString("mail", "null");
        if (!mail.equals("null")) {
            loadProfile(mail);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String mail = preferences.getString("mail", "null");
        if (!mail.equals("null")) {
            loadProfile(mail);
        }
    }

    public void loadProfile(String username) {
        Callback<ProfileModel> listCallBack = new Callback<ProfileModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    reloadHeaderProfile(response.body().getImage());
                    String date [] =response.body().getCreatedAt().split(" ");
                    String split[]=date[0].split("-");
                    registerDate.setText(split[2]+" "+mounth(Integer.parseInt(split[1]))+" "+split[0]);
                    nameSurname.setText(response.body().getName() + " " + response.body().getSurname());
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
            }
        };
        apiService.getProfile(username).enqueue(listCallBack);

    }

    public String mounth(int mounth){
        String mo="";
        switch (mounth){
            case 1:
                mo="Ocak";
                break;
            case 2:
                mo="Şubat";
                break;
            case 3:
                mo="Mart";
                break;
            case 4:
                mo="Nisan";
                break;
            case 5:
                mo="Mayıs";
                break;
            case 6:
                mo="Haziran";
                break;
            case 7:
                mo="Temmuz";
                break;
            case 8:
                mo="Ağustos";
                break;
            case 9:
                mo="Eylül";
                break;
            case 10:
                mo="Ekim";
                break;
            case 11:
                mo="Kasım";
                break;
            case 12:
                mo="Aralık";
                break;

        }
        return   mo;
    }

    public void reloadHeaderProfile(String url) {
        String baseUrl = "http://quiz.cenkkaraboa.com/public/profile/";
        Picasso.get()
                .load(baseUrl + url)
                .into(profile);
    }
}