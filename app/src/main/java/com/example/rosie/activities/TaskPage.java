package com.example.rosie.activities;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;


import com.example.rosie.R;
import com.example.rosie.ShortPref.SharedPrefManager;
import com.example.rosie.activities.introApp.SignUp;
import com.example.rosie.fragments.FragmentViewTasks;
import com.example.rosie.fragments.ProfileFragment;
import com.example.rosie.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;


public class TaskPage extends AppCompatActivity {


    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FragmentTransaction mFragmentTransaction;
    public  static TextView navUsername,navEmail,navPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);

//        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedin())
//        {
//            finish();
//            startActivity(new Intent(getApplicationContext(), SignUp.class));
//        }


        drawerLayout =(DrawerLayout) findViewById(R.id.navLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //define the header of navigation view
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.user_name_nav);
        navEmail = (TextView) headerView.findViewById(R.id.Email_nav);
        navPhone = (TextView) headerView.findViewById(R.id.Phone_nav);




        setHeaderInfo();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              int num= item.getItemId();
              if(num==R.id.nav_log_out){
                  finish();
                  SharedPrefManager.getInstance(getApplicationContext()).logout();

              }
               if (num==R.id.nav_home){

                    mFragmentTransaction=getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.container,new FragmentViewTasks());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
              else if (num==R.id.nav_profile){

                  mFragmentTransaction=getSupportFragmentManager().beginTransaction();
                  mFragmentTransaction.replace(R.id.container,new ProfileFragment());
                  mFragmentTransaction.addToBackStack(null);
                  mFragmentTransaction.commit();
              }
              else if (num==R.id.nav_settings){
                  mFragmentTransaction=getSupportFragmentManager().beginTransaction();
                  mFragmentTransaction.replace(R.id.container,new SettingsFragment());
                  mFragmentTransaction.addToBackStack(null);
                  mFragmentTransaction.commit();
              }
                return true;
            }
        });

//        //recive the info
//        Intent intent=getIntent();
//String name=intent.getStringExtra("name");
//String phone=intent.getStringExtra("phone");
//        String email=intent.getStringExtra("email");
//        navUsername.setText(name);
//        navPhone.setText(phone);
//        navEmail.setText(email);



        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    public void setHeaderInfo() {
        String name= SharedPrefManager.getInstance(TaskPage.this).getUsers().getName();
        String email=SharedPrefManager.getInstance(TaskPage.this).getUsers().getEmail();
        String phone=SharedPrefManager.getInstance(TaskPage.this).getUsers().getPhone();
        navUsername.setText(name);
        navEmail.setText(email);
        navPhone.setText(phone);

    }
}