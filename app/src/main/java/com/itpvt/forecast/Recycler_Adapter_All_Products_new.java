package com.itpvt.forecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Itpvt on 13-Jan-18.
 */

public class Recycler_Adapter_All_Products_new extends RecyclerView.Adapter<Recycler_Adapter_All_Products_new.MyViewHolder>{

    ArrayList<Products_pojo> arrayList= new ArrayList<>();
    Activity activity;

    public Recycler_Adapter_All_Products_new(ArrayList<Products_pojo> arrayList, Context context)
    {

        this.arrayList=arrayList;
        activity=(Activity)context;
    }

    @Override
    public Recycler_Adapter_All_Products_new.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_new,parent,false);
     MyViewHolder viewHolder= new MyViewHolder(view);
     return viewHolder;

    }

    @Override
    public void onBindViewHolder(Recycler_Adapter_All_Products_new.MyViewHolder holder, final int position) {
        holder.name.setText(arrayList.get(position).getProduct_name());
        holder.price.setText(arrayList.get(position).getPrice());
        Glide.with(activity).load(arrayList.get(position).getProduct_uri()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetails.class);
                intent.putExtra("product_id",arrayList.get(position).getProduct_id());
                intent.putExtra("SKU",arrayList.get(position).getSKU());
                activity.startActivity(intent);
            }
        });


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetails.class);
                intent.putExtra("product_id",arrayList.get(position).getProduct_id());
                intent.putExtra("SKU",arrayList.get(position).getSKU());
                activity.startActivity(intent);

//
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView name;
        TextView price;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.tvmodelname);
            imageView=(ImageView)itemView.findViewById(R.id.thumbnail);
            price=(TextView)itemView.findViewById(R.id.tvprice);

        }
    }
}
