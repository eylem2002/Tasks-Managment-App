package com.example.rosie.apis;

public interface APIUrl {
    String BASE_URL="https://10.0.2.2/rosie/API.php/";//chnage https to http
    String SIGN_IN = "http://10.0.2.2/rosie/checkUsers.php/";



    String SIGN_UP = "http://10.0.2.2/rosie/insertUsers.php/";
    String UPDATE_INFO = "http://10.0.2.2/rosie/updateUsers.php/";
    String DELETE_USER = "http://10.0.2.2/rosie/deleteUsers.php/";

    String DELETE_TASK = "http://10.0.2.2/rosie/deleteTask.php/";

    String ADD_TASKS = "http://10.0.2.2/rosie/insertTasks.php/";

}
