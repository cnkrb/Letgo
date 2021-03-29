package com.cenkkaraboa.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SolidAdapter extends RecyclerView.Adapter<SolidAdapter.Holder>  {
    public List<Product> itemList;
    public Context context;
    private OnItemClickListener mListener;

    public SolidAdapter(List<Product> itemList, Context context) {
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
    public SolidAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SolidAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SolidAdapter.Holder holder, int position) {
       holder.name.setText(itemList.get(position).getStatement());
        String baseUrl="http://quiz.cenkkaraboa.com/public/products/";
        Picasso.get()
                .load(baseUrl+itemList.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public Holder(final View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            image= itemView.findViewById(R.id.image);
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