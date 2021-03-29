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
import com.cenkkaraboa.myapplication.models.CityModel;
import com.cenkkaraboa.myapplication.models.DistrictModel;
import com.cenkkaraboa.myapplication.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class İtemAdapter extends RecyclerView.Adapter<İtemAdapter.Holder>  {
    public List<CityModel> itemList;
    public List<DistrictModel> itemDistrict;
    public List<DistrictModel> itemDistrict2;
    public Context context;
    private OnItemClickListener mListener;
    Boolean value=false;
    String a="null";

    public İtemAdapter(List<CityModel> itemList, Context context,Boolean value) {
        this.itemList = itemList;
        this.context = context;
        this.value=value;
    }

    public İtemAdapter(List<DistrictModel> itemDistrict2, Context context,String a) {
        this.itemDistrict2 = itemDistrict2;
        this.context = context;
        this.a=a;
    }


    public İtemAdapter(List<DistrictModel> itemDistrict, Context context) {
        this.itemDistrict = itemDistrict;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClickProduct(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    @Override
    public İtemAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new İtemAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(İtemAdapter.Holder holder, int position) {
        if(value){
            holder.text.setText(itemList.get(position).getName());
        }else {
            if(a.equals("null")){
                holder.text.setText(itemDistrict.get(position).getIlce());
            }else {
                holder.text.setText(itemDistrict2.get(position).getMahalle());
            }
        }


    }

    @Override
    public int getItemCount() {
        int b;
        if(value){
           b=itemList.size();
        }else {
            if(a.equals("null")){
                b=itemDistrict.size();

            }else {
                b=itemDistrict2.size();

            }
        }
        return b;

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView text;
        public Holder(final View itemView) {
            super(itemView);
            text= itemView.findViewById(R.id.text);
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