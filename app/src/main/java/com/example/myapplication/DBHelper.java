package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "persondb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists person (_id INTEGER primary key autoincrement,nom TEXT, ville TEXT, adresse TEXT, tel TEXT,email TEXT, genre TEXT, age INTEGER, poids INTEGER, taille REAL, vaccine1 INTEGER, vaccine2 INTEGER,dateVacc1 INTEGER,dateVacc2 INTEGER, photo BLOB)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         db.execSQL("drop table person");
         onCreate(db);
    }
    public ContentValues getContentValues(Person p){
        ContentValues cv=new ContentValues();
        cv.put("nom",p.getNom());
        cv.put("ville",p.getVille());
        cv.put("adresse",p.getAdresse());
        cv.put("tel",p.getTel());
        cv.put("email",p.getEmail());
        cv.put("genre","Mr");
        cv.put("age",p.getAge());
        cv.put("poids",p.getPoids());
        cv.put("taille",p.getTaille());
        cv.put("vaccine1",p.isVaccine1());
        cv.put("vaccine2",p.isVaccine2());
        cv.put("dateVacc1",p.getDateVacc1().getTime());
        cv.put("dateVacc2",p.getDateVacc2().getTime());
        cv.put("photo",p.getPhoto());
        return cv;
    }
    public void  insert(Person p){
        SQLiteDatabase db=getWritableDatabase();
        db.insert("person",null,getContentValues(p));
    }
    public void  update(Person p){
        SQLiteDatabase db=getWritableDatabase();
        db.update("person",getContentValues(p),"_id=?",new String[]{p.getId()+""});
    }
    public void  delete(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("person","_id=?",new String[]{id+""});
    }
    public Cursor listAllCursor() {
        List<Person> l = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from person", null);
        return c;
    }
    @SuppressLint("Range")
    public ArrayList<Person> listAll() throws PersonException {
        ArrayList<Person> l=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("select * from person",null);
        while(c.moveToNext()){
            Person p=new Person();
            p.setNom(c.getString(c.getColumnIndex("nom")));
            p.setVille(c.getString(c.getColumnIndex("ville")));
            p.setAdresse(c.getString(c.getColumnIndex("adresse")));
            p.setTel(c.getString(c.getColumnIndex("tel")));
            p.setEmail(c.getString(c.getColumnIndex("email")));
            p.setGenre(Person.Genre.Mr);//Person.Genre.valueOf(c.getString(c.getColumnIndex("genre")))
            p.setAge(c.getInt(c.getColumnIndex("age")));
            p.setPoids(c.getInt(c.getColumnIndex("poids")));
            p.setTaille(c.getDouble(c.getColumnIndex("taille")));
            p.setVaccine1(c.getInt(c.getColumnIndex("vaccine1"))!=0);
            p.setVaccine2(c.getInt(c.getColumnIndex("vaccine2"))!=0);
            p.setDateVacc1(new Date(c.getLong(c.getColumnIndex("dateVacc1"))));
            p.setDateVacc2(new Date(c.getLong(c.getColumnIndex("dateVacc2"))));
            p.setPhoto(c.getBlob(c.getColumnIndex("photo")));
            l.add(p);
        }
        return l;
    }
}
