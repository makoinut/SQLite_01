package com.sena.esp.sqlite_01.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sena.esp.sqlite_01.modelo.Abogado;

import java.util.ArrayList;


/**
 * Created on 2/27/17.
 */

public class AbogadoHelper extends SQLiteOpenHelper{

    /**
     *
     * @param context context es la clase que lo llama.
     * @param factory factory se guarda en forma temporal la info que se consulte, normalmente se envia nulo
     * @param version version número de la versión de la base de datos.
     */
    public AbogadoHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "sistemas_abogados.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TblAbogado.NOMBRE_TABLA + "(" +
                TblAbogado._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TblAbogado.CEDULA + " TEXT UNIQUE,"
                + TblAbogado.NOMBRE + " TEXT,"
                + TblAbogado.ESPECIALIDAD + " TEXT,"
                + TblAbogado.BIOGRAFIA + " TEXT,"
                + TblAbogado.TELEFONO + " TEXT);";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if exists table " + TblAbogado.NOMBRE_TABLA);
        onCreate(db);
    }

    /**
     * Guarda un registro de abogado en la base de datos
     *
     * @param abogado objecto abogado
     * @param bd base de datos
     * @return si se guardó el regisro
     */
    public boolean guardarAbogado(Abogado abogado, SQLiteDatabase bd){

        ContentValues valores = new ContentValues();
        valores.put(TblAbogado.CEDULA, abogado.getCedula());
        valores.put(TblAbogado.NOMBRE, abogado.getNombre());
        valores.put(TblAbogado.TELEFONO, abogado.getTelefono());
        valores.put(TblAbogado.ESPECIALIDAD, abogado.getEspecialidad());
        valores.put(TblAbogado.BIOGRAFIA, abogado.getBiografia());

        return  (bd.insert(TblAbogado.NOMBRE_TABLA, null, valores) > 0);
    }

    /**
     * Obtiene el siguiente id disponible en la table
     *
     * @param bd base de datos
     * @return id
     */
    public int contarRegistros(SQLiteDatabase bd){

        Cursor mCount = bd.rawQuery("Select count (*) from ".concat(TblAbogado.NOMBRE_TABLA), null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        return count;
    }

    /**
     * Obtiene una lista con los nombres de los abogados
     *
     * @return lista
     */
    public ArrayList<String> obtenerAbogados() {

        String consultaSQL = "SELECT ".concat(TblAbogado.NOMBRE).concat(" FROM ").concat(TblAbogado.NOMBRE_TABLA);
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor  = bd.rawQuery(consultaSQL, null);
        final ArrayList<String> data = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        bd.close();

        return data;
    }
}
