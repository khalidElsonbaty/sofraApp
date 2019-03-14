package com.example.sofra.ui.fragment.clientCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantItemsRecycler;
import com.example.sofra.data.model.restaurantItems.RestaurantItems;
import com.example.sofra.data.model.restaurantItems.RestaurantItemsData;
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
public class RestaurantItemsFragment extends Fragment {


    private View view;
    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<RestaurantItemsData> restaurantItemsData = new ArrayList<>();
    private RestaurantItemsRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;

    public RestaurantItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_items, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.Restaurant_Items_RecyclerView_Products);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setRestaurantItemsData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new RestaurantItemsRecycler(getContext(),restaurantItemsData );
        myRecyclerView.setAdapter(recyclerViewAdapter);

        setRestaurantItemsData(1);

        return view;
    }

    private void setRestaurantItemsData(int page) {
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.getRestaurantItems(25,page).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                if (response.body().getStatus() == 1) {
                    restaurantItemsData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {

            }
        });
    }

}
