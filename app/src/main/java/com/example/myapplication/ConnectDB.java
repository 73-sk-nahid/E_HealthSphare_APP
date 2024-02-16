package com.example.myapplication;

import com.adedom.library.Dru;

import java.sql.Connection;

public class ConnectDB {
    public static String BASE_URL = "http://10.0.2.2:3306";

    public static Connection getConnection() {
        return Dru.connection(BASE_URL, "root", "", "hospital");
        /*return Dru.connection(BASE_URL, "nahidsheikh", "NahidSheikh", "hospital");*/
    }
}
