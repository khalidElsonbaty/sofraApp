package com.example.sofra.ui.fragment.clientCycle;


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
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.cities.Cities;
import com.example.sofra.data.model.cities.CitiesData;
import com.example.sofra.data.model.clientRegister.ClientRegister;
import com.example.sofra.data.model.regions.Regions;
import com.example.sofra.data.model.regions.RegionsData;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;

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
public class ClientRegisterFragment extends Fragment {


    @BindView(R.id.Client_Register_Et_Name)
    EditText ClientRegisterEtName;
    @BindView(R.id.Client_Register_Et_Email)
    EditText ClientRegisterEtEmail;
    @BindView(R.id.Client_Register_Et_Phone)
    EditText ClientRegisterEtPhone;
    @BindView(R.id.Client_Register_Spn_City)
    Spinner ClientRegisterSpnCity;
    @BindView(R.id.Client_Register_Spn_Region)
    Spinner ClientRegisterSpnRegion;
    @BindView(R.id.Client_Register_Et_Home)
    EditText ClientRegisterEtHome;
    @BindView(R.id.Client_Register_Et_Password)
    EditText ClientRegisterEtPassword;
    @BindView(R.id.Client_Register_Et_RePassword)
    EditText ClientRegisterEtRePassword;
    private ApiServices apiServices;
    private Integer region_Id;
    Unbinder unbinder;

    public ClientRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        getCity();
        //getRegion(1);

        return view;
    }

    private void SendRegisterData() {
        String name = ClientRegisterEtName.getText().toString();
        String email = ClientRegisterEtEmail.getText().toString();
        String password = ClientRegisterEtPassword.getText().toString();
        String rePassword = ClientRegisterEtRePassword.getText().toString();
        String phone = ClientRegisterEtPhone.getText().toString();
        String address = ClientRegisterEtHome.getText().toString();
        Integer region = region_Id;
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.addClientRegister(name, email, password, rePassword, phone, address, 2)
                .enqueue(new Callback<ClientRegister>() {
                    @Override
                    public void onResponse(Call<ClientRegister> call, Response<ClientRegister> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientRegister> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());

                    }
                });
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
                    ClientRegisterSpnCity.setAdapter(spinnerCountryAdapter);
                    ClientRegisterSpnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    ClientRegisterSpnRegion.setAdapter(spinnerCountryAdapter);
                    ClientRegisterSpnRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Log.i(TAG, "onRespo" + "sorry");
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.Client_Register_Btn_Register)
    public void onViewClicked() {
        SendRegisterData();
    }
}
