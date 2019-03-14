package com.example.sofra.ui.fragment.clientCycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerAdapter;
import com.example.sofra.data.model.restaurantDetails.Category;
import com.example.sofra.data.model.restaurantDetails.RestaurantDetails;
import com.example.sofra.data.model.restaurantDetails.RestaurantDetailsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.fragment.restaurantCycle.RestaurantAddItemsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment {
    @BindView(R.id.Restaurant_Details_ImageView_Restaurant)
    ImageView RestaurantDetailsImageViewRestaurant;
    @BindView(R.id.Restaurant_Details_Tv_Restaurant_Name)
    TextView RestaurantDetailsTvRestaurantName;
    @BindView(R.id.Restaurant_Details_Tv_Restaurant_Category)
    TextView RestaurantDetailsTvRestaurantCategory;
    @BindView(R.id.Restaurant_Details_RatingBar_Restaurant)
    RatingBar RestaurantDetailsRatingBarRestaurant;
    @BindView(R.id.Restaurant_Details_Tv_Restaurant_Available)
    TextView RestaurantDetailsTvRestaurantAvailable;
    @BindView(R.id.Restaurant_Details_Tv_Restaurant_delivery_period_Result)
    TextView RestaurantDetailsTvRestaurantDeliveryPeriodResult;
    @BindView(R.id.Restaurant_Details_Tv_Restaurant_delivery_cost_Result)
    TextView RestaurantDetailsTvRestaurantDeliveryCostResult;
    Unbinder unbinder;
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ApiServices apiServices;
    private String check = SharedPreferencesManger.LoadStringData(getActivity(), "Key");
    private int restaurant_id;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.Restaurant_Details_TabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.Restaurant_Details_ViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new RestaurantInfoFragment(), "معلومات المتجر");
        adapter.addFragment(new RestaurantFeedBacksFragment(), "التعليقات والتقيم");
        if (check.equalsIgnoreCase("Client")) {
            adapter.addFragment(new RestaurantItemsFragment(), "قائمه الطعام");
        }
        if (check.equalsIgnoreCase("Restaurant")) {
            adapter.addFragment(new RestaurantAddItemsFragment(), "قائمه الطعام");
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        getRestaurantDetailsData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void getRestaurantDetailsData() {
        if (check.equalsIgnoreCase("Client")) {
            restaurant_id = SharedPreferencesManger.LoadIntegerData(getActivity(), "restaurant_id");
        }
        if (check.equalsIgnoreCase("Restaurant")) {
            restaurant_id = 25;
        }
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.getRestaurantDetails(restaurant_id).
                enqueue(new Callback<RestaurantDetails>() {
                    @Override
                    public void onResponse
                            (Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                        if (response.body().getStatus() == 1) {
                            RestaurantDetailsData detailsData = response.body().getData();
                            Glide.with(getActivity()).load(detailsData.getPhotoUrl()).into(RestaurantDetailsImageViewRestaurant);
                            RestaurantDetailsTvRestaurantName.setText(detailsData.getName());
                            StringBuilder categories = new StringBuilder();
                            List<Category> categoryList = detailsData.getCategories();
                            for (int i = 0; i < categoryList.size(); i++) {
                                categories.append(categoryList.get(i).getName() + ".");
                            }
                            RestaurantDetailsTvRestaurantCategory.setText(categories.toString());
                            RestaurantDetailsRatingBarRestaurant.setRating(detailsData.getRate());
                            RestaurantDetailsTvRestaurantAvailable.setText(detailsData.getAvailability());
                            RestaurantDetailsTvRestaurantDeliveryPeriodResult.setText(detailsData.getMinimumCharger());
                            RestaurantDetailsTvRestaurantDeliveryCostResult.setText(detailsData.getDeliveryCost());
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
