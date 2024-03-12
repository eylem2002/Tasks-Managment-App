package com.example.rosie.ShortPref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.rosie.activities.introApp.SignUp;
import com.example.rosie.model.User;


public class SharedPrefManager {

    private static  final String SHARED_PREF_NAME="EstoreSharedPref";
    private static final String KEY_ID = "keyId";
    private static final String KEY_USERNAME = "keyUsername";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_PASSWORD = "keyPassword";
    private static final String KEY_PHONE = "keyPhone" ;
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private SharedPrefManager (Context context){
        mCtx=context;

    }
    public  static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);

        }
        return  mInstance;
    }

    public void userLogin(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences (SHARED_PREF_NAME, Context. MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_ID,user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.apply();

    }
    public void userUpdate(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE,user.getPhone());
        editor.apply();
    }
    public User getUsers(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences (SHARED_PREF_NAME, Context. MODE_PRIVATE) ;
        return new User(
                sharedPreferences.getInt(KEY_ID,0),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_PHONE,null)

        );
    }
    public boolean isLoggedin(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences (SHARED_PREF_NAME, Context. MODE_PRIVATE) ;
        return sharedPreferences.getString(KEY_USERNAME,null)!=null;

    }
    public void logout(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences (SHARED_PREF_NAME, Context. MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent i= new Intent(mCtx, SignUp.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);
    }
}
