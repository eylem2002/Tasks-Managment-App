package com.example.rosie.activities.introApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosie.R;
import com.example.rosie.ShortPref.SharedPrefManager;
import com.example.rosie.activities.TaskPage;
import com.example.rosie.apis.RetrofitSignIn;
import com.example.rosie.model.Result;
import com.example.rosie.model.User;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity   implements View.OnClickListener{





        private TextInputLayout EmailTextInputLayout, PasswordTextInputLayout;
        private Button buttonSignIn;
        private TextView SignIn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);


            EmailTextInputLayout = (TextInputLayout) findViewById(R.id.TextInputEmailLayout);
            PasswordTextInputLayout = (TextInputLayout) findViewById(R.id.TextInputPassword);

            buttonSignIn=(Button)findViewById(R.id.btnNext);
            SignIn = (TextView) findViewById(R.id.create);


            buttonSignIn.setOnClickListener(this);
            SignIn.setOnClickListener(this);
        }

        private void userSignIn() {

            String email = EmailTextInputLayout.getEditText().getText().toString().trim();
            String password = PasswordTextInputLayout.getEditText().getText().toString().trim();

            Call<Result> call = RetrofitSignIn.getInstance().getMyApi().userLogin(email,password);
            call.enqueue(new Callback<Result>() {

                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Result result = response.body();
                        if (!result.getError()) {
                            User user = result.getUser();



                            finish(); // Finish the current activity to prevent back navigation
                            Toast.makeText(getApplicationContext(), "Welcome " + user.getName(), Toast.LENGTH_LONG).show();
                           Intent intent= new Intent(SignUp.this, TaskPage.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to get response from server", Toast.LENGTH_LONG).show();
                    }
                }




                @Override
                public void onFailure(Call<Result> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Retrofit ERROR -->",t.getMessage());

                }
            });
        }

        @Override
        public void onClick(View view) {

            if (view == buttonSignIn) {
                userSignIn();
            }else if(view == SignIn){

                startActivity(new Intent(getApplicationContext(),SignIn.class));
            }
        }
    }