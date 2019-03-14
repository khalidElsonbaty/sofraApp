package com.example.sofra.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.MyOrderRecycler;
import com.example.sofra.data.model.order.Order;
import com.example.sofra.data.model.order.OrderData;
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
public class CurrentOrderFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private ApiServices apiServices;
    private ArrayList<OrderData> orderData = new ArrayList<>();
    private MyOrderRecycler recyclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;

    public CurrentOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_order, container, false);
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.Current_Order_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setCurrentOrderData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        recyclerViewAdapter = new MyOrderRecycler(getContext(), orderData, "current");
        myRecyclerView.setAdapter(recyclerViewAdapter);

        setCurrentOrderData(1);

        return view;
    }

    private void setCurrentOrderData(int page) {
        apiServices.getOrder("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",
                "current", page).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body().getStatus() == 1) {
                    orderData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

}
