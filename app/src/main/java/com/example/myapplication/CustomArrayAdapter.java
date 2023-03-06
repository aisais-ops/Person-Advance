package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Person> {
    Context context=getContext();
    int resource;
    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
 /**************récupérer layout******************************/
        View layout=v;
        if(v==null)  layout= LayoutInflater.from(context).inflate(resource,parent,false);
/********************récupérer views*************/
        TextView name,adresse,age;
        ImageButton tel,email,web,map,msg,youtube,instagram,facebook,whatsapp;
        ImageView img;
        name= (TextView) layout.findViewById(R.id.name);
        age= (TextView) layout.findViewById(R.id.age);
        adresse= (TextView) layout.findViewById(R.id.adresse);
        img= (ImageView) layout.findViewById(R.id.photo);
        tel= (ImageButton) layout.findViewById(R.id.emaile);
        email= (ImageButton) layout.findViewById(R.id.emaile);
        msg= (ImageButton) layout.findViewById(R.id.msg);
        web= (ImageButton) layout.findViewById(R.id.web);
        youtube= (ImageButton) layout.findViewById(R.id.youtube);
        map= (ImageButton) layout.findViewById(R.id.map);
        whatsapp= (ImageButton) layout.findViewById(R.id.whatsapp);
        facebook= (ImageButton) layout.findViewById(R.id.facebook);
        instagram= (ImageButton) layout.findViewById(R.id.instagram);
/*****************read data at position******************************************************************/
        Person item=(Person) getItem(position);
        byte[] p=item.getPhoto();
        Person.Genre genre= item.getGenre();
        boolean isVaccine1=  item.isVaccine1();
        String phone= item.getTel();
        String emaile=  item.getEmail();
/*******************simple bind data with views************************/
        name.setText(item.getNom());
        age.setText(String.valueOf(item.getAge()));
        adresse.setText(item.getAdresse()+" . "+item.getVille());
/*******************advanced bind*************************************************************/
        layout.setBackgroundColor(isVaccine1?context.getResources().getColor(R.color.green):context.getResources().getColor(R.color.red));
        if(p!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(p, 0, p.length);
            img.setImageBitmap(bitmap);
        }
        else img.setImageResource((genre==Person.Genre.Mr?R.mipmap.mr: (genre == Person.Genre.Mme? R.mipmap.mme: R.mipmap.mlle)));
        tel.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"tel",phone);}});
        instagram.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"instagram","");}});
        facebook.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"facebook","");}});
        whatsapp.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"whatsapp",phone);}});
        map.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"map","");}});
        youtube.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"youtube","");}});
        web.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"web","www.ofppt.ma");}});
        msg.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"msg",phone);}});
        email.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"email",emaile);}});

        return layout;
    }
}
