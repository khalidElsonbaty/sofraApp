package com.example.sofra.ui.fragment.loginCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.resetPassword.ResetPassword;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.activity.ClientHomeActivity;
import com.example.sofra.ui.activity.RestaurantHomeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ForgetPasswordFragment extends Fragment {


    @BindView(R.id.Forget_Password_Et_Email)
    EditText ForgetPasswordEtEmail;
    Unbinder unbinder;
    String Key = SharedPreferencesManger.LoadStringData(getActivity(), "Key");
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    public String email;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Client_Register_Btn_Register)
    public void onViewClicked() {
        email = ForgetPasswordEtEmail.getText().toString();
        if (!validateEmail(email)) {
            ForgetPasswordEtEmail.setError("Not a valid email address!");
        }
        else {
            setResetPassword(email);
            TextView toolbarTitle = null;
            try {
                ClientHomeActivity clientHomeActivity = (ClientHomeActivity) getActivity();
                toolbarTitle = clientHomeActivity.toolbarTitle;
            } catch (Exception e) {

                RestaurantHomeActivity restaurantHomeActivity = (RestaurantHomeActivity) getActivity();
                toolbarTitle = restaurantHomeActivity.restaurantToolbarTitle;
            }
            if (Key.equalsIgnoreCase("Client")) {
                HelperMethod.replace(new NewPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "نسيت كلمه المرور");
            }
            if (Key.equalsIgnoreCase("Restaurant")) {
                HelperMethod.replace(new NewPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, toolbarTitle, "نسيت كلمه المرور");
            }
        }
    }

    private void setResetPassword (String email) {
        ApiServices apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.sendResetPasswordRequest(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus()== 1) {
                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });
    }

    public boolean validateEmail (String email){
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
