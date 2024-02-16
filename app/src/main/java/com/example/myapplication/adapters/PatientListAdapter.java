package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.PatientModel;

import java.util.List;

public class PatientListAdapter extends ArrayAdapter<PatientModel> {

    public PatientListAdapter(Context context, List<PatientModel> patients) {
        super(context, 0, patients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PatientModel patient = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detailsview, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvAge = convertView.findViewById(R.id.tvAge);
        TextView tvNumber = convertView.findViewById(R.id.tvNumber);
        TextView tvGender = convertView.findViewById(R.id.tvGender);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvDateOfBirth = convertView.findViewById(R.id.tvDOB);
        TextView tvDiagnosis = convertView.findViewById(R.id.tvDiagnosis);
        TextView tvPrescription = convertView.findViewById(R.id.tvPrescription);


        if (patient != null) {
            tvName.setText("Name: " + patient.getName());
            tvLocation.setText("Location: " + patient.getLocation());
            tvAge.setText("Age: " + patient.getAge());
            tvNumber.setText("Patient Number: " +patient.getNumber());
            tvGender.setText("Gender: " +patient.getGender());
            tvPhone.setText("Phone no: " +patient.getPhone());
            tvDateOfBirth.setText("DOB: " +patient.getDateOfBirth());
            tvDiagnosis.setText("Diagnosis: " +patient.getDiagnosis());
            tvPrescription.setText("Prescription: " +patient.getPrescription());
            // Set other TextView values...
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
