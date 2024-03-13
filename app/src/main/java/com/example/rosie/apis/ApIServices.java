package com.example.rosie.apis;

import com.example.rosie.activities.Task;
import com.example.rosie.model.Result;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApIServices {
    @GET("API.php")
    Call<List<Task>> getProduct();
    /* the SignIn call */
    @FormUrlEncoded
    @POST("checkUsers.php")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    /*The SignUp Call */
    @FormUrlEncoded
    @POST("insertUsers.php")
    Call<Result>  insertUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone

    );


    /*The SignUp Call */
    @FormUrlEncoded
    @POST("insertTasks.php")
    Call<Result>  insertTasks(
            @Field("taskName") String taskName,
            @Field("description") String description,
            @Field("timeToDo") String timeToDo,
            @Field("timeCreate") String timeCreate,
            @Field("statuss") String statuss

    );

    @FormUrlEncoded
    @POST("updateUsers.php")
    Call<Result> updateUser(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );
    @FormUrlEncoded
    @POST("deleteUsers.php")
    Call<Result> deleteUser(
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("deleteTask.php")
    Call<Result> deleteTask(
            @Field("Id") int Id

    );

}
