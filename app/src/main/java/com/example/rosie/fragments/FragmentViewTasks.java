package com.example.rosie.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rosie.R;
import com.example.rosie.activities.Task;
import com.example.rosie.adapter.taskAdapter;
import com.example.rosie.apis.RetrofitClient;
import com.example.rosie.apis.RetrofitTasks;
import com.example.rosie.model.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewTasks extends Fragment {
    ListView listView;
    FloatingActionButton fab;
    TextView userName;
    private AlertDialog addTaskDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listView);
        fab = view.findViewById(R.id.addTaskkFAB);

        userName = view.findViewById(R.id.userNameTv);
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

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                String formattedDate = dateFormat.format(currentDate);
                Log.d("Today's Date", formattedDate);
                String timecreate = formattedDate;

                insertTask(title, description, dueDate, timecreate);
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

    private void insertTask(String Title, String Desc, String DueDate, String Timecreate) {
        Call<Result> call = RetrofitTasks.getInstance().getMyApi().insertTasks(Title, Desc, DueDate, Timecreate);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result != null && !result.getError()) {
                        Log.d("Response ---> ", "Task inserted successfully");
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                        //loadData(); // Reload data after successful insertion
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

    private void loadData() {
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
