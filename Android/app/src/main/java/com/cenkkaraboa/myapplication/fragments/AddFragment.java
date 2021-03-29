package com.cenkkaraboa.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.activities.AddAdActivity;


public class AddFragment extends Fragment implements View.OnClickListener {

    View view;
    CardView car, home, electronic, clothes, book, cosmetic, bicycle, other, spor, farm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        car = view.findViewById(R.id.car);
        home = view.findViewById(R.id.home);
        electronic = view.findViewById(R.id.electronic);
        clothes = view.findViewById(R.id.clothes);
        book = view.findViewById(R.id.book);
        cosmetic = view.findViewById(R.id.cosmetic);
        bicycle = view.findViewById(R.id.bicycle);
        other = view.findViewById(R.id.other);
        spor = view.findViewById(R.id.spor);
        farm = view.findViewById(R.id.farm);

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
        return view;
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
        Intent intent = new Intent(getContext(), AddAdActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}