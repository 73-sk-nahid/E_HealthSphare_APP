package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Appointments extends AppCompatActivity {
    String doctorToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Intent i = getIntent();
        doctorToken = i.getStringExtra("dr");
        Toast.makeText(this, doctorToken, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        getAppointmentsData();
        super.onStart();
    }

    private void getAppointmentsData() {
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        //String url = "http://10.0.2.2/E-healthSphare/doctor_details.php?t1=" + email + "&t2=" + password + "&t3=" + status + "";
        String url = "http://192.168.1.106/E-healthSphare/appointments_details.php?t1="+doctorToken+"";

        Log.e("url", url);
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TableLayout)findViewById(R.id.tableLayout)).removeAllViews();
                Toast.makeText(Appointments.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray userArray = new JSONArray(response);
                    // Parse the JSON response
                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject patientObject = userArray.getJSONObject(i);
                        // Extract patient details for each patient
                        String pName = patientObject.getString("name");
                        String pNumber = patientObject.getString("fromm");
                        String pStatus = patientObject.getString("paymentstatus");
                        String reply = patientObject.getString("message");

                        TableRow tableRow = new TableRow(getApplicationContext());
                        setOnTable(pNumber, pName, pStatus, reply);

                        // Add the TableRow to the TableLayout
                        ((TableLayout)findViewById(R.id.tableLayout)).addView(tableRow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }
    private void setOnTable(String pNumber, String pName, String pStatus, String reply) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        if (tableLayout.getChildCount() == 0) {
            // Add table header if it doesn't exist
            addTableHeader(tableLayout);
        }

        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);

        TextView textViewNumber = new TextView(this);
        TextView textViewName = new TextView(this);
        TextView textViewStatus = new TextView(this);
        TextView textViewReply = new TextView(this);

        // Set weights for TextView elements
        textViewNumber.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
        textViewName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        textViewStatus.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        textViewReply.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));

        // Set gravity, max lines, and padding for TextView elements
        textViewNumber.setGravity(Gravity.CENTER);
        textViewName.setGravity(Gravity.CENTER);
        textViewStatus.setGravity(Gravity.CENTER);
        textViewReply.setGravity(Gravity.CENTER);

        textViewNumber.setMaxLines(1);
        textViewName.setMaxLines(1);
        textViewStatus.setMaxLines(1);
        textViewReply.setMaxLines(1);

        textViewNumber.setPadding(2, 10, 2, 10);
        textViewName.setPadding(2, 10, 2, 10);
        textViewStatus.setPadding(2, 10, 2, 10);
        textViewReply.setPadding(2, 10, 2, 10);

        textViewNumber.setText(pNumber);
        textViewName.setText(pName);
        textViewStatus.setText(pStatus);
        textViewReply.setText(reply);

        textViewNumber.setBackgroundColor(Color.WHITE);
        textViewName.setBackgroundColor(Color.WHITE);
        textViewStatus.setBackgroundColor(Color.WHITE);
        textViewReply.setBackgroundColor(Color.WHITE);

        tableRow.addView(textViewNumber);
        tableRow.addView(textViewName);
        tableRow.addView(textViewStatus);
        tableRow.addView(textViewReply);

        tableLayout.addView(tableRow);
    }


    /*private void setOnTable(String pNumber, String pName, String pStatus, String reply) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        if (tableLayout.getChildCount() == 0) {
            // Add table header if it doesn't exist
            addTableHeader(tableLayout);
        }
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);

        TextView textViewNumber = new TextView(this);
        TextView textViewName = new TextView(this);
        TextView textViewStatus = new TextView(this);
        TextView textViewReply = new TextView(this);

        textViewNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
        textViewName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.5f));
        textViewStatus.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.5f));
        textViewReply.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.5f));

        textViewNumber.setGravity(Gravity.CENTER);
        textViewName.setGravity(Gravity.CENTER);
        textViewStatus.setGravity(Gravity.CENTER);
        textViewReply.setGravity(Gravity.CENTER);

        textViewNumber.setMaxLines(1);
        textViewName.setMaxLines(1);
        textViewStatus.setMaxLines(1);
        textViewReply.setMaxLines(1);

        textViewNumber.setPadding(2, 10, 2, 10);
        textViewName.setPadding(2, 10, 2, 10);
        textViewStatus.setPadding(2, 10, 2, 10);
        textViewReply.setPadding(2, 10, 2, 10);

        textViewNumber.setText(pNumber);
        textViewName.setText(pName);
        textViewStatus.setText(pStatus);
        textViewReply.setText(reply);

        textViewNumber.setBackgroundColor(Color.WHITE);
        textViewName.setBackgroundColor(Color.WHITE);
        textViewStatus.setBackgroundColor(Color.WHITE);
        textViewReply.setBackgroundColor(Color.WHITE);

        tableRow.addView(textViewNumber);
        tableRow.addView(textViewName);
        tableRow.addView(textViewStatus);
        tableRow.addView(textViewReply);
    }*/

    private void addTableHeader(TableLayout tableLayout) {
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.parseColor("#FFC107")); // Set header background color

        String[] headerText = {"Patient Number", "Name", "Payment Status", "Reply"};

        for (String title : headerText) {
            TextView headerTextView = new TextView(this);
            headerTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
            headerTextView.setGravity(Gravity.CENTER);
            headerTextView.setMaxLines(1);
            headerTextView.setPadding(2, 10, 2, 10);
            headerTextView.setText(title);
            headerTextView.setTextColor(Color.BLACK); // Set header text color
            headerRow.addView(headerTextView);
        }

        tableLayout.addView(headerRow);
    }
}