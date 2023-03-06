package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import java.io.*;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.*;

public class MainActivity2 extends AppCompatActivity{
    TextView tv;
    ListView listView;
    RecyclerView recyclerview;
    Button saveTextFile,saveBinaryFile,back;
    ArrayList<Person> list=new ArrayList<>();
    DBHelper db=new DBHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView) findViewById(R.id.tv);
        listView=(ListView) findViewById(R.id.listView) ;
        back=(Button) findViewById(R.id.back);
        saveTextFile=(Button) findViewById(R.id.saveTextfile);
        saveBinaryFile=(Button) findViewById(R.id.saveBinaryfile);
        recyclerview=(RecyclerView) findViewById(R.id.recyclerview);
        /*********************************récupérer Intent et lire l'extra*****************************/
        Intent i=getIntent();
        Serializable s=i.getSerializableExtra("list");
        if(s !=null) list =(ArrayList<Person>)s ;
        //Trier la collection
        //Collections.sort(list) ;
        Collections.sort(list, new Comparator<Person>(){
            public int compare(Person p1,Person p2){ return p2.getNom().compareTo(p1.getNom()); //ordre décroissant de nom
            }}) ;
        //afficher la valeur de l’extra dans un TextView
        String ch="";
        for(Person p : list)ch+=p+"\n";
        tv.setText(ch);
        //afficher la valeur de l’extra dans un ListView avec un adapter
        /**********************************CREER UN ADAPTER*************************/
        //method 0 : avec BaseAdapter personnalisé
        BaseAdapter adapter0=new CustomBaseAdapter(this,R.layout.person_item,list);
        // avec BaseAdapter anonyme
        BaseAdapter adapter1=new BaseAdapter(){
            private Context context;
            private ArrayList<Person> items;
            private int resource;
            public BaseAdapter init (Context context,int resource,ArrayList<Person> items)
            {this.context = context;this.items = items;	this.resource=resource; return this;}
            @Override
            public int getCount() {	return items.size();}
            @Override
            public Object getItem(int position) {return items.get(position);}
            @Override
            public long getItemId(int position) {return position;}
            @Override
            public View getView(int position, View v, ViewGroup parent) {return v;}//meme code in CustomBaseAdapter
        }.init(this,R.layout.person_item,list);
        //method 1 : avec un arrayAdapter simple (layout predefini contenant TextView avec id text1)
        ArrayAdapter<Person> adapter2=new ArrayAdapter<>( MainActivity2.this,android.R.layout.simple_list_item_1,list);
        //method 2 : avec un arrayAdapter simple (layout personnalisé contenant TextView id différent de text1)
        ArrayAdapter<Person> adapter3=new ArrayAdapter<>( MainActivity2.this, R.layout.person_item,R.id.name,list);
        //method 3 : avec un arrayAdapter personnalisé (plusieurs views in item)
        ArrayAdapter adapter4=new CustomArrayAdapter(this,R.layout.person_item,list);
        //avec un arrayAdapter anonyme
        ArrayAdapter<Person> adapter5=new ArrayAdapter<Person>(this,R.layout.person_item,list){
            int resource=R.layout.person_item;Context context=getContext();
            @Override
            public View getView(int position, View v, ViewGroup parent) { return v;}//meme code in CustomArrayAdapter
        };
        //method 4: avec un simpleAdapter sur plusieurs lignes
        //permet de mettre les valeurs selon type de valeur et type de composant sinon exception :Checkable(setChecked(boolean) or setText(String)), TextView(setText), ImageView(setImageResource(int) or setImageURI(Uri.parse(String)))
        //Known direct/indirect subclasses Checkable : CheckBox, CheckedTextView, CompoundButton, RadioButton, Switch, ToggleButton
        //Known direct/indirect subclasses TextView:   CheckBox, CheckedTextView, CompoundButton, RadioButton, Switch, ToggleButton, Button, Chronometer, DigitalClock, EditText, TextClock,AutoCompleteTextView, ExtractEditText, MultiAutoCompleteTextView,
        //Known direct/indirect subclasses ImageView: ImageButton, QuickContactBadge,ZoomButton
        ArrayList<HashMap<String, Object>> list1 = new ArrayList<>();
        for (Person p:list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("nom", p.getNom());
            map.put("age", p.getAge());
            map.put("adresse", p.getAdresse()+". "+p.getVille());
            map.put("tel",p.getTel());
            map.put("email",p.getEmail());
            map.put("photo",p.getPhoto());
            map.put("isVaccine",p.isVaccine1());
            map.put("genre",p.getGenre());
            list1.add(map);
        }
        String[] from = {"nom", "age","adresse"};
        int to[] = {R.id.name, R.id.age,R.id.adresse};
        SimpleAdapter adapter6=new SimpleAdapter( MainActivity2.this,list1,R.layout.person_item,from,to);
        SimpleAdapter adapter7=new SimpleAdapter( MainActivity2.this,list1,android.R.layout.simple_list_item_2,new String[]{"nom","age"},new int[]{android.R.id.text1,android.R.id.text2});
        // list.stream().map(item->{return new HashMap<String,Object>(){{put("nom",item.getNom());put("age",item.getAge());}};}).collect(Collectors.toList()),
        //  list.stream().map(item->{return new HashMap<String,Object>(Map.of("nom",item.getNom(),"age",item.getAge()));}).collect(Collectors.toList()),
        //avec  un simpleAdapter personnalisé
        SimpleAdapter adapter8=new CustomSimpleAdapter( MainActivity2.this,list1,R.layout.person_item,from,to);
        //avec  un simpleAdapter anonyme
        SimpleAdapter adapter9=new SimpleAdapter( MainActivity2.this,list1,R.layout.person_item,from,to)
        {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {//meme code in CustomSimpleAdapter
                View layout = super.getView(position, convertView, parent);
                HashMap<String, Object> item=(HashMap<String, Object>)getItem(position);
                return layout;
            }
        };
        //method 4: avec un SimpleCursorAdapter personnalisé
        CustomSimpleCursorAdapter adapter10= new CustomSimpleCursorAdapter(this,R.layout.person_item,db.listAllCursor(),new String[]{"nom", "age","adresse"},new int[]{R.id.name, R.id.age,R.id.adresse});

        /*******************associer un recyclerView******************************/
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(this,R.layout.person_item,list);
        recyclerview.setAdapter(adapter);
        //recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        /*******************associer un adapter à AdapterView(ListView)******************************/
        listView.setAdapter(adapter4);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        /*******************associer un listener à AdapterView(ListView)******************************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent0=new Intent(MainActivity2.this,MainActivity.class);
                intent0.putExtra("persona",list.get(i));
                startActivity(intent0);
                Toast.makeText(getApplicationContext(),"eeeeeeeeeeeeeeee",Toast.LENGTH_LONG).show();
            }
        });
        /*******************associer les listeners aux buttons*****************************/
        saveTextFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Util.saveInTextFile("persons.txt",list);
                        Toast.makeText(MainActivity2.this,"Successfull save ",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        saveBinaryFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File f=null;
                    try {
                        f=new File("persons.bin");
                        Util.saveInBinaryFile(f,list);
                        Toast.makeText(MainActivity2.this,"Successfull save in"+f.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity2.this,"Not Successfull save in"+f.getAbsolutePath()+":"+e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                    intent.putExtra("list",list);
                    startActivity(intent);
                }
            });
        }



    }

