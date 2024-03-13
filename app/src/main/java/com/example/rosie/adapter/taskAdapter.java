package com.example.rosie.adapter;

//import static com.example.rosie.fragments.FragmentViewTasks.num_per;

import static com.example.rosie.fragments.FragmentViewTasks.num_per;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.rosie.R;
import com.example.rosie.activities.Task;


import java.util.List;


public class taskAdapter extends ArrayAdapter<Task> {

    private List<Task> taskList;
    private boolean flag = false ;

    //the context object
    private Context mCtx;
  static   int counter=0;
    SearchView searchView;
    public static double percent_num=0;

    public taskAdapter(Context context, List<Task> products){

        super(context, R.layout.list_item,products);
        this.taskList = products;
        this.mCtx = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        int totalTasks = taskList.size();


        Task task = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }


        TextView taskname = (TextView) convertView.findViewById(R.id.taskname);
        TextView desc = (TextView) convertView.findViewById(R.id.taskdes);

        TextView timetocreate = (TextView) convertView.findViewById(R.id.timeofcreate);

        TextView timetodone = (TextView) convertView.findViewById(R.id.timetodone);


       TextView statuss = (TextView) convertView.findViewById(R.id.taskStatusTv);


        ImageView imageView = convertView.findViewById(R.id.rightofNot);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = taskList.get(position);
                task.toggleImageViewClicked();

                // Update the ImageView color based on the flag
                if (task.isImageViewClicked()) {
                    imageView.setColorFilter(Color.GREEN);
                    statuss.setText("Done");
                    counter++;


                } else {
                    imageView.setColorFilter(null);
                    statuss.setText("Work");
                    counter--;
                }

                if (totalTasks != 0)
                    percent_num = (double) counter / totalTasks * 100;
                    num_per.setText(String.format("%.2f", percent_num));

                // Notify the adapter of the data changes
                notifyDataSetChanged();
            }
        });

        taskname.setText(taskList.get(position).getTaskn());
        desc.setText(taskList.get(position).getDesc());
        timetocreate.setText(taskList.get(position).getTimeC());
        timetodone.setText(taskList.get(position).getTimeT());


     return convertView;
    }
}
