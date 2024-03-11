package com.example.rosie.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.rosie.R;
import com.example.rosie.ShortPref.SharedPrefManager;

import com.example.rosie.activities.navPage;
import com.example.rosie.apis.RetrofitUpdate;
import com.example.rosie.model.Result;
import com.example.rosie.model.User;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextInputLayout name,password,email,phone;
    Button updateBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profilefragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        name = (TextInputLayout) getView().findViewById(R.id.TextInputUsername);
        email = getView().findViewById(R.id.TextInputEmail);
        password = getView().findViewById(R.id.TextInputPassword);
        phone = getView().findViewById(R.id.TextInputPhone);


        //get the Info of current user
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        name.getEditText().setText(user.getName());
        password.getEditText().setText(user.getPassword());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhone());

        updateBtn = getView().findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Call<Result> call = RetrofitUpdate.getInstance().getMyApi().updateUser(user.getId(),name.getEditText().getText().toString(),email.getEditText().getText().toString(),password.getEditText().getText().toString(),phone.getEditText().getText().toString());
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {

                        if (response.body().getError() == true || response.body().getUser().getName() == null) {
                            Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        } else {


                            User user = new User(response.body().getUser().getId(), response.body().getUser().getName(), response.body().getUser().getEmail(), response.body().getUser().getPassword(), response.body().getUser().getPhone());
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getContext()).userUpdate(user);
                            //reload the info of navigation drawer header
                            navPage object = new navPage();
                            object.setHeaderInfo();

                            Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getContext(), "something goes wrong!! ===> "+t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Error 102 --> ",t.getMessage());

                    }
                });



            }
        });

    }
}


