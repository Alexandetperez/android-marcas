package com.example.productos.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdOpenHelper extends SQLiteOpenHelper {// ATRUBUTOS - PROPIEDADES - CARACTERISTICAS
    static String bdNombre="Productos";
    static int bdVersion=1;

    //METODOS - ACCIONES - FUNCIONES

    public BdOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREAMOS LA BASE DE DATOS CUANDO SE EJECUTE LA APLICACION

        //Primera tabla
        String cadenaSql="create table marcas (id integer primary key autoincrement, nombre text, descripcion text)";
        sqLiteDatabase.execSQL(cadenaSql);

        //Segunda tabla
        cadenaSql="create table articulos (id integer primary key autoincrement, nombre text, precio double, idMarca integer references marcas(id))";
        sqLiteDatabase.execSQL(cadenaSql);

        //Insertar datos - NO recomendable, esto es solo un ejemplo.
        // La insercion de datos es solo en los formularios de la interface

        cadenaSql="insert into marcas(nombre, descripcion) values ('adidas', 'zapatos rojos')";
        sqLiteDatabase.execSQL(cadenaSql);
        cadenaSql="insert into articulos(nombre,precio,idMarca) values ('zapatos para dama',250000,1)";
        sqLiteDatabase.execSQL(cadenaSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // ACTUALIZAR LA BASE DE DATOS

        String cadenaSql="drop table if exists articulos";
        sqLiteDatabase.execSQL(cadenaSql);
        cadenaSql="drop table if exists marcas";
        sqLiteDatabase.execSQL(cadenaSql);
        onCreate(sqLiteDatabase);
    }

    // CREO MIS METODOS DE CONSULTA FORMA GENERICA
    // CONSULTA SIN RETORNO --MODIFICADOR DE ACCESO
    public static void consultaSinRetorno(Context context, String cadenaSql){
        // update, delete, insert
        BdOpenHelper miConector=new BdOpenHelper(context,bdNombre,null,bdVersion);
        SQLiteDatabase miBd=miConector.getWritableDatabase();
        miBd.execSQL(cadenaSql);
        miBd.close();
        miConector.close();
    }


    //CONSULTA CON RETORNO
    public static String[][] consultaConRetorno(Context context,String cadenaSql){
        // select
        String [][] datos=null;
        BdOpenHelper miConector=new BdOpenHelper(context,bdNombre, null,bdVersion);
        SQLiteDatabase miBd=miConector.getReadableDatabase();
        Cursor miCursor= miBd.rawQuery(cadenaSql, null);
        //CONTAR FILAS Y COLUMNAS
        int filas=miCursor.getCount();
        int columnas=miCursor.getColumnCount();

        //DOY LONGITUD A LA MATRIZ
        datos=new String[filas][columnas];
        int contadorFila=0;
        while (miCursor.moveToNext()){ //verdadero si tiene almenos una fila
            // recorrer las columnas de cada fila
            for (int contadorCol=0; contadorCol< columnas; contadorCol++){
                //ASIGNAMOS EL VALOR DE LA TABLA A CADA POSICION
                datos[contadorFila][contadorCol]=miCursor.getString(contadorCol);
            }
            contadorFila++;
        }
        return datos;
    }
}
