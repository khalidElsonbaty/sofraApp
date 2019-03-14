package com.example.sofra.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.clientCycle.HomeClientFragment;
import com.example.sofra.ui.fragment.clientCycle.RestaurantDetailsFragment;
import com.example.sofra.ui.fragment.loginCycle.LoginFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.AboutFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.ContactAsFragment;
import com.example.sofra.ui.fragment.navigationViewCycle.OfferFragment;
import com.example.sofra.ui.fragment.restaurantCycle.RestaurantAddItemsFragment;

public class RestaurantHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public TextView restaurantToolbarTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.Restaurant_Home_DrawerLayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.Restaurant_Home_ToolBar);
        restaurantToolbarTitle = (TextView) toolbar.findViewById(R.id.Restaurant_Home_Toolbar_Tv);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toolbar_open, R.string.toolbar_close);
        actionBarDrawerToggle.syncState();

        HelperMethod.replace(new LoginFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "تسجيل دخول");

        NavigationView navigationView = (NavigationView) findViewById(R.id.Restaurant_Home_Navigation_Drawer);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.nav_restaurant_home:
                HelperMethod.replace(new RestaurantDetailsFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "المطعم");
                break;
            case R.id.nav_my_product:
                HelperMethod.replace(new RestaurantAddItemsFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "منتجاتي");
                break;
            case R.id.nav_my_offers:
                HelperMethod.replace(new OfferFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "عروضي");
                break;
            case R.id.nav_rest_about:
                HelperMethod.replace(new AboutFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "حول البرنامج");
                break;
            case R.id.nav_rest_dail_up:
                HelperMethod.replace(new ContactAsFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "تواصل معنا");
                break;
            case R.id.nav_restaurant_sign_up:
                HelperMethod.replace(new LoginFragment(), getSupportFragmentManager(), R.id.Restaurant_Home_Fragment_Container, restaurantToolbarTitle, "تسجيل الدخول");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
