package com.example.sofra.ui.fragment.clientCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantDetails.City;
import com.example.sofra.data.model.restaurantDetails.RestaurantDetails;
import com.example.sofra.data.model.restaurantDetails.RestaurantDetailsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.SharedPreferencesManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {

    @BindView(R.id.Restaurant_Info_Tv_Available)
    TextView RestaurantInfoTvAvailable;
    @BindView(R.id.Restaurant_Info_Tv_City)
    TextView RestaurantInfoTvCity;
    @BindView(R.id.Restaurant_Info_Tv_Region)
    TextView RestaurantInfoTvRegion;
    @BindView(R.id.Restaurant_Info_Tv_deliveryPeriod)
    TextView RestaurantInfoTvDeliveryPeriod;
    @BindView(R.id.Restaurant_Info_Tv_deliveryCost)
    TextView RestaurantInfoTvDeliveryCost;
    Unbinder unbinder;
    private View view;
    private ApiServices apiServices;
    private String check = SharedPreferencesManger.LoadStringData(getActivity(), "Key");
    private int restaurant_id = 1;

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        getDetailsData();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void getDetailsData() {
        if (check.equalsIgnoreCase("Client")) {
            restaurant_id = SharedPreferencesManger.LoadIntegerData(getActivity(), "restaurant_id");
        }
        if (check.equalsIgnoreCase("Restaurant")) {
            restaurant_id = 25;
        }
        apiServices.getRestaurantDetails(25).enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                if (response.body().getStatus() == 1) {
                    City city = response.body().getData().getRegion().getCity();
                    RestaurantDetailsData detailsData = response.body().getData();

                    RestaurantInfoTvAvailable.setText(detailsData.getAvailability());
                    RestaurantInfoTvCity.setText(city.getName());
                    RestaurantInfoTvRegion.setText(detailsData.getRegion().getName());
                    RestaurantInfoTvDeliveryPeriod.setText(detailsData.getMinimumCharger());
                    RestaurantInfoTvDeliveryCost.setText(detailsData.getDeliveryCost());

                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetails> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
