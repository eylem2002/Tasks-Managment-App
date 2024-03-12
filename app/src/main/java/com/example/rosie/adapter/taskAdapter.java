package com.example.rosie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rosie.R;
import com.example.rosie.activities.Task;


import java.util.List;


public class taskAdapter extends ArrayAdapter<Task> {

    private List<Task> taskList;
    private boolean flag = false ;

    //the context object
    private Context mCtx;

    public taskAdapter(Context context, List<Task> products){

        super(context, R.layout.list_item,products);
        this.taskList = products;
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


        TextView taskname = (TextView) convertView.findViewById(R.id.taskname);
        TextView desc = (TextView) convertView.findViewById(R.id.taskdes);

        TextView timetocreate = (TextView) convertView.findViewById(R.id.timeofcreate);

        TextView timetodone = (TextView) convertView.findViewById(R.id.timetodone);

    //    TextView status = (TextView) convertView.findViewById(R.id.taskStatusTv);


        taskname.setText(taskList.get(position).getTaskn());
        desc.setText(taskList.get(position).getDesc());
        timetocreate.setText(taskList.get(position).getTimeC());
        timetodone.setText(taskList.get(position).getTimeT());
//        status.setText(taskList.get(position).gets());




        return convertView;
    }
}
