package com.cenkkaraboa.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.adapters.SolidAdapter;
import com.cenkkaraboa.myapplication.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;

public class SoldFragment extends Fragment implements SolidAdapter.OnItemClickListener {

    View view;
    RecyclerView recyclerViewProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sold, container, false);

        recyclerViewProduct = view.findViewById(R.id.recyclerViewProduct);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String product = preferences.getString("product", "null");
        if (!product.equals("null")) {
            load(product);
        }

        return view;
    }

    public void load(String product) {
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        SolidAdapter searchAdapter = new SolidAdapter(response.body(), getContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        searchAdapter.setOnItemClickListener(SoldFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    } else {
                        SolidAdapter searchAdapter = new SolidAdapter(response.body(), getContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        searchAdapter.setOnItemClickListener(SoldFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        apiService.getProduct(product, 1).enqueue(listCallBack);
    }

    @Override
    public void onItemClickProduct(int position) {

    }
}