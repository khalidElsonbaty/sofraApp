package com.example.sofra.ui.fragment.restaurantCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.cities.Cities;
import com.example.sofra.data.model.cities.CitiesData;
import com.example.sofra.data.model.regions.Regions;
import com.example.sofra.data.model.regions.RegionsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.RestaurantHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantRegisterFragment extends Fragment {


    @BindView(R.id.Restaurant_Register_Et_Name)
    EditText RestaurantRegisterEtName;
    @BindView(R.id.Restaurant_Register_Spn_City)
    Spinner RestaurantRegisterSpnCity;
    @BindView(R.id.Restaurant_Register_Spn_Region)
    Spinner RestaurantRegisterSpnRegion;
    @BindView(R.id.Restaurant_Register_Et_Email)
    EditText RestaurantRegisterEtEmail;
    @BindView(R.id.Restaurant_Register_Et_Password)
    EditText RestaurantRegisterEtPassword;
    @BindView(R.id.Restaurant_Register_Et_Re_Password)
    EditText RestaurantRegisterEtRePassword;
    Unbinder unbinder;
    private ApiServices apiServices;
    private Integer region_Id;

    public RestaurantRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        getCity();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Restaurant_Register_Btn_Register)
    public void onViewClicked() {
        TextView toolbarTitle=null;

        RestaurantHomeActivity restaurantHomeActivity = (RestaurantHomeActivity) getActivity();
        toolbarTitle = restaurantHomeActivity.restaurantToolbarTitle;
        String name = RestaurantRegisterEtName.getText().toString();
        String email = RestaurantRegisterEtEmail.getText().toString();
        String password = RestaurantRegisterEtPassword.getText().toString();
        String rePassword = RestaurantRegisterEtRePassword.getText().toString();
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        bundle.putString("email",email);
        bundle.putString("password",password);
        bundle.putString("rePassword",rePassword);
        bundle.putInt("region_id",region_Id);
        RestaurantSecondRegisterFragment restaurantSecondRegisterFragment = new RestaurantSecondRegisterFragment();
        restaurantSecondRegisterFragment.setArguments(bundle);
        HelperMethod.replace(restaurantSecondRegisterFragment,getActivity().getSupportFragmentManager(),R.id.Restaurant_Home_Fragment_Container,toolbarTitle,"انشاء حساب جديد");

    }

    private void getCity() {

        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    List<CitiesData> data = response.body().getData().getData();
                    List<String> cityNames = new ArrayList<>();
                    final List<Integer> cityId = new ArrayList<>();
                    cityNames.add(getActivity().getResources().getString(R.string.spn_city));
                    cityId.add(0);
                    for (int i = 0; i < data.size(); i++) {
                        cityNames.add(data.get(i).getName());
                        cityId.add(data.get(i).getId());
                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , cityNames);
                    RestaurantRegisterSpnCity.setAdapter(spinnerCountryAdapter);
                    RestaurantRegisterSpnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position != 0) {
                                getRegion(cityId.get(position));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } else {
                    Log.i(TAG, "onRespo" + "sorry");
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });


    }

    private void getRegion(Integer id) {
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.getRegions(id).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                if (response.body().getStatus() == 1) {
                    List<RegionsData> data = response.body().getData().getData();
                    List<String> regionNames = new ArrayList<>();
                    final List<Integer> regionId = new ArrayList<>();
                    regionNames.add(getActivity().getResources().getString(R.string.spn_region));
                    regionId.add(0);
                    for (int i = 0; i < data.size(); i++) {
                        regionNames.add(data.get(i).getName());
                        regionId.add(data.get(i).getId());
                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , regionNames);
                    RestaurantRegisterSpnRegion.setAdapter(spinnerCountryAdapter);
                    RestaurantRegisterSpnRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position != 0) {
                                region_Id = (regionId.get(position));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } else {
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {

            }
        });


    }
}
