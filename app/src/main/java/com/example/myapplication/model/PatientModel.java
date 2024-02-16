package com.example.myapplication.model;

public class PatientModel {
    String name;
    String location;
    String age;
    String gender;
    String phone;
    String dateOfBirth;
    String diagnosis;
    String prescription;
    String number;
    String pcondition;

    public PatientModel(String name, String location, String age, String gender, String phone, String dateOfBirth, String diagnosis, String prescription, String number, String pcondition) {
        this.name = name;
        this.location = location;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.number = number;
        this.pcondition = pcondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPcondition() {
        return pcondition;
    }

    public void setPcondition(String pcondition) {
        this.pcondition = pcondition;
    }
}
