package com.cenkkaraboa.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>  {
    public List<Product> itemList;
    public Context context;
    private OnItemClickListener mListener,favListener;

    public ProductAdapter(List<Product> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    public interface OnItemClickListener {
        void onItemClickProduct(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }






    @Override
    public ProductAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ProductAdapter.Holder holder, int position) {
       holder.name.setText(itemList.get(position).getStatement());
       holder.price.setText(itemList.get(position).getPrice());
        String baseUrl="http://quiz.cenkkaraboa.com/public/products/";
        Picasso.get()
                .load(baseUrl+itemList.get(position).getImage())
                .into(holder.image);
       holder.favorite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              String imageName= (String) holder.favorite.getTag();
               if(imageName.length()==3){
                   holder.favorite.setTag("fava");
                   holder.favorite.setImageResource(R.drawable.heart_red);
               }else {
                   holder.favorite.setTag("fav");
                   holder.favorite.setImageResource(R.drawable.heart_black);
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView image,favorite;

        public Holder(final View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            price= itemView.findViewById(R.id.price);
            image= itemView.findViewById(R.id.image);
            favorite= itemView.findViewById(R.id.favorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClickProduct(position);
                        }
                    }
                }
            });

        }
    }

}