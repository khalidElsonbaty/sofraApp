package com.example.sofra.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sofra.R;
import com.example.sofra.helper.SharedPreferencesManger;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AskLoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.Ask_Login_Client_Btn, R.id.Ask_Login_Restaurant_Btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Ask_Login_Client_Btn:
                SharedPreferencesManger.SaveData(this,"Key","Client");
                startActivity(new Intent(AskLoginActivity.this, ClientHomeActivity.class));
                break;
            case R.id.Ask_Login_Restaurant_Btn:
                SharedPreferencesManger.SaveData(this,"Key","Restaurant");
                startActivity(new Intent(AskLoginActivity.this, RestaurantHomeActivity.class));

                break;
        }
    }
}
