package com.example.sofra.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.OffersRecycler;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.OnEndless;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.activity.RestaurantHomeActivity;
import com.example.sofra.ui.fragment.restaurantCycle.AddOfferFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.Offers_Rl_Add_Product)
    RelativeLayout OffersRlAddProduct;
    private View view;
    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<OffersData> offersData = new ArrayList<>();
    private OffersRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;
    private String check= null;
    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_offer, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.Offers_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    addOffersData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new OffersRecycler(getContext(), offersData);
        myRecyclerView.setAdapter(recyclerViewAdapter);
        unbinder = ButterKnife.bind(this, view);
        check = SharedPreferencesManger.LoadStringData(getActivity(), "Key");
        if (check.equalsIgnoreCase("Client")) {
            OffersRlAddProduct.setVisibility(View.GONE);
        }else
            OffersRlAddProduct.setVisibility(View.VISIBLE);

        addOffersData(1);
        return view;
    }

    private void addOffersData(int page) {
        apiServices.getOffers(page).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                if (response.body().getStatus() == 1) {
                    offersData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.toString());

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Offers_Rl_Add_Product)
    public void onViewClicked() {
        RestaurantHomeActivity restaurantHomeActivity = (RestaurantHomeActivity) getActivity();
        TextView toolbarTitle = restaurantHomeActivity.restaurantToolbarTitle;
        HelperMethod.replace(new AddOfferFragment(), getActivity().getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, toolbarTitle, "اضافة عرض");

    }

}
