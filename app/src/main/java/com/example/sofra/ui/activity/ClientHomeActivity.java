package com.example.sofra.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.clientCycle.ClientProfileFragment;
import com.example.sofra.ui.fragment.clientCycle.HomeClientFragment;
import com.example.sofra.ui.fragment.loginCycle.LoginFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.AboutFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.ContactAsFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.MyOrderFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.OfferFragment;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

public class ClientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public TextView toolbarTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String api_Token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.Client_Home_DrawerLayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.Client_Home_ToolBar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.Client_Home_Toolbar_Tv);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toolbar_open, R.string.toolbar_close);
        actionBarDrawerToggle.syncState();

        HelperMethod.replace(new HomeClientFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "المطاعم");

        final NavigationView navigationView = (NavigationView) findViewById(R.id.Client_Home_Navigation_Drawer);
        View header = navigationView.getHeaderView(0);
        ImageView loginButton = header.findViewById(R.id.Client_Home_Header_Iv_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.replace(new ClientProfileFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "بياناتي");
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                HelperMethod.replace(new HomeClientFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "المطاعم");
                break;
            case R.id.nav_orders:
                Toast.makeText(getApplicationContext(), "Orders", Toast.LENGTH_LONG).show();
                HelperMethod.replace(new MyOrderFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "طلباتي");
                break;
            case R.id.nav_new_offers:
                HelperMethod.replace(new OfferFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "جديد العروض");
                break;
            case R.id.nav_rule:
                break;
            case R.id.nav_about:
                HelperMethod.replace(new AboutFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "حول البرنامج");
                break;
            case R.id.nav_dail_up:
                HelperMethod.replace(new ContactAsFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "تواصل معنا");
                break;
            case R.id.nav_sign_up:
                HelperMethod.replace(new LoginFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "تسجيل الدخول");
                break;
        }
        if (api_Token == null) {
            new FancyAlertDialog.Builder(this)
                    .setTitle("Cannot use this feather without login")
                    .setBackgroundColor(Color.parseColor("#FFD13D"))  //Don't pass R.color.colorvalue
                    .setMessage("Do you want to Login ?")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FFD13D"))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Login")
                    .setNegativeBtnBackground(Color.parseColor("#A9A7A8"))  //Don't pass R.color.colorvalue
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(R.drawable.ic_error_outline, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            HelperMethod.replace(new LoginFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "تسجيل الدخول");
                        }
                    })
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            HelperMethod.replace(new HomeClientFragment(), getSupportFragmentManager(), R.id.Client_Home_Fragment_Container, toolbarTitle, "المطاعم");
                        }
                    })
                    .build();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
