package com.cenkkaraboa.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.activities.İtemActivity;
import com.cenkkaraboa.myapplication.adapters.ProductAdapter;
import com.cenkkaraboa.myapplication.adapters.İtemAdapter;
import com.cenkkaraboa.myapplication.models.CityModel;
import com.cenkkaraboa.myapplication.models.FavoriteModel;
import com.cenkkaraboa.myapplication.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;


public class FavoriteFragment extends Fragment {


    View view;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        getFavorite();
        return  view;
    }

    public void getFavorite(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String user=preferences.getString("mail","null");
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        //searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerView.setAdapter(searchAdapter);
                    }else {
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),requireContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
                       // searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerView.setAdapter(searchAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        apiService.getFavorite("10").enqueue(listCallBack);
    }
}