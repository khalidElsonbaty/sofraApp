package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.listOfRestaurants.Category;
import com.example.sofra.data.model.listOfRestaurants.ListOfRestaurantsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.ClientHomeActivity;
import com.example.sofra.ui.fragment.clientCycle.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeClientRestaurantRecycler extends RecyclerView.Adapter<HomeClientRestaurantRecycler.MyViewHolder> {
    Context context;
    List<ListOfRestaurantsData> dataList = new ArrayList<>();

    public HomeClientRestaurantRecycler(Context context, List<ListOfRestaurantsData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_client_home_restaurant, viewGroup, false);
        HomeClientRestaurantRecycler.MyViewHolder viewHolder = new HomeClientRestaurantRecycler.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        Glide.with(context).load(dataList.get(position).getPhotoUrl()).into(myViewHolder.restaurantImage);
        myViewHolder.restaurantName.setText(dataList.get(position).getName());
        StringBuilder categories = new StringBuilder();
        List<Category> categoryList = dataList.get(position).getCategories();
        for (int i = 0; categoryList.size() > i; i++) {
            categories.append(categoryList.get(i).getName() + ".");
        }
        myViewHolder.restaurantCategory.setText(categories.toString());
        myViewHolder.restaurantRatingBar.setRating(dataList.get(position).getRate());
        myViewHolder.restaurantAvailable.setText(dataList.get(position).getAvailability());
        myViewHolder.deliveryPeriod.setText(dataList.get(position).getMinimumCharger());
        myViewHolder.deliveryCost.setText(dataList.get(position).getDeliveryCost());
        myViewHolder.restaurantCardView.setOnClickListener(new View.OnClickListener() {
            String restaurantName = dataList.get(position).getName();

            @Override
            public void onClick(View v) {
                TextView toolbarTitle = null;
                ClientHomeActivity clientHomeActivity = (ClientHomeActivity) context;
                toolbarTitle = clientHomeActivity.toolbarTitle;
                HelperMethod.replace(new RestaurantDetailsFragment(), ((FragmentActivity) context).getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "مطعم " + restaurantName);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView restaurantImage;
        private TextView restaurantName;
        private TextView restaurantCategory;
        private RatingBar restaurantRatingBar;
        private TextView restaurantAvailable;
        private TextView deliveryPeriod;
        private TextView deliveryCost;
        private CardView restaurantCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.Home_Client_ImageView_Restaurant);
            restaurantName = (TextView) itemView.findViewById(R.id.Home_Client_Tv_Restaurant_Name);
            restaurantCategory = (TextView) itemView.findViewById(R.id.Home_Client_Tv_Restaurant_Category);
            restaurantAvailable = (TextView) itemView.findViewById(R.id.Home_Client_Tv_Restaurant_Available);
            restaurantRatingBar = (RatingBar) itemView.findViewById(R.id.Home_Client_RatingBar_Restaurant);
            deliveryPeriod = (TextView) itemView.findViewById(R.id.Home_Client_Tv_Restaurant_delivery_period_Result);
            deliveryCost = (TextView) itemView.findViewById(R.id.Home_Client_Tv_Restaurant_delivery_cost_Result);
            restaurantCardView = (CardView) itemView.findViewById(R.id.Home_Client_CardView_Restaurant);

        }
    }
}
