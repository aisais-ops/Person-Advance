package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSimpleAdapter extends SimpleAdapter {
    Context context;
    public CustomSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 /**************récupérer layout******************************/
        View layout = super.getView(position, convertView, parent);
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
        HashMap<String, Object> item=(HashMap<String, Object>)getItem(position);
        byte[] p=(byte[])item.get("photo");
        Person.Genre genre= (Person.Genre) item.get("genre");
        boolean isVaccine1= (boolean) item.get("isVaccine");
        String phone= (String) item.get("tel");
        String emaile= (String) item.get("email");

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
