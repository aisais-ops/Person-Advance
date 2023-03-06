package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsPerson extends AppCompatActivity {
    TextView nom, age, taille, poids, vaccin1, vaccin2, datevacc1, datevacc2, ville, adresse, tel_tv, msg_tv, web_tv, fb_tv, wh_tv, insta_tv;
    ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_person);

        //Get person list intent
        Intent intent= getIntent();
        Serializable p = intent.getSerializableExtra("persona");
        //findViews
        nom = (TextView) findViewById(R.id.nom);
        age = (TextView) findViewById(R.id.age);
        taille = (TextView) findViewById(R.id.taille);
        poids = (TextView) findViewById(R.id.poids);
        vaccin1 = (TextView) findViewById(R.id.vaccin1);
        vaccin2 = (TextView) findViewById(R.id.vaccin2);
        datevacc1 = (TextView) findViewById(R.id.datevacc1);
        datevacc2 = (TextView) findViewById(R.id.datevacc2);
        ville = (TextView) findViewById(R.id.ville);
        adresse = (TextView) findViewById(R.id.adresse);
        tel_tv = (TextView) findViewById(R.id.tel_tv);
        msg_tv = (TextView) findViewById(R.id.msg_tv);
        web_tv = (TextView) findViewById(R.id.web_tv);
        fb_tv = (TextView) findViewById(R.id.fb_tv);
        wh_tv = (TextView) findViewById(R.id.web_tv);
        insta_tv = (TextView) findViewById(R.id.insta_tv);
    }

    public void setData() {}
}