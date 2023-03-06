package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {
    private int layout;

    public CustomSimpleCursorAdapter (Context context,int layout, Cursor c,String[] from,int[] to) {
        super(context,layout,c,from,to);
        this.layout=layout;
    }


    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layout, null);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        View layout = view;
/********************récupérer views*************/
        ImageButton tel,email,web,map,msg,youtube,instagram,facebook,whatsapp;
        ImageView img;

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

        @SuppressLint("Range") byte[] p=(byte[])cursor.getBlob(cursor.getColumnIndex("photo"));
        @SuppressLint("Range")Person.Genre genre= Person.Genre.valueOf(cursor.getString(cursor.getColumnIndex("genre")));
        @SuppressLint("Range") boolean isVaccine1=  cursor.getInt(cursor.getColumnIndex("isVaccine"))!=0;
        @SuppressLint("Range")  String phone= cursor.getString(cursor.getColumnIndex("tel"));
        @SuppressLint("Range") String emaile= cursor.getString(cursor.getColumnIndex("email"));

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
    }

}
