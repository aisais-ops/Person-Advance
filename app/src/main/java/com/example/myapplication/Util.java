package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class Util {
    public static void appLauncher(Context context,String app, String value){
        String uri="";
        String packageName="";
        switch(app){
            case "tel": uri = "tel:"+value;break;
            case "email":uri = "mailto:"+value;break;//intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");intent.putExtra(Intent.EXTRA_TEXT, "your_text");Don't use direct package name like("com.google.android.gm") because in future if they change package name your app will face problems.
            case "msg": uri = "smsto:"+value;break;//intent.putExtra("sms_body", message);
            case "web": uri = "https://www.offpt.ma";break;
            case "map": uri = "geo:43.565715431592736,1.398482322692871";break;// avec latitude and longitude
            case "youtube":packageName="com.google.android.youtube";break;
            case "whatsapp":uri= "https://api.whatsapp.com/send?phone="+value;packageName="com.whatsapp";break;
            case "facebook": uri = "https://www.facebook.com/rachid.saad.988";packageName="com.facebook.katana";break;
            case "instagram":uri= "https://www.instagram.com/hicham_stiitou/";packageName="com.instagram.android";break;
        }
        Intent intent = new Intent ();
        try {
            if(packageName.isEmpty())
                intent = new Intent (Intent.ACTION_VIEW , Uri.parse( uri ));
            else intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

            context.startActivity(intent);
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Sorry...You don't have any suitable app", Toast.LENGTH_SHORT).show();
            try { intent =new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName));}
            catch (Exception e1) { intent =new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName));}
        }

    }
    public static void printAllPackages(Context context){
        //<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"/>
        final PackageManager pm = context.getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }
    }

    public static void saveInBinaryFile(File f, List l)throws Exception{
        ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(f));
        for(Object o:l) writer.writeObject(o);
        writer.close();
    }
    public static void saveInTextFile(String filename,List l)throws Exception{
        File root = new File(Environment.getExternalStorageDirectory().toString(), "Notes");
        //   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // if external memory exists and folder with name Notes this will create folder.
        if (!root.exists())  root.mkdirs(); //
        File f = new File(root,  filename);
        PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        for (Object o : l) writer.println(o.toString());
        writer.close();
    }
}
