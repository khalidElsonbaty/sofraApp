package com.example.sofra.ui.fragment.restaurantCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.categories.Categories;
import com.example.sofra.data.model.categories.CategoriesData;
import com.example.sofra.data.model.restaurantRegister.RestaurantRegister;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.StateSpinner;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.support.constraint.Constraints.TAG;
import static com.example.sofra.helper.HelperMethod.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantSecondRegisterFragment extends Fragment {

    @BindView(R.id.Restaurant_Register_Spn_Category)
    Spinner RestaurantRegisterSpnCategory;
    @BindView(R.id.Restaurant_Register_Et_Min_Order)
    EditText RestaurantRegisterEtMinOrder;
    @BindView(R.id.Restaurant_Register_Et_delivery)
    EditText RestaurantRegisterEtDelivery;
    @BindView(R.id.Restaurant_Register_Et_Phone)
    EditText RestaurantRegisterEtPhone;
    @BindView(R.id.Restaurant_Register_Et_whatsUp)
    EditText RestaurantRegisterEtWhatsUp;
    Unbinder unbinder;
    @BindView(R.id.Restaurant_Register_Et_Image)
    ImageView RestaurantRegisterEtImage;
    private View view;
    int region_Id;
    private String name;
    private String email;
    private String rePassword;
    private String password;
    private ApiServices apiServices;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;
    private String phone;
    private String whatsUp;
    private String delivery_cost;
    private String minimum_charge;
    private String availability = "Open";
    private RequestBody requestBodyName;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyPassword;
    private RequestBody requestBodyRePassword;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyWhatsUp;
    private RequestBody requestBodyDeliveryCost;
    private RequestBody requestBodyRegionId;
    private RequestBody requestBodyMinimumCharge;
    private RequestBody requestBodyAvailability;


    public RestaurantSecondRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_second_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        name = getArguments().getString("name");
        email = getArguments().getString("email");
        password = getArguments().getString("password");
        rePassword = getArguments().getString("rePassword");
        region_Id = getArguments().getInt("region_id", 0);

        getCategory();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.Restaurant_Register_Et_Image, R.id.Restaurant_Register_Btn_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Restaurant_Register_Et_Image:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ImagesFiles.clear();
                        ImagesFiles.addAll(result);
                        Glide.with(getActivity()).load(ImagesFiles.get(0).getPath())
                                .into(RestaurantRegisterEtImage);
                        String path = ImagesFiles.get(0).getPath();
                        photoRequestBody = HelperMethod.convertFileToMultipart(path, "photo");

                    }
                };

                openAlbum(3, getActivity(), ImagesFiles, action);
                break;
            case R.id.Restaurant_Register_Btn_Register:
                phone = RestaurantRegisterEtPhone.getText().toString();
                whatsUp = RestaurantRegisterEtWhatsUp.getText().toString();
                delivery_cost = RestaurantRegisterEtDelivery.getText().toString();
                minimum_charge = RestaurantRegisterEtMinOrder.getText().toString();
                sendRestaurantRegisterRequest();
                break;
        }
    }

    private void sendRestaurantRegisterRequest() {


        requestBodyName = HelperMethod.convertToRequestBody(name);
        requestBodyEmail = HelperMethod.convertToRequestBody(email);
        requestBodyPassword = HelperMethod.convertToRequestBody(password);
        requestBodyRePassword = HelperMethod.convertToRequestBody(rePassword);
        requestBodyPhone = HelperMethod.convertToRequestBody(phone);
        requestBodyWhatsUp = HelperMethod.convertToRequestBody(whatsUp);
        requestBodyDeliveryCost = HelperMethod.convertToRequestBody(delivery_cost);
        requestBodyMinimumCharge = HelperMethod.convertToRequestBody(minimum_charge);
        requestBodyAvailability = HelperMethod.convertToRequestBody(availability);
        requestBodyRegionId = HelperMethod.convertToRequestBody(String.valueOf(region_Id));
        List<String> categoryList = SpinnerAdapter.selectedCategory;
        List<RequestBody> category = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            category.add(HelperMethod.convertToRequestBody(categoryList.get(i)));
        }


        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.addRestaurantRegister(requestBodyName, requestBodyEmail, requestBodyPassword,
                requestBodyRePassword, requestBodyPhone, requestBodyWhatsUp, requestBodyRegionId,
                category, requestBodyDeliveryCost, requestBodyMinimumCharge, photoRequestBody,
                requestBodyAvailability).enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                Log.i("onResponse", "onResponse: "+ response.raw());
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RestaurantRegister> call, Throwable t) {

            }
        });
    }

    private void getCategory() {

        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.body().getStatus() == 1) {
                    List<CategoriesData> data = response.body().getData();
                    List<String> cityNames = new ArrayList<>();
                    final List<Integer> cityId = new ArrayList<>();
                    ArrayList<StateSpinner> stateSpinnerList = new ArrayList<>();
                    /*cityNames.add(getActivity().getResources().getString(R.string.spn_categories));
                    cityId.add(0);*/
                    StateSpinner stateSpinner = new StateSpinner();
                    stateSpinner.setTitle("تصنيفات");
                    stateSpinner.setSelected(false);
                    stateSpinnerList.add(stateSpinner);
                    for (int i = 0; i < data.size(); i++) {
                        StateSpinner stateSpinner1 = new StateSpinner();
                        stateSpinner1.setTitle(data.get(i).getName());
                        stateSpinner1.setSelected(false);
                        stateSpinnerList.add(stateSpinner1);
                        /*cityNames.add(data.get(i).getName());
                        cityId.add(data.get(i).getId());*/

                    }
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), 0, stateSpinnerList);
                    RestaurantRegisterSpnCategory.setAdapter(spinnerAdapter);

                  /*  ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , cityNames);
                    RestaurantRegisterSpnCategory.setAdapter(spinnerCountryAdapter);
                    RestaurantRegisterSpnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position != 0) {
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });*/


                } else {
                    Log.i(TAG, "onRespo" + "sorry");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });


    }
}
