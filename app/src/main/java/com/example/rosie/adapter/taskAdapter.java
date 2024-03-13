package com.example.rosie.adapter;

//import static com.example.rosie.fragments.FragmentViewTasks.num_per;

import static android.app.PendingIntent.getActivity;
import static com.example.rosie.fragments.FragmentViewTasks.date_name;
import static com.example.rosie.fragments.FragmentViewTasks.num_per;
import static com.example.rosie.fragments.FragmentViewTasks.support;
import static com.example.rosie.fragments.FragmentViewTasks.today_name;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.rosie.R;
import com.example.rosie.ShortPref.SharedPrefManager;
import com.example.rosie.activities.Task;
import com.example.rosie.apis.RetrofitDelete;
import com.example.rosie.apis.RetrofitTaskDelete;
import com.example.rosie.model.Result;
import com.example.rosie.model.Taskmodel;
import com.example.rosie.model.User;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class taskAdapter extends ArrayAdapter<Task> {
//public static  List<Task> taskList2;
    public static List<Task> taskList;
    private boolean flag = false ;

    //the context object
    private Context mCtx;
  static   int counter=0;
  CardView cardView;
    static public String moon="Work";
    SearchView searchView;
    int Id;
    int taskId;
    public static double percent_num=0;

    public taskAdapter(Context context, List<Task> products){

        super(context, R.layout.list_item,products);
        this.taskList = products;
        this.mCtx = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


//        taskList2.addAll(taskList);
//        Log.i("TAG", taskList2.toString());
//        Toast.makeText(getContext(), "the id" + taskList2.toString(), Toast.LENGTH_LONG).show();




        taskId =   taskList.get(position).getId();
     //   Toast.makeText(getContext(), "the id" + taskList.get(position).getId(), Toast.LENGTH_LONG).show();


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



        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dayOfWeek = dayFormat.format(currentDate);
        String formattedDate = dateFormat.format(currentDate);

        today_name.setText(dayOfWeek);
        date_name.setText(formattedDate);
   //     Toast.makeText(getContext(),"the date  ---> "+formattedDate,Toast.LENGTH_LONG).show();


        ImageView imageView = convertView.findViewById(R.id.rightofNot);

        cardView= convertView.findViewById(R.id.cardView1);

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteTask();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = taskList.get(position);
                task.toggleImageViewClicked();


                if (task.isImageViewClicked()) {
                    imageView.setColorFilter(Color.GREEN);
                    statuss.setText("Done");
                    moon="Done";
                    counter++;


                } else {
                    imageView.setColorFilter(null);
                    statuss.setText("Work");
                    moon="Work";
                    counter--;
                }

                if (totalTasks != 0)
                {
                    percent_num = (double) counter / totalTasks * 100;
                }

                num_per.setText(String.format("%.1f%%", percent_num));


                if(percent_num>50) support.setText("Well done! ");
                    else support.setText("Try to complete more tasks.");


                notifyDataSetChanged();//this is to Notify the adapter of the data changes
            }
        });

        taskname.setText(taskList.get(position).getTaskn());
        desc.setText(taskList.get(position).getDesc());
        timetocreate.setText(taskList.get(position).getTimeC());
        timetodone.setText(taskList.get(position).getTimeT());


     return convertView;
    }
    public void DeleteTask(){

        Call<Result> call = RetrofitTaskDelete.getInstance().getMyApi().deleteTask(taskId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body().getError() == false){

                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();

                    // Find the index of the task with the matching ID
                    int index = -1;
                    for (int i = 0; i < taskList.size(); i++) {
                        if (taskList.get(i).getId() == taskId) {
                            index = i;
                            break;
                        }
                    }

                    // Remove the task from the list if found
                    if (index != -1) {
                        taskList.remove(index);
                        notifyDataSetChanged();
                    }

                    Log.d("msg ---> ",response.body().getMessage());

                } else if (response.body().getError() == true){
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
