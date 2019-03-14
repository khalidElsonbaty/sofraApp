package com.example.sofra.adapter;

import android.app.Activity;
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
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersRecycler extends RecyclerView.Adapter<OffersRecycler.MyViewHolder> {
    Context context;
    List<OffersData> offersData = new ArrayList<>();

    public OffersRecycler(Context context, List<OffersData> offersData) {
        this.context = context;
        this.offersData = offersData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_offers_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Glide.with(context).load("http://ipda3.com/sofra/" + offersData.get(position).getPhoto()).into(myViewHolder.RestaurantItemsImageViewRestaurant);
        myViewHolder.OffersTvRestaurantOffer.setText(offersData.get(position).getDescription());
        myViewHolder.OffersTvRestaurantName.setText(offersData.get(position).getRestaurant().getName());
        String startDate = HelperMethod.formatDate(offersData.get(position).getStartingAt());
        String endDate = HelperMethod.formatDate(offersData.get(position).getEndingAt());
        myViewHolder.OffersTvRestaurantAvailable.setText(startDate+" : "+endDate);
        myViewHolder.OffersTvRestaurantPrice.setText(offersData.get(position).getPrice());

    }


    @Override
    public int getItemCount() {
        return offersData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Restaurant_Items_ImageView_Restaurant)
        ImageView RestaurantItemsImageViewRestaurant;
        @BindView(R.id.Offers_Tv_Restaurant_Offer)
        TextView OffersTvRestaurantOffer;
        @BindView(R.id.Offers_Tv_Restaurant_Name)
        TextView OffersTvRestaurantName;
        @BindView(R.id.Offers_Tv_Restaurant_available)
        TextView OffersTvRestaurantAvailable;
        @BindView(R.id.Offers_Tv_Restaurant_price)
        TextView OffersTvRestaurantPrice;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
