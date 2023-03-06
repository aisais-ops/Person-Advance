package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Person;

import java.text.BreakIterator;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
	private List<Person> list;
	int resource;
    Context context;
	public CustomRecyclerViewAdapter(Context context,int resource, List<Person> list) {
       this.context=context; this.list = list;this.resource=resource;
    }
	@Override
    public int getItemCount() {return list.size();}
	@Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(resource, parent, false);
        return new ViewHolder(layout);
    }
	public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,adresse,age;
        public ImageButton tel,email,web,map,msg,youtube,instagram,facebook,whatsapp;
        public ImageView img;

        public ViewHolder(View layout) {
            super(layout);
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
        }
    }
    @Override
    public void onBindViewHolder(CustomRecyclerViewAdapter.ViewHolder holder, int position) {
        Person item = list.get(position);
        /*****************read data at position******************************************************************/
        byte[] p=item.getPhoto();
        Person.Genre genre= item.getGenre();
        boolean isVaccine1=  item.isVaccine1();
        String phone= item.getTel();
        String emaile=  item.getEmail();
        /******************* bind*************/
        View layout=holder.itemView;
        layout.setBackgroundColor(isVaccine1?context.getResources().getColor(R.color.green):context.getResources().getColor(R.color.red));
        holder.name.setText(item.getNom());
        holder.age.setText(String.valueOf(item.getAge()));
        holder.adresse.setText(item.getAdresse()+" . "+item.getVille());
        if(p!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(p, 0, p.length);
            holder.img.setImageBitmap(bitmap);
        }
        else holder.img.setImageResource((genre==Person.Genre.Mr?R.mipmap.mr: (genre == Person.Genre.Mme? R.mipmap.mme: R.mipmap.mlle)));
        holder.tel.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"tel",phone);}});
        holder.instagram.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"instagram","");}});
        holder.facebook.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"facebook","");}});
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"whatsapp",phone);}});
        holder.map.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"map","");}});
        holder.youtube.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"youtube","");}});
        holder.web.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"web","www.ofppt.ma");}});
        holder.msg.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"msg",phone);}});
        holder.email.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {Util.appLauncher(context,"email",emaile);}});
        
    }  
}
