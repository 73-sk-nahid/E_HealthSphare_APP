package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class AddPatient extends AppCompatActivity {
    TextInputEditText pName, pLocation, pAge, pPhone, pSymptoms, pPrescription, DOB;
    AutoCompleteTextView pGender, pCondition;
    String doctorToken, dr;
    Button add;
    private String[] list_of_genders = {"Select Gender", "Male", "Female"};
    private String[] list_of_condition = {"Select Condition", "Impatient", "Outpatient"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient_activity);

        Intent intent = getIntent();
        dr = intent.getStringExtra("dr");

        pName = findViewById(R.id.etFullName);
        pLocation = findViewById(R.id.etLocation);
        pAge = findViewById(R.id.etAge);
        pPhone = findViewById(R.id.etPhone);
        pSymptoms = findViewById(R.id.etDisease);
        pPrescription = findViewById(R.id.etPrescription);
        pGender = findViewById(R.id.etGender);
        pCondition = findViewById(R.id.etCondition);
        add = findViewById(R.id.addPatient);

        DOB = findViewById(R.id.etDOB);


        doctorToken = intent.getStringExtra("DrToken");


        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this,
                R.layout.gender_drop_down, list_of_genders);
        AutoCompleteTextView autoCompleteTextViewGender = pGender;
        autoCompleteTextViewGender.setAdapter(adapterGender);

        ArrayAdapter<String> adapterCondition = new ArrayAdapter<>(this,
                R.layout.condition_drop_down, list_of_condition);
        AutoCompleteTextView autoCompleteTextViewCondition = pCondition;
        autoCompleteTextViewCondition.setAdapter(adapterCondition);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient();
            }
        });
    }

    public void addPatient(){
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        String number, name, location, age, phone, dob, time, symptoms, prescription, token, gender, condition;
        number = generateRandomID();
        name = pName.getText().toString().trim();
        location = pLocation.getText().toString().trim();
        age = pAge.getText().toString().trim();
        phone = pPhone.getText().toString().trim();
        dob = DOB.getText().toString().trim();
        time = String.valueOf(System.currentTimeMillis());
        symptoms = pSymptoms.getText().toString().trim();
        prescription =pPrescription.getText().toString().trim();
        token = name+age+phone;
        gender = pGender.getText().toString().trim();
        condition = pCondition.getText().toString().trim();

        String url = "http://192.168.1.106/E-healthSphare/db_insert.php?t1="+name+"&t2="+location+"&t3="+age+"&t4="+gender+"&t5="+phone+"&t6="+dob+"&t7="+time+
                "&t8="+symptoms+"&t9="+prescription+"&t10="+token+"&t11="+dr+"&t12="+number+"&t13="+condition+"";
        Log.e("url", url);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on the screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);

        /*StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddPatient.this, response.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(AddPatient.this, error.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("t1", name);
                param.put("t2", location);
                param.put("t3", age);
                param.put("t4", gender);
                param.put("t5", phone);
                param.put("t6", dob);
                param.put("t7", time);
                param.put("t8", symptoms);
                param.put("t9", prescription);
                param.put("t10", token);
                param.put("t11", doctorToken);
                param.put("t12", number);
                param.put("t13", condition);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);*/
    }

    public void dob(View view) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date").setSelection(MaterialDatePicker
                        .todayInUtcMilliseconds()).build();

        datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                DOB.setText(datePicker.getHeaderText());
            }
        });
    }

    /*public void add(View view) {
        String number, name, location, age, phone, dob, time, symptoms, prescription, token, gender, condition;
        number = generateRandomID();
        name = pName.getText().toString().trim();
        location = pLocation.getText().toString().trim();
        age = pAge.getText().toString().trim();
        phone = pPhone.getText().toString().trim();
        dob = DOB.getText().toString().trim();
        time = String.valueOf(System.currentTimeMillis());
        symptoms = pSymptoms.getText().toString().trim();
        prescription =pPrescription.getText().toString().trim();
        token = name+age+phone;
        gender = pGender.getText().toString().trim();
        condition = pCondition.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddPatient.this, response.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(AddPatient.this, error.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("t1", name);
                param.put("t2", location);
                param.put("t3", age);
                param.put("t4", gender);
                param.put("t5", phone);
                param.put("t6", dob);
                param.put("t7", time);
                param.put("t8", symptoms);
                param.put("t9", prescription);
                param.put("t10", token);
                param.put("t11", doctorToken.toString());
                param.put("t12", number);
                param.put("t13", condition);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }*/


    private String generateRandomID() {
        Random random = new Random();
        int randomInt = random.nextInt(4000) + 1;
        return String.valueOf(randomInt);
    }
}