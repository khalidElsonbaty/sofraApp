package com.example.sofra.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_order, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.MY_Order_TabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.My_Order_ViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new CompletedOrderFragment(), "طلبات سابقة");
        adapter.addFragment(new CurrentOrderFragment(), "طلبات حالية");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
