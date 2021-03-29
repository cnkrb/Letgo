package com.cenkkaraboa.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.adapters.SliderAdapterExample;
import com.cenkkaraboa.myapplication.adapters.SliderItem;
import com.cenkkaraboa.myapplication.models.Product;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowProductActivity extends AppCompatActivity {
    TextView title,price,detay,category;
    ImageView close;
    ImageView images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        title=findViewById(R.id.title);
        price=findViewById(R.id.price);
        detay=findViewById(R.id.detay);
        category=findViewById(R.id.category);
        Intent i = getIntent();
        Product product = (Product)i.getSerializableExtra("product");
        close=findViewById(R.id.close);
        images=findViewById(R.id.images);
/*        String baseUrl="http://quiz.cenkkaraboa.com/public/products/";
        Picasso.get()
                .load(baseUrl+product.getImage())
                .into(images);*/

        title.setText(product.getTitle());
        price.setText(product.getPrice());
        category.setText(product.getCategory());
        detay.setText(product.getStatement());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<SliderItem> sliderItems=new ArrayList<>();
        SliderItem sliderItem=new SliderItem();
        SliderItem sliderItem2=new SliderItem();
        sliderItem.setImage(product.getImage());
        sliderItem2.setImage(product.getImageTwo());



        sliderItems.add(sliderItem);
        sliderItems.add(sliderItem2);


        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(this,sliderItems);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
}