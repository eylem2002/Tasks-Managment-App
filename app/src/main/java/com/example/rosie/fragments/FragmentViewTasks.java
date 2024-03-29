package com.example.rosie.fragments;

import static com.example.rosie.activities.TaskPage.sample_name;
import static com.example.rosie.adapter.taskAdapter.moon;
import static com.example.rosie.adapter.taskAdapter.percent_num;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.rosie.R;
import com.example.rosie.activities.Task;
import com.example.rosie.adapter.taskAdapter;
import com.example.rosie.apis.RetrofitClient;
import com.example.rosie.apis.RetrofitTasks;
import com.example.rosie.model.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewTasks extends Fragment {
   public static ListView listView;
    FloatingActionButton fab;
    TextView userName;
    private AlertDialog addTaskDialog;
    CardView cardView;

    TextView TextViewStatus;
    public static TextView num_per;
    public static TextView support;

    public static  TextView date_name,today_name;



    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listView);
        fab = view.findViewById(R.id.addTaskkFAB);
        cardView=view.findViewById(R.id.cardView1);


        TextViewStatus=(TextView) view.findViewById(R.id.taskStatusTv);
        num_per=view.findViewById(R.id.completionPercentageTextView);
        support=view.findViewById(R.id.Support);


         today_name= (TextView) view.findViewById(R.id.today);
         date_name= (TextView) view.findViewById(R.id.datee);

        num_per.setVisibility(View.VISIBLE);
        userName = view.findViewById(R.id.userNameTv);

        userName.setText(sample_name.toUpperCase());




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });



        loadData();
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText editTextTitle = dialogView.findViewById(R.id.editTitle);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);

        EditText editTextDueDate = dialogView.findViewById(R.id.editDueDate);


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                String dueDate = editTextDueDate.getText().toString();
                //String status=TextViewStatus.getText().toString();
               String status=moon;

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                String formattedDate = dateFormat.format(currentDate);
                Log.d("Today's Date", formattedDate);
                String timecreate = formattedDate;

                insertTask(title, description, dueDate, timecreate,status);
                dialog.dismiss(); // Dismiss the dialog after processing
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void insertTask(String Title, String Desc, String DueDate, String Timecreate,String Status) {
        Call<Result> call = RetrofitTasks.getInstance().getMyApi().insertTasks(Title, Desc, DueDate, Timecreate,Status);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result != null && !result.getError()) {
                        Log.d("Response ---> ", "Task inserted successfully");
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                        loadData();
                    } else {
                        Log.v("Something went wrong", result.getMessage());
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Response error", "Unsuccessful response received. Code: " + response.code());
                    Toast.makeText(getContext(), "Unsuccessful response received", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("Failed to Insert Data", t.getMessage());
                Toast.makeText(getContext(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadData() {
        Call<List<Task>> call = RetrofitClient.getInstance().getMyApi().getProduct();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    List<Task> productList = response.body();
                    taskAdapter adapter = new taskAdapter(getContext(), productList);
                    listView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Data loaded successfully", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Response error", "Unsuccessful response received. Code: " + response.code());
                    Toast.makeText(getContext(), "Unsuccessful response received", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("FragmentViewTasks", "Failed to load data: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
            }
        });
    }
}
