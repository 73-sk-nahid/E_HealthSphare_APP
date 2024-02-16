package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorDashboard extends AppCompatActivity {
    CardView patientAdd, patient;
    TextView drName, drEmail, drRole, drPhone;
    String email, password, status;
    String drToken;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        patientAdd = findViewById(R.id.addPatient);
        patient = findViewById(R.id.patient);
        drName = findViewById(R.id.Name);
        drEmail = findViewById(R.id.email);
        drRole = findViewById(R.id.role);
        drPhone = findViewById(R.id.phone);


        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("pass");
            status = intent.getStringExtra("status");
        }

        patientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPatient.class));
            }
        });
    }

    @Override
    protected void onStart() {
        getDoctorData();
        super.onStart();
    }

    private void getDoctorData() {
        //String url = "http://10.0.2.2/E-healthSphare/doctor_details.php?t1=" + email + "&t2=" + password + "&t3=" + status + "";
        String url = "http://192.168.1.106/E-healthSphare/doctor_details.php?t1=" + email + "&t2=" + password + "&t3=" + status + "";

        Log.e("url", url);
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
                    JSONObject userObject = new JSONObject(response);

                    // Extract user details
                    String firstName = userObject.getString("firstName");
                    String email = userObject.getString("email");
                    String phone = userObject.getString("phone");
                    String role = userObject.getString("role");
                    drToken = userObject.getString("token");
                    //Toast.makeText(DoctorDashboard.this, drToken, Toast.LENGTH_SHORT).show();

                    // Set values to TextViews
                    drName.setText(firstName);
                    drEmail.setText(email);
                    drRole.setText(role);
                    drPhone.setText(phone);

                } catch (JSONException e) {
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
    }

    public void patientDetails(View view) {
        Intent intent = new Intent(getApplicationContext(), PatientDetails.class);
        intent.putExtra("dr", drToken);
        startActivity(intent);
    }

    public void appointment(View view) {
        Intent intent = new Intent(getApplicationContext(), Appointments.class);
        intent.putExtra("dr", drToken);
        startActivity(intent);
    }

    public void addPatient(View view) {
        Intent intent1 = new Intent(getApplicationContext(), AddPatient.class);
        intent1.putExtra("DrToken", drToken);
        intent1.putExtra("dr", drToken);
        startActivity(intent1);
    }
}