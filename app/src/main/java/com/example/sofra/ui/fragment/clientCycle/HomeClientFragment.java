package com.example.sofra.ui.fragment.clientCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.HomeClientRestaurantRecycler;
import com.example.sofra.data.model.listOfRestaurants.ListOfRestaurants;
import com.example.sofra.data.model.listOfRestaurants.ListOfRestaurantsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.OnEndless;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeClientFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<ListOfRestaurantsData> restaurantsData = new ArrayList<>();
    private HomeClientRestaurantRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;

    public HomeClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_client, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.Home_Client_RecyclerView_Restaurant);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setRestaurantData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new HomeClientRestaurantRecycler(getContext(), restaurantsData);
        myRecyclerView.setAdapter(recyclerViewAdapter);

        setRestaurantData(1);
        return view;
    }

    private void setRestaurantData(int page) {
        apiServices.getRestaurants(page).enqueue(new Callback<ListOfRestaurants>() {
            @Override
            public void onResponse(Call<ListOfRestaurants> call, Response<ListOfRestaurants> response) {
                if (response.body().getStatus() == 1) {
                    restaurantsData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ListOfRestaurants> call, Throwable t) {
            }
        });

    }


}
