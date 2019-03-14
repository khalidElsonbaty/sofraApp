package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.example.sofra.data.model.restaurantReviews.RestaurantReviewsData;

import java.util.ArrayList;
import java.util.List;

public class RestaurantReviewsRecycler extends RecyclerView.Adapter<RestaurantReviewsRecycler.MyViewHolder> {
    Context context;
    List<RestaurantReviewsData> dataList = new ArrayList<>();

    public RestaurantReviewsRecycler(Context context, List<RestaurantReviewsData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_feedbacks, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(dataList.get(i).getClient().getName());
        myViewHolder.date.setText(dataList.get(i).getCreatedAt());
        myViewHolder.description.setText(dataList.get(i).getComment());
        myViewHolder.feedBackRatingBar.setRating(Float.parseFloat(dataList.get(i).getRate()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        TextView description;
        RatingBar feedBackRatingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Restaurant_FeedBacks_Tv_product_Name);
            date = (TextView) itemView.findViewById(R.id.Restaurant_FeedBacks_Tv_Restaurant_Date);
            description = (TextView) itemView.findViewById(R.id.Restaurant_FeedBacks_Tv_product_Description);
            feedBackRatingBar = (RatingBar) itemView.findViewById(R.id.Restaurant_FeedBacks_RatingBar_Product);
        }
    }
}
