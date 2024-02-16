package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText etMail, etPass;
    CardView logIn;
    private String email, password;
    public String status;
    //private static final String url = "http://10.0.2.2/E-healthSphare/db_user.php";
    private static final String url = "http://192.168.1.106/E-healthSphare/db_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        logIn = findViewById(R.id.login_card);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etMail.getText().toString().trim();
                password = etPass.getText().toString().trim();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this,
                                    "No answer has been selected",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    RadioButton radioButton
                            = (RadioButton)radioGroup
                            .findViewById(selectedId);

                    String user = radioButton.getText().toString();
                    if (user.equals("Admin")){
                        status = "1";
                    } else if (user.equals("Doctor")) {
                        status = "2";
                    }
                    else {
                        status = "3";
                    }
                    getUserAccess();
                }

            }

            private void getUserAccess() {

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ("User exists".equals(response)) {
                            if (status.equals("2")){
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), DoctorDashboard.class);
                                intent.putExtra("email", email);
                                intent.putExtra("pass", password);
                                intent.putExtra("status", status);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("t1", email);
                        param.put("t2", password);
                        param.put("t3", status);
                        return param;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
            }
        });
    }
}
