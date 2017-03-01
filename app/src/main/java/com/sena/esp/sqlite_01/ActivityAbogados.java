package com.sena.esp.sqlite_01;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sena.esp.sqlite_01.repositorios.AbogadoHelper;

import java.util.ArrayList;

public class ActivityAbogados extends AppCompatActivity {

    private   ListView listView ;
    private AbogadoHelper abogadoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abogados);
        abogadoHelper = new AbogadoHelper(this, null, 1);
        inicializarInterfaz();
    }

    public void inicializarInterfaz(){

        //Obtener una lista con los nombres de los abogados
        SQLiteDatabase bd = abogadoHelper.getWritableDatabase();
        ArrayList<String> arrayList = abogadoHelper.obtenerAbogados();
        bd.close();

        listView = (ListView) findViewById(R.id.listAbogados);
        TextView sinRegistrosTv = (TextView)findViewById(R.id.sinRegistrosTv);

        sinRegistrosTv.setVisibility(arrayList.size() > 0 ? View.GONE : View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), String.format("Posición %s, Abogado %s", position, itemValue), Toast.LENGTH_LONG).show();
            }
        });

        //Llenar la lista con los nombres
        ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
