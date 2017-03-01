package com.sena.esp.sqlite_01;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sena.esp.sqlite_01.modelo.Abogado;
import com.sena.esp.sqlite_01.repositorios.AbogadoHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textNombre, textTelefono,
            textEspecialidad, textBiografia, textCedula;
    private TextView cantidadTv;

    private AbogadoHelper abogadoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarInterfaz();

        abogadoHelper = new AbogadoHelper(this, null, 1);
        cantidadRegistros();
    }

    public void inicializarInterfaz(){

        textCedula = (EditText) findViewById(R.id.textCedula);
        textNombre = (EditText) findViewById(R.id.textNombre);
        textTelefono = (EditText) findViewById(R.id.textTelefono);
        textEspecialidad = (EditText) findViewById(R.id.textEspecialidad);
        textBiografia = (EditText) findViewById(R.id.textBiografia);
        cantidadTv = (TextView) findViewById(R.id.cantidadTv);

        Button buttonGuardar = (Button) findViewById(R.id.buttonGuardar);
        Button buttonListar = (Button) findViewById(R.id.buttonListar);
        Button buttonConsultar = (Button) findViewById(R.id.buttonConsultar);

        buttonGuardar.setOnClickListener(this);
        buttonConsultar.setOnClickListener(this);
        buttonListar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        SQLiteDatabase bd = abogadoHelper.getWritableDatabase();
        Abogado abogado = new Abogado();

        switch (v.getId()){
            //Boton guardar registro
            case R.id.buttonGuardar:

                if(textCedula.getText().length() == 0 || textNombre.getText().length() == 0 ||
                        textTelefono.length() == 0 || textEspecialidad.length() == 0 ||
                        textBiografia.getText().length() == 0){

                    mostrarMensaje(getString(R.string.datos_incompletos));

                }else{

                    abogado.setCedula(textCedula.getText().toString());
                    abogado.setTelefono(textTelefono.getText().toString());
                    abogado.setNombre(textNombre.getText().toString());
                    abogado.setBiografia(textBiografia.getText().toString());
                    abogado.setEspecialidad(textEspecialidad.getText().toString());

                    if(abogadoHelper.guardarAbogado(abogado, bd)){
                        mostrarMensaje(String.format(getString(R.string.registro_creado), textNombre.getText().toString()));
                        limpiarCampos();
                        bd.close();
                        cantidadRegistros();
                    }

                    bd.close();
                }

                break;
            //Botón Listar regstros
            case R.id.buttonListar:

                Intent intent = new Intent(this, ActivityAbogados.class);
                startActivity(intent);

                break;
            //Botón consultar
            case R.id.buttonConsultar:
                mostrarMensaje(getString(R.string.en_implementacion));
                break;
        }
    }

    /**
     * Limpia los campos y ubica el foco en el campo de cedula
     */
    private void limpiarCampos(){
        textCedula.setText("");
        textNombre.setText("");
        textBiografia.setText("");
        textEspecialidad.setText("");
        textTelefono.setText("");
        textCedula.requestFocus();
    }

    /**
     * Mustra un mensaje al usuario
     * @param mensaje texto que se desea mostrar
     */
    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Obtiene la cantidad de registros en la base de datos
     */
    private void cantidadRegistros(){
        SQLiteDatabase bd = abogadoHelper.getWritableDatabase();
        cantidadTv.setText(String.format(getString(R.string.registros), String.valueOf(abogadoHelper.contarRegistros(bd))));
        bd.close();
    }
}
