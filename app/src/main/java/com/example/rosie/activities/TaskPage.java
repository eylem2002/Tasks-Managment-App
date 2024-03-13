package com.example.rosie.activities;

import static com.example.rosie.adapter.taskAdapter.taskList;
import static com.example.rosie.fragments.FragmentViewTasks.listView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.rosie.adapter.taskAdapter;
import com.example.rosie.fragments.FragmentAboutApp;
import com.example.rosie.fragments.FragmentAboutUs;
import com.example.rosie.fragments.FragmentViewTasks;
import com.example.rosie.fragments.ProfileFragment;
import com.example.rosie.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class TaskPage extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    taskAdapter taskAdapter;

    FragmentTransaction mFragmentTransaction;
    public static TextView navUsername, navEmail, navPhone;

    public static String sample_name;
    TextView number_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);

        taskAdapter = new taskAdapter(getApplicationContext(), new ArrayList<>()); // Initialize the field


        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedin())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),SignUp.class));

        }

        drawerLayout = findViewById(R.id.navLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);






        toolbar.setBackgroundColor(Color.parseColor("#fffcf1"));
       toolbar.setTitleTextColor(Color.parseColor("#fffcf1"));

        toolbar.setElevation(0);


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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//
//        MenuItem item = menu.findItem(R.id.action_icon);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                Toast.makeText(getApplicationContext(), "Woohoo! Rosie is here for you!", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem item = menu.findItem(R.id.action_icon);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (taskAdapter.taskList != null) {
                    Collections.sort(taskAdapter.taskList, new Comparator<Task>() {
                        @Override
                        public int compare(Task task1, Task task2) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                            try {
                                Date date1 = dateFormat.parse(task1.getTimeC());
                                Date date2 = dateFormat.parse(task2.getTimeC());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }
                    });

                    // Notify the adapter that the data has changed
                    taskAdapter.notifyDataSetChanged();

                    // Show a toast message indicating the sorting is complete
                    Toast.makeText(TaskPage.this, "Tasks sorted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TaskPage", "taskList is null");
                }

                // Update the UI to reflect the sorted list
                listView.invalidateViews(); // Assuming listView is your ListView instance

                return true;
            }

        });

        return true;
    }

}
