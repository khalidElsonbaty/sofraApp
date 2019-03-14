package com.example.sofra.ui.fragment.clientCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantItemsRecycler;
import com.example.sofra.adapter.RestaurantReviewsRecycler;
import com.example.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.example.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.example.sofra.data.model.restaurantReviews.RestaurantReviewsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.OnEndless;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFeedBacksFragment extends Fragment {

    private View view;
    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<RestaurantReviewsData> restaurantReviewsData = new ArrayList<>();
    private RestaurantReviewsRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;

    public RestaurantFeedBacksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_feedbacks, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.Restaurant_FeedBacks_RecyclerView_Clients);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setRestaurantFeedBacksData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new RestaurantReviewsRecycler(getContext(), restaurantReviewsData);
        myRecyclerView.setAdapter(recyclerViewAdapter);

        setRestaurantFeedBacksData(1);


        return view;
    }

    private void setRestaurantFeedBacksData(int page) {
        apiServices.getRestaurantReviews("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",
                25, page).enqueue(new Callback<RestaurantReviews>() {
            @Override
            public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                restaurantReviewsData.addAll(response.body().getData().getData());
                max = response.body().getData().getLastPage();
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RestaurantReviews> call, Throwable t) {

            }
        });
    }

}
