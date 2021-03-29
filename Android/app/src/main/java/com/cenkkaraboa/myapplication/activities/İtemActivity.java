package com.cenkkaraboa.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.adapters.SellingAdapter;
import com.cenkkaraboa.myapplication.adapters.İtemAdapter;
import com.cenkkaraboa.myapplication.fragments.SellingFragment;
import com.cenkkaraboa.myapplication.models.CityModel;
import com.cenkkaraboa.myapplication.models.DistrictModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;

public class İtemActivity extends AppCompatActivity  implements İtemAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    List<CityModel> city;
    List<DistrictModel> town;
    List<DistrictModel> district;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        recyclerView=findViewById(R.id.recyclerView);
        String value = Objects.requireNonNull(getIntent().getExtras()).getString("value");
        if(value.equals("city")){
            getCities();
        }else if(value.equals("town")){
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String id=preferences.getString("id","null");
            getTown(id);
        }else if(value.equals("district")){
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String id=preferences.getString("id","null");
            String town=preferences.getString("town","null");
            getDistrict(id,town);
        }
    }

    public void getCities() {
        Callback<List<CityModel>> listCallBack = new Callback<List<CityModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful()) {
                    city=response.body();
                    İtemAdapter searchAdapter = new İtemAdapter(response.body(), getApplicationContext(),true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    searchAdapter.setOnItemClickListener(İtemActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
            }
        };
        apiService.getCities().enqueue(listCallBack);
    }

    public void getTown(String id) {
        Callback<List<DistrictModel>> listCallBack = new Callback<List<DistrictModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DistrictModel>> call, Response<List<DistrictModel>> response) {
                if (response.isSuccessful()) {
                    town=response.body();
                    İtemAdapter searchAdapter = new İtemAdapter(response.body(), getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    searchAdapter.setOnItemClickListener(İtemActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DistrictModel>> call, Throwable t) {
            }
        };
        apiService.getTown(id).enqueue(listCallBack);
    }

    public void getDistrict(String id,String town) {
        Callback<List<DistrictModel>> listCallBack = new Callback<List<DistrictModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DistrictModel>> call, Response<List<DistrictModel>> response) {
                if (response.isSuccessful()) {
                    district=response.body();
                    İtemAdapter searchAdapter = new İtemAdapter(response.body(), getApplicationContext(),"cenk");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    searchAdapter.setOnItemClickListener(İtemActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DistrictModel>> call, Throwable t) {
            }
        };
        apiService.getDistrict(id,town).enqueue(listCallBack);
    }

    @Override
    public void onItemClickProduct(int position) {
        String value = Objects.requireNonNull(getIntent().getExtras()).getString("value");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(value.equals("city")){
            editor.putString("city", city.get(position).getName());
            editor.putString("id", city.get(position).getId());
            editor.putString("town", "null");
            editor.putString("district", "null");
        }else if(value.equals("town")){
            editor.putString("town", town.get(position).getIlce());
            editor.putString("district", "null");
        }else if(value.equals("district")){
            editor.putString("district", district.get(position).getMahalle());
        }
        editor.apply();
        finish();
    }
}