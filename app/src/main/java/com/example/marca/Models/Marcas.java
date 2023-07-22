package com.example.productos.Models;

import android.content.Context;

import com.example.productos.conexion.BdOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Marcas {
    // ATRIBUTOS - PROPIEDADES - CARACTERISTICAS
    // Tipo nombreVariable; =Valor;

    private int id;
    private String nombre;
    private String descripcion;

    //METODOS - FUNCIONES - ACCIONES

    //Constructor vacio
    public Marcas() {
    }

    //Constructor con parametros
    //Me servira para construir un objeto tipo Marca con el id
    public Marcas(Context miContexto, int id) {
        String cadenaSql="select * from marcas where id="+id;
        String [][] miMarca= BdOpenHelper.consultaConRetorno(miContexto, cadenaSql);

        if(miMarca !=null && miMarca.length> 0) {
            this.id = id;
            nombre=miMarca[0][1];
            descripcion=miMarca[0][2];
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;


    }
    //CRUD - metodos
    // Listar
    public static String[][] listarMarcas(Context context,String filtro, String orden){
        String miCadenaSql="select * from marcas";
        if(filtro !=null) miCadenaSql +=" where "+filtro;
        if(orden !=null) miCadenaSql +=" order by "+orden;
        return BdOpenHelper.consultaConRetorno(context, miCadenaSql);
    }
    public static List<Marcas> listarMArcasObj(Context context, String filtro, String orden){
        String[][] datosMarcas=Marcas.listarMarcas(context,filtro,orden);
        List<Marcas> marcasList=new ArrayList<>();
        if(datosMarcas !=null){
            // Recorrer los datos
            for(int contador=0; contador < datosMarcas.length; contador++){
                Marcas miObjetoMarca=new Marcas();
                miObjetoMarca.id= Integer.parseInt(datosMarcas[contador][0]);
                miObjetoMarca.nombre=datosMarcas[contador][1];
                miObjetoMarca.descripcion=datosMarcas[contador][2];
                marcasList.add(miObjetoMarca);
            }
        }
        return marcasList;
    }
    // Adicionar o crear marcas
    public void crearMarca(Context context){
        String cadenaSql="insert into marcas (nombre,descripcion) values ('"+nombre+"','"+descripcion+"')";
        BdOpenHelper.consultaSinRetorno(context,cadenaSql);
    }

    // Modificar una marca
    public void modificar(Context context){
        String cadenaSql="update marcas set nombre='"+nombre+"',descripcion='"+descripcion+"' where id="+id;
        BdOpenHelper.consultaSinRetorno(context,cadenaSql);
    }

    // Eliminar una marca
    public void eliminar(Context context) {
        List<Articulos> listArticulos = Articulos.listarArticuloObj(context,"idMarca="+id,null);
        if ((long)listArticulos.size()>0){
            for (int i =0;i<(long) listArticulos.size();i++){
                listArticulos.get(i).eliminar(context);
            }
        }
        String cadenaSql = "delete from marcas where id=" + id;
        BdOpenHelper.consultaSinRetorno(context, cadenaSql);
    }

}
