package com.example.rosie.activities.introApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rosie.R;
import com.example.rosie.apis.RetrofitSignUp;
import com.example.rosie.model.Result;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    public TextInputLayout name,password,email,phone;
    public String Name,Pass,Email,Phone;
    Button b1,b2;


    public Button RegBtn;//b2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name=(TextInputLayout)findViewById(R.id.username);
        password=(TextInputLayout)findViewById(R.id.pass);
        phone=(TextInputLayout)findViewById(R.id.phone);
        email=(TextInputLayout)findViewById(R.id.email);

        b1=(Button) findViewById(R.id.signin);
        b2=(Button) findViewById(R.id.start);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usernameVlaidate() && passwordValidate() && phoneValidate() && emailValidate())
                {
                    insertUser();
                    Toast.makeText(getApplicationContext(),"welcome User :)",Toast.LENGTH_LONG).show();


                }

                else return;
            }
        });
    }//End onCreate method


    private void insertUser(){

        //retrieve data from Edit texts
        Name = name.getEditText().getText().toString().trim();
        Pass = password.getEditText().getText().toString().trim();
        Email = email.getEditText().getText().toString().trim();
        Phone = phone.getEditText().getText().toString().trim();

        //Here we will handle the http request to insert user to mysql db


        Call<Result> call = RetrofitSignUp.getInstance().getMyApi().insertUser(Name,Pass,Email,Phone);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Result result = response.body();
                        if (result.getError() != null && result.getError()) {
                            Log.v("Something went wrong", result.getMessage());
                            Toast.makeText(SignIn.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Response ---> ", "User registered successfully");
                            Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), SignUp.class));
                        }
                    } else {
                        // Handle null body
                        Log.d("Response error", "Null body received");
                        Toast.makeText(getApplicationContext(), "Null body received", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Log.d("Response error", "Unsuccessful response received. Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "Unsuccessful response received", Toast.LENGTH_LONG).show();
                }
            }






            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.d("Failed to Insert Data",t.getMessage());
                Toast.makeText(getApplicationContext(),"Failed to Insert Data --> "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }//End insertUser method











    private boolean usernameVlaidate(){
        Name = name.getEditText().getText().toString().trim();//trim ---> remove the whitespaces
        if(Name.isEmpty()){
            name.setError("This field should not be Empty");
            return false;

        }
        else {

            name.setErrorEnabled(false);//مشات اخفي رسالة الغلط لما المستخدمك يرجع مرة ثانية يدخل صح
            return true;

        }}
    private boolean passwordValidate(){
        Pass = password.getEditText().getText().toString().trim();//trim ---> remove the whitespaces
        if(Pass.isEmpty()){
            password.setError("This field should not be Empty");
            return false;

        } else if(Pass.length()>8){

            password.setError("This field should not be greater than 8 charcter");
            return false;

        }
        else {

            password.setErrorEnabled(false);//مشات اخفي رسالة الغلط لما المستخدمك يرجع مرة ثانية يدخل صح
            return true;

        }}//end of password

    private boolean phoneValidate(){
        Phone = phone.getEditText().getText().toString().trim();//trim ---> remove the whitespaces
        if(Phone.isEmpty()){
            phone.setError("This field should not be Empty");
            return false;

        }
        else if(Phone.charAt(0)!='7' )
        {
            phone.setError("The first number should be 7 ");
            return false;
        }
        else if(!(Phone.charAt(1)=='7' )&& !(Phone.charAt(1)=='8') && !(Phone.charAt(1)=='9'))
        {
            phone.setError("The  second number should be 7 || 8 || 9");
            return false;
        }

        else {

            phone.setErrorEnabled(false);//مشات اخفي رسالة الغلط لما المستخدمك يرجع مرة ثانية يدخل صح
            return true;

        }}
    private boolean emailValidate(){
        Email = email.getEditText().getText().toString().trim();//trim ---> remove the whitespaces
        if(Email .isEmpty()){
            email.setError("This field should not be Empty");
            return false;

        }
        else if(!isValidEmail(Email))
        {

            email.setError("This field should not be Empty");
            return false;   }

        else {

            email.setErrorEnabled(false);//مشات اخفي رسالة الغلط لما المستخدمك يرجع مرة ثانية يدخل صح
            return true;

        }}
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}