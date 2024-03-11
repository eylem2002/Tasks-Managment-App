package com.example.rosie.fragments;

import android.graphics.Color;
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
import com.example.rosie.apis.RetrofitDelete;
import com.example.rosie.model.Result;
import com.example.rosie.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    Button deleteBtn;
    int Id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //Get the Id of current user to delete the account
        User user = SharedPrefManager.getInstance(getContext()).getUsers();

        Id = user.getId();

        deleteBtn = getView().findViewById(R.id.deleteBtn);
        deleteBtn.setBackgroundColor(Color.RED);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteUser();
            }
        });


    }

    public void DeleteUser(){

        Call<Result> call = RetrofitDelete.getInstance().getMyApi().deleteUser(Id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body().getError() == false){

                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Log.d("msg ---> ",response.body().getMessage());
                    SharedPrefManager.getInstance(getContext()).logout();
                }else if (response.body().getError() == true){
                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("msg ---> ",response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getContext(),"Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error ---> ",t.getMessage());

            }
        });
    }
}
