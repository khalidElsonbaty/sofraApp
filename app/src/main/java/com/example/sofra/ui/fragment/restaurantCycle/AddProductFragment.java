package com.example.sofra.ui.fragment.restaurantCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {
    View view ;
    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_product, container, false);


        return view;
    }

}
