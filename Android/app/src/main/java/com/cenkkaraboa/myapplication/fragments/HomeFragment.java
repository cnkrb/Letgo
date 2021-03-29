package com.cenkkaraboa.myapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.activities.CategoryActivity;
import com.cenkkaraboa.myapplication.activities.LocationActivity;
import com.cenkkaraboa.myapplication.activities.MainActivity;
import com.cenkkaraboa.myapplication.activities.ShowProductActivity;
import com.cenkkaraboa.myapplication.adapters.ProductAdapter;
import com.cenkkaraboa.myapplication.models.Product;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;


public class HomeFragment extends Fragment  implements ProductAdapter.OnItemClickListener {

    View view;
    ImageView navigation,close;
    TextView textSearch,location,selectCategory;
    RelativeLayout vitrin,category;
    LinearLayout changeLocation,car,home,electronic;
    RecyclerView recyclerViewProduct;
    List<Product> productList;
    CardView cardSelect;
    LinearLayout categories;
    String loca="Konum Seçiniz";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        navigation = view.findViewById(R.id.navigation);
        textSearch = view.findViewById(R.id.textSearch);
        categories = view.findViewById(R.id.categories);
        car = view.findViewById(R.id.car);
        home = view.findViewById(R.id.home);
        electronic = view.findViewById(R.id.electronic);
        selectCategory = view.findViewById(R.id.selectCategory);
        cardSelect = view.findViewById(R.id.cardSelect);
        location = view.findViewById(R.id.location);
        close = view.findViewById(R.id.close);
        recyclerViewProduct = view.findViewById(R.id.recyclerViewProduct);
        vitrin = view.findViewById(R.id.vitrin);
        changeLocation = view.findViewById(R.id.changeLocation);
        textSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.setVisibility(View.GONE);
                cardSelect.setVisibility(View.VISIBLE);
                selectCategory.setText("Araba");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedCategory","Araba");
                editor.apply();
                load(true);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.setVisibility(View.GONE);
                cardSelect.setVisibility(View.VISIBLE);
                selectCategory.setText("Elektronik");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedCategory","Elektronik");
                editor.apply();
                load(true);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.setVisibility(View.GONE);
                cardSelect.setVisibility(View.VISIBLE);
                selectCategory.setText("Emlak ve Ev Eşyaları");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedCategory","Emlak ve Ev Eşyaları");
                editor.apply();
                load(true);
            }
        });
        category = view.findViewById(R.id.category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        vitrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.setVisibility(View.VISIBLE);
                cardSelect.setVisibility(View.GONE);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedCategory","null");
                editor.apply();
                load(false);
            }
        });
        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LocationActivity.class);
                startActivity(intent);
            }
        });
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).openDrawer();
            }
        });
        return view;
    }

    public void load(Boolean category){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String city=preferences.getString("city","null");
        String town=preferences.getString("town","null");
        String district=preferences.getString("district","null");
        String selectedCategory=preferences.getString("selectedCategory","null");
        if(!city.equals("null")){
            loca=city;
            if(!town.equals("null")){
                loca=city+" "+town;
            }
            if(!district.equals("null")){
                loca=city+" "+town+" "+district;
            }
                    location.setText(loca);
                    if(category){
                        getCategory(loca,selectedCategory);
                    }else {
                        getLocation(loca);
                    }
        }else {
            Toast.makeText(getContext(),"En az bir tane bölge seçmelisiniz",Toast.LENGTH_SHORT).show();
        }
    }

    public void getLocation(String location){
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        productList=response.body();
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),getContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),2));
                        searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    }else {
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),requireContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(requireContext(),2));
                        searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        apiService.searchLocation(location).enqueue(listCallBack);
    }

    public void getCategory(String location,String category){
        Callback<List<Product>> listCallBack = new Callback<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0){
                        productList=response.body();
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),getContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),2));
                        searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    }else {
                        ProductAdapter searchAdapter=new ProductAdapter(response.body(),requireContext());
                        recyclerViewProduct.setLayoutManager(new GridLayoutManager(requireContext(),2));
                        searchAdapter.setOnItemClickListener(HomeFragment.this);
                        recyclerViewProduct.setAdapter(searchAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        };
        apiService.searchCategory(location,category).enqueue(listCallBack);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String selectedCategory=preferences.getString("selectedCategory","null");
        if(selectedCategory.equals("null")){
            categories.setVisibility(View.VISIBLE);
            cardSelect.setVisibility(View.GONE);
            load(false);
        }else {
            categories.setVisibility(View.GONE);
            cardSelect.setVisibility(View.VISIBLE);
            selectCategory.setText(selectedCategory);
            load(true);
        }

    }

    @Override
    public void onItemClickProduct(int position) {
         Intent intent =new Intent(getContext(), ShowProductActivity.class);
         intent.putExtra("product",productList.get(position));
         startActivity(intent);
    }

}