package com.cenkkaraboa.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.adapters.SellingAdapter;
import com.cenkkaraboa.myapplication.fragments.SellingFragment;
import com.cenkkaraboa.myapplication.models.CityModel;
import com.cenkkaraboa.myapplication.models.DistrictModel;
import com.cenkkaraboa.myapplication.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;

public class LocationActivity extends AppCompatActivity {
    SharedPreferences preferences;
    LinearLayout  city,town,district,location;
    TextView textCity,textTown,textDistrict;
    ImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        city=findViewById(R.id.city);
        town=findViewById(R.id.town);
        location=findViewById(R.id.location);
        district=findViewById(R.id.district);
        textCity=findViewById(R.id.textCity);
        textTown=findViewById(R.id.textTown);
        textDistrict=findViewById(R.id.textDistrict);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        exit=findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),İtemActivity.class);
                intent.putExtra("value", "city");
                startActivity(intent);
            }
        });
        town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=preferences.getString("city","null");
                if(city.equals("null")){
                    Toast.makeText(getApplicationContext(),"Şehir Seçiniz",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getApplicationContext(),İtemActivity.class);
                    intent.putExtra("value", "town");
                    startActivity(intent);
                }
            }
        });
        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=preferences.getString("city","null");
                String town=preferences.getString("town","null");
                if(city.equals("null") || town.equals("null")){
                    Toast.makeText(getApplicationContext(),"Şehir veya İlçe Seçiniz",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getApplicationContext(),İtemActivity.class);
                    intent.putExtra("value", "district");
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String city=preferences.getString("city","null");
        String town=preferences.getString("town","null");
        String district=preferences.getString("district","null");
        if(!city.equals("null")){
            textCity.setText(city);
        }else{
            textCity.setText("Şehrini Seç");
        }
        if(!town.equals("null"))
        {
            textTown.setText(town);
        }else {
            textTown.setText("İlçeni Seç");
        }
        if(!district.equals("null")){
                textDistrict.setText(district);
        }else {
            textDistrict.setText("Mahalleni Seç");
        }
    }
}