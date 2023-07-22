package com.example.marca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.Models.Articulos;
import com.example.productos.Models.Marcas;

import java.util.List;

public class ActivityAgregarProducto extends AppCompatActivity {
    EditText nombreP, precio;
    Button agregarProdu, volverInicio;
    Spinner listMarca;
    TextView titulo;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nombreP=findViewById(R.id.nombreP);
        precio=findViewById(R.id.precio);
        agregarProdu=findViewById(R.id.agregarProdu);
        volverInicio=findViewById(R.id.volverInicio);
        listMarca=findViewById(R.id.listMarca);
        titulo=findViewById(R.id.tituloPro);

        List<Marcas> listaMarcas = Marcas.listarMArcasObj(this,null,null);

        ArrayAdapter<Marcas> adapterMarcas = new ArrayAdapter<Marcas>(this,android.R.layout.simple_list_item_1,listaMarcas);
        listMarca.setAdapter(adapterMarcas);

        //intento
        Intent intentoValores=getIntent();
        String accion = intentoValores.getStringExtra("accion");
        if (accion.equals("modificar")){
            titulo.setText("Modificar una articulo");
            agregarProdu.setText("Modificar");
            int idProdu=intentoValores.getIntExtra("idProdu",0);
            Articulos articulo = new Articulos(this, idProdu);
            nombreP.setText(articulo.getNombre());
            precio.setText(String.valueOf(articulo.getPrecio()));
            Marcas marcaA = new Marcas(this,articulo.getIdMarca());
            //posicion
            int posicionMarca=0;
            for (int i =0;i<adapterMarcas.getCount();i++){
                if (adapterMarcas.getItem(i).getNombre().equals(marcaA.getNombre())){
                    posicionMarca=i;
                    break;
                }
            }
            listMarca.setSelection(posicionMarca);
        }

        agregarProdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrePro=nombreP.getText().toString();
                String precioPro=precio.getText().toString();
                Marcas marca=(Marcas) listMarca.getSelectedItem();

                String accion=intentoValores.getStringExtra("accion");
                Articulos articulo;
                if (accion.equals("agregar")){
                articulo=new Articulos();
                }else{
                    int idArti=intentoValores.getIntExtra("idProdu",0);
                    articulo=new Articulos(ActivityAgregarProducto.this,idArti);
                }
                articulo.setNombre(nombrePro);
                articulo.setPrecio(Double.parseDouble(precioPro));
                articulo.setIdMarca(marca.getId());
                if (accion.equals("agregar")){
                    articulo.crearArti(ActivityAgregarProducto.this);
                    Toast.makeText(ActivityAgregarProducto.this, "Producto "+articulo.getNombre()+" creado", Toast.LENGTH_SHORT).show();
                }else{
                    articulo.modificar(ActivityAgregarProducto.this);
                    Toast.makeText(ActivityAgregarProducto.this, "Producto "+articulo.getNombre()+" modificado", Toast.LENGTH_SHORT).show();
                }


                Intent intento=new Intent(ActivityAgregarProducto.this,ActivityListaProductos.class);
                startActivity(intento);
            }
        });
        volverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento=new Intent(ActivityAgregarProducto.this,ActivityListaProductos.class);
                startActivity(intento);
            }
        });
    }
}