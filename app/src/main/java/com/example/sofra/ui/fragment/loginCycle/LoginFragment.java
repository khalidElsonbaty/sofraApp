package com.example.sofra.ui.fragment.loginCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.example.sofra.data.model.restaurantLogin.User;
import com.example.sofra.data.model.userLogin.UserLogin;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.activity.ClientHomeActivity;
import com.example.sofra.ui.activity.RestaurantHomeActivity;
import com.example.sofra.ui.fragment.clientCycle.ClientRegisterFragment;
import com.example.sofra.ui.fragment.clientCycle.HomeClientFragment;
import com.example.sofra.ui.fragment.clientCycle.RestaurantDetailsFragment;
import com.example.sofra.ui.fragment.restaurantCycle.RestaurantRegisterFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    Unbinder unbinder;
    String Key = SharedPreferencesManger.LoadStringData(getActivity(), "Key");
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    public String name;
    View view;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private String username;
    private String password;
    private ApiServices apiServices;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        usernameWrapper = (TextInputLayout) view.findViewById(R.id.Login_Til_Name);
        usernameWrapper.setHint("اسم المستخدم");
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.Login_Til_Password);
        passwordWrapper.setHint("كلمه المرور");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Login_login_Btn, R.id.Login_Forget_Password_Et, R.id.Login_Register_Btn})
    public void onViewClicked(View view) {
        TextView toolbarTitle = null;
        try {
            ClientHomeActivity clientHomeActivity = (ClientHomeActivity) getActivity();
            toolbarTitle = clientHomeActivity.toolbarTitle;
        } catch (Exception e) {

            RestaurantHomeActivity restaurantHomeActivity = (RestaurantHomeActivity) getActivity();
            toolbarTitle = restaurantHomeActivity.restaurantToolbarTitle;
        }
        switch (view.getId()) {
            case R.id.Login_login_Btn:
                username = usernameWrapper.getEditText().getText().toString();
                password = passwordWrapper.getEditText().getText().toString();
                if (!validateEmail(username)) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    if (Key.equalsIgnoreCase("Client")) {
                        SendClientLoginRequest();
                        HelperMethod.replace(new HomeClientFragment(),getActivity().getSupportFragmentManager(),R.id.Client_Home_Fragment_Container,toolbarTitle,"المطاعم");
                    }
                    if (Key.equalsIgnoreCase("Restaurant")) {
                        SendRESTAURANTLoginRequest();
                    }

                }
                break;
            case R.id.Login_Forget_Password_Et:
                if (Key.equalsIgnoreCase("Client")) {
                    HelperMethod.replace(new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "نسيت كلمه المرور");
                }
                if (Key.equalsIgnoreCase("Restaurant")) {
                    HelperMethod.replace(new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, toolbarTitle, "نسيت كلمه المرور");
                }

                break;
            case R.id.Login_Register_Btn:

                if (Key.equalsIgnoreCase("Client")) {
                    HelperMethod.replace(new ClientRegisterFragment(), getActivity().getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "انشاء حساب جديد");
                }
                if (Key.equalsIgnoreCase("Restaurant")) {
                    HelperMethod.replace(new RestaurantRegisterFragment(), getActivity().getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, toolbarTitle, "انشاء حساب جديد");
                }
                break;
        }
    }

    private void SendRESTAURANTLoginRequest() {
        if (!username.isEmpty() && !password.isEmpty()) {
            apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
            apiServices.sendRestaurantLoginRequest(username, password).enqueue(new Callback<RestaurantLogin>() {
                @Override
                public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                    if (response.body().getStatus() == 1) {
                        User restaurantData = response.body().getData().getUser();
                        SharedPreferencesManger.SaveData(getActivity(),"restaurant_id",restaurantData.getId());
                        HelperMethod.replace(new RestaurantDetailsFragment(),getActivity().getSupportFragmentManager(),R.id.Restaurant_Home_Fragment_Container,null,null);

                    } else
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<RestaurantLogin> call, Throwable t) {

                }
            });
        }

    }

    private void SendClientLoginRequest() {

        if (!username.isEmpty() && !password.isEmpty()) {
            apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
            apiServices.sendUserLoginRequest(username, password).enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {

                }
            });

        }

    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 2;
    }

}
