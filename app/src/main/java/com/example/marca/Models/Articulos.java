package com.example.productos.Models;

import android.content.Context;

import com.example.productos.conexion.BdOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Articulos {
    // ATRIBUTOS - PROPIEDADES - CARACTERISTICAS
    // Tipo nombreVariable; =Valor;

    private int id;
    private String nombre;
    private double precio;
    private int idMarca;

    //METODOS - FUNCIONES - ACCIONES

    //Constructor vacio
    public Articulos() {
    }

    //Constructor con parametros
    //Me servira para construir un objeto tipo Marca con el id
    public Articulos(Context miContexto, int id) {
        String cadenaSql="select * from articulos where id="+id;
        String [][] miArticulo= BdOpenHelper.consultaConRetorno(miContexto, cadenaSql);

        if(miArticulo !=null && miArticulo.length> 0) {
            this.id = id;
            nombre=miArticulo[0][1];
            precio= Double.parseDouble(miArticulo[0][2]);
            idMarca= Integer.parseInt(miArticulo[0][3]);
        }
    }
    @Override
    public String toString(){
        return id +" "+nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    //CRUD - metodos
    // Listar
    public static String[][] listarArticulos(Context context,String filtro, String orden){
        String miCadenaSql="select * from articulos";
        if(filtro !=null) miCadenaSql +=" where "+filtro;
        if(orden !=null) miCadenaSql +=" order by "+orden;
        return BdOpenHelper.consultaConRetorno(context, miCadenaSql);
    }
    public static List<Articulos> listarArticuloObj(Context context, String filtro, String orden){
        String[][] datosArticulo=Articulos.listarArticulos(context,filtro,orden);
        List<Articulos> articulosList=new ArrayList<>();
        if(datosArticulo !=null){
            // Recorrer los datos
            for(int contador=0; contador < datosArticulo.length; contador++){
                Articulos miObjetoArticulo=new Articulos();
                miObjetoArticulo.id= Integer.parseInt(datosArticulo[contador][0]);
                miObjetoArticulo.nombre=datosArticulo[contador][1];
                miObjetoArticulo.precio= Double.parseDouble(datosArticulo[contador][2]);
                articulosList.add(miObjetoArticulo);
            }
        }
        return articulosList;
    }
    public void crearArti(Context context){
        String cadenaSql="insert into articulos (nombre,precio,idMarca) values ('"+nombre+"',"+precio+","+idMarca+")";
        BdOpenHelper.consultaSinRetorno(context,cadenaSql);
    }

    public void modificar(Context context){
        String cadenaSql="update articulos set nombre='"+ nombre + "',precio="+ precio +",idMarca="+ idMarca +" where id="+id;
        BdOpenHelper.consultaSinRetorno(context, cadenaSql);
    }
    public void eliminar(Context context){
        String cadenaSql="delete from articulos where id="+id;
        BdOpenHelper.consultaSinRetorno(context, cadenaSql);
    }
}
