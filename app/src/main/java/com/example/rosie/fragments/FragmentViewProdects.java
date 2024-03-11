package com.example.rosie.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.rosie.R;
import com.example.rosie.activities.Task;
import com.example.rosie.adapter.productAdapter;
import com.example.rosie.apis.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewProdects extends Fragment {
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {//لربط ملف الxml with the activity
    return inflater.inflate(R.layout.fragment_view_prodects,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView=(ListView) view.findViewById(R.id.listView);

        Call<List<Task>> call= RetrofitClient.getInstance().getMyApi().getProduct();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task>productList=response.body();
                productAdapter adapter=new productAdapter(getContext(),productList);
                listView.setAdapter(adapter);

                Toast.makeText(getContext(),"donnnnnne",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                Log.d(TAG, "onFailure: on retrofir  ");
            }
        });
    }
    //onview created لتنفيذ الاوامر بدخل الاكتفتي

}