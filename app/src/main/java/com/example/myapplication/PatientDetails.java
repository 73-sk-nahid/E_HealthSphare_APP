package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.adapters.PatientListAdapter;
import com.example.myapplication.model.PatientModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientDetails extends AppCompatActivity {
    String dr;
    ListView patientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        patientList = findViewById(R.id.patientList);

        Intent intent = getIntent();
        dr = intent.getStringExtra("dr");

    }

    @Override
    protected void onStart() {
        getPatientData();
        super.onStart();
    }

    private void getPatientData() {
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        //String url = "http://10.0.2.2/E-healthSphare/patients_details.php?t1=" + dr+"";
        String url = "http://192.168.1.106/E-healthSphare/patients_details.php?t1=" + dr+"";
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
                    JSONArray patientsArray = new JSONArray(response);

                    // Create a list to store patient models
                    List<PatientModel> patients = new ArrayList<>();

                    // Iterate through the array of patients
                    for (int i = 0; i < patientsArray.length(); i++) {
                        JSONObject patientObject = patientsArray.getJSONObject(i);

                        // Extract patient details for each patient
                        String number = patientObject.getString("number");
                        String name = patientObject.getString("name");
                        String location = patientObject.getString("location");
                        String age = patientObject.getString("age");
                        String gender = patientObject.getString("gender");
                        String phone = patientObject.getString("phone");
                        String dateOfBirth = patientObject.getString("dateOfBirth");
                        String diagnosis = patientObject.getString("diagnosis");
                        String prescription = patientObject.getString("prescription");
                        String pcondition = patientObject.getString("pcondition");

                        // Create a new PatientModel and add it to the list
                        PatientModel patientModel = new PatientModel(name, location, age, gender, phone, dateOfBirth, diagnosis, prescription, number, pcondition);
                        patients.add(patientModel);
                    }

                    // Create the custom adapter and set it to your ListView
                    PatientListAdapter adapter = new PatientListAdapter(getApplicationContext(), patients);
                    patientList.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on the screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }


    /*private void getPatientData() {
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        //String url = "http://10.0.2.2/E-healthSphare/patients_details.php?t1=" + dr+"";
        String url = "http://192.168.1.106/E-healthSphare/patients_details.php?t1=" + dr+"";
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
                    JSONArray patientsArray = new JSONArray(response);

                    // Iterate through the array of patients
                    for (int i = 0; i < patientsArray.length(); i++) {
                        JSONObject patientObject = patientsArray.getJSONObject(i);

                        // Extract patient details for each patient
                        String number = patientObject.getString("number");
                        String name = patientObject.getString("name");
                        String location = patientObject.getString("location");
                        String age = patientObject.getString("age");
                        String gender = patientObject.getString("gender");
                        String phone = patientObject.getString("phone");
                        String dateOfBirth = patientObject.getString("dateOfBirth");
                        String diagnosis = patientObject.getString("diagnosis");
                        String prescription = patientObject.getString("prescription");
                        String pcondition = patientObject.getString("pcondition");

                        patientModel = new PatientModel(name, location, age, gender, phone, dateOfBirth, diagnosis, prescription, number, pcondition);
                        //patientModel = new PatientModel(number, name, location, age, gender, phone, dateOfBirth, diagnosis, prescription, pcondition);

                        List<PatientModel> patients = new ArrayList<>();
                        patients.add(patientModel);
                    }
                    // Create the custom adapter and set it to your ListView
                    PatientListAdapter adapter = new PatientListAdapter(getApplicationContext(), patients);
                    patientList.setAdapter(adapter);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
        }*/

}