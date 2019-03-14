package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.model.restaurantItems.RestaurantItemsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantItemsSwipeRecycler extends RecyclerView.Adapter<RestaurantItemsSwipeRecycler.MyViewHolder> {
    Context context;
    List<RestaurantItemsData> itemsData = new ArrayList<>();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public RestaurantItemsSwipeRecycler(Context context, List<RestaurantItemsData> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_swipe_restaurant_items, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        RestaurantItemsData restaurantItemsData = itemsData.get(position);
        viewBinderHelper.bind(myViewHolder.restaurantSwipeItemsSwipeRevealLayout, String.valueOf(restaurantItemsData.getId()));
        Glide.with(context).load(itemsData.get(position).getPhotoUrl()).into(myViewHolder.RestaurantSwipeItemsImageViewProduct);
        myViewHolder.RestaurantSwipeItemsTvProductName.setText(itemsData.get(position).getName());
        myViewHolder.RestaurantSwipeItemsTvProductDescription.setText(itemsData.get(position).getDescription());
        myViewHolder.RestaurantSwipeItemsTvProductPrice.setText(itemsData.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Restaurant_Swipe_Items_Btn_product_Edit)
        ImageButton RestaurantSwipeItemsBtnProductEdit;
        @BindView(R.id.Restaurant_Swipe_Items_Btn_product_Delete)
        ImageButton RestaurantSwipeItemsBtnProductDelete;
        @BindView(R.id.Restaurant_Swipe_Items_ImageView_product)
        ImageView RestaurantSwipeItemsImageViewProduct;
        @BindView(R.id.Restaurant_Swipe_Items_Tv_product_Name)
        TextView RestaurantSwipeItemsTvProductName;
        @BindView(R.id.Restaurant_Swipe_Items_Tv_product_Description)
        TextView RestaurantSwipeItemsTvProductDescription;
        @BindView(R.id.Restaurant_Swipe_Items_Tv_product_Price)
        TextView RestaurantSwipeItemsTvProductPrice;
        @BindView(R.id.restaurant_Swipe_Items_SwipeRevealLayout)
        SwipeRevealLayout restaurantSwipeItemsSwipeRevealLayout;
        private View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
