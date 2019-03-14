package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.restaurantItems.RestaurantItemsData;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemsRecycler extends RecyclerView.Adapter<RestaurantItemsRecycler.MyViewHolder> {
    Context context;
    List<RestaurantItemsData> itemsData = new ArrayList<>();

    public RestaurantItemsRecycler(Context context, List<RestaurantItemsData> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_items, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Glide.with(context).load(itemsData.get(position).getPhotoUrl()).into(myViewHolder.productImage);
        myViewHolder.productName.setText(itemsData.get(position).getName());
        myViewHolder.productDescription.setText(itemsData.get(position).getDescription());
        myViewHolder.productPrice.setText(itemsData.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productDescription;
        private TextView productPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage= (ImageView) itemView.findViewById(R.id.Restaurant_Items_ImageView_product);
            productName = (TextView) itemView.findViewById(R.id.Restaurant_Items_Tv_product_Name);
            productDescription = (TextView) itemView.findViewById(R.id.Restaurant_Items_Tv_product_Description);
            productPrice = (TextView) itemView.findViewById(R.id.Restaurant_Items_Tv_product_Price);
        }
    }
}
