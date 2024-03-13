package com.example.rosie.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.rosie.R;
import com.example.rosie.ShortPref.SharedPrefManager;
import com.example.rosie.activities.introApp.SignUp;
import com.example.rosie.fragments.FragmentAboutApp;
import com.example.rosie.fragments.FragmentAboutUs;
import com.example.rosie.fragments.FragmentViewTasks;
import com.example.rosie.fragments.ProfileFragment;
import com.example.rosie.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class TaskPage extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FragmentTransaction mFragmentTransaction;
    public static TextView navUsername, navEmail, navPhone;

    public static String sample_name;
    TextView number_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);



        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedin())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),SignUp.class));

        }

        drawerLayout = findViewById(R.id.navLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);








        toolbar.setBackgroundColor(Color.parseColor("#fffcf1"));
        toolbar.setTitleTextColor(Color.BLACK);




        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.user_name_nav);
        navEmail = headerView.findViewById(R.id.Email_nav);
        navPhone = headerView.findViewById(R.id.Phone_nav);

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container, new FragmentViewTasks());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
        setHeaderInfo();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int num = item.getItemId();

                if (num == R.id.nav_log_out) {
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }
                if (num == R.id.nav_home) {
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container, new FragmentViewTasks());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                } else if (num == R.id.nav_profile) {
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container, new ProfileFragment());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                } else if (num == R.id.nav_settings) {
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container, new SettingsFragment());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
                else if (num == R.id.nav_about_us) {
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container, new FragmentAboutUs());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
                else if (num == R.id.nav_AppInFo) {
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container, new FragmentAboutApp());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
                return true;
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    public void setHeaderInfo() {
        String name = SharedPrefManager.getInstance(TaskPage.this).getUsers().getName();
        String email = SharedPrefManager.getInstance(TaskPage.this).getUsers().getEmail();
        String phone = SharedPrefManager.getInstance(TaskPage.this).getUsers().getPhone();
        Log.d("TAG", ">>>>>>>>>>>>>>>>>>>>" + name);
        navUsername.setText(name);
        navEmail.setText(email);
        navPhone.setText(phone);
        sample_name=name;
    }
}
