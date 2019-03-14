package com.example.sofra.ui.fragment.restaurantCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantItemsSwipeRecycler;
import com.example.sofra.data.model.restaurantItems.RestaurantItems;
import com.example.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.OnEndless;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.activity.RestaurantHomeActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantAddItemsFragment extends Fragment {

    Unbinder unbinder;
    private View view;
    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<RestaurantItemsData> restaurantItemsData;
    private RestaurantItemsSwipeRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;
    private int restaurant_id;

    public RestaurantAddItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_add_items, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        restaurantItemsData = new ArrayList<>();
        myRecyclerView = (RecyclerView) view.findViewById(R.id.Restaurant_Add_Items_RecyclerView_Products);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    addRestaurantItemsData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new RestaurantItemsSwipeRecycler(getContext(), restaurantItemsData);
        myRecyclerView.setAdapter(recyclerViewAdapter);

        addRestaurantItemsData(1);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void addRestaurantItemsData(int page) {
        restaurant_id = SharedPreferencesManger.LoadIntegerData(getActivity(), "restaurant_id");
        apiServices.getRestaurantItems(restaurant_id, 1).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                if (response.body().getStatus() == 1) {
                    restaurantItemsData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Restaurant_Add_Items_Rl_Add_Product)
    public void onViewClicked() {
        RestaurantHomeActivity restaurantHomeActivity = (RestaurantHomeActivity) getActivity();
        TextView toolbarTitle = restaurantHomeActivity.restaurantToolbarTitle;
        HelperMethod.replace(new AddProductFragment(), getActivity().getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, toolbarTitle, "اضافة منتج");

    }
}
