package com.example.rosie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosie.R;
import com.example.rosie.activities.Task;


import java.util.List;


public class productAdapter extends ArrayAdapter<Task> {

    private List<Task> productList;
    private boolean flag = false ;

    //the context object
    private Context mCtx;

    public productAdapter(Context context, List<Task> products){

        super(context, R.layout.list_item,products);
        this.productList = products;
        this.mCtx = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get products data at certain position using Product class
        Task task = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }


      //  ImageView img = (ImageView) convertView.findViewById(R.id.imageView2);
        TextView taskname = (TextView) convertView.findViewById(R.id.taskname);
        TextView desc = (TextView) convertView.findViewById(R.id.taskdes);

        taskname.setText(productList.get(position).getTaskn());
        desc.setText(productList.get(position).getDesc());

      //  Glide.with(getContext()).load("http://10.0.2.2/store/Uploads/"+productList.get(position).getImg()).apply(new RequestOptions().override(600,600))
               // .error(R.drawable.notfound).into(img);


        return convertView;
    }
}
