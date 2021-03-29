package com.cenkkaraboa.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cenkkaraboa.myapplication.R;

public class CategoryActivity extends AppCompatActivity  implements View.OnClickListener {
    ImageView exit;
    LinearLayout car, home, electronic, clothes, book, cosmetic, bicycle, other, spor, farm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        exit=findViewById(R.id.exit);

        car=findViewById(R.id.car);
        home=findViewById(R.id.home);
        electronic=findViewById(R.id.electronic);
        clothes=findViewById(R.id.clothes);
        book=findViewById(R.id.book);
        cosmetic=findViewById(R.id.cosmetic);
        bicycle=findViewById(R.id.bicycle);
        other=findViewById(R.id.other);
        spor=findViewById(R.id.spor);
        farm=findViewById(R.id.farm);

        car.setOnClickListener(this);
        home.setOnClickListener(this);
        electronic.setOnClickListener(this);
        clothes.setOnClickListener(this);
        book.setOnClickListener(this);
        cosmetic.setOnClickListener(this);
        bicycle.setOnClickListener(this);
        other.setOnClickListener(this);
        spor.setOnClickListener(this);
        farm.setOnClickListener(this);




        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String category = "";
        int id = v.getId();
        switch (id) {
            case R.id.car:
                category = "Araba";
                break;
            case R.id.home:
                category = "Emlak ve Ev Eşyaları";
                break;
            case R.id.electronic:
                category = "Elektronik";
                break;
            case R.id.clothes:
                category = "Kıyafet";
                break;
            case R.id.book:
                category = "Kitap";
                break;
            case R.id.cosmetic:
                category = "Kozmetik Ürünleri";
                break;
            case R.id.bicycle:
                category = "Bisiklet";
                break;
            case R.id.other:
                category = "Diğer araçlar ve Ekipmanlar";
                break;
            case R.id.spor:
                category = "Spor, Eğlence ve Oyun Eşyaları";
                break;
            case R.id.farm:
                category = "Traktör, Hayvancılık ve Tarım Aletleri";
                break;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedCategory",category);
        editor.apply();
        finish();
    }
}