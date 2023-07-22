package com.example.marca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.Models.Marcas;

public class ActivityIngresarMarca extends AppCompatActivity {
    Button btnGuardar,btnListar, cancelar;
    EditText nombre,desc;
    TextView titulo;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_marca);
        btnGuardar=findViewById(R.id.btnGuardar);
        btnListar=findViewById(R.id.btnListar);
        nombre=findViewById(R.id.nombre);
        desc=findViewById(R.id.descripcion);
        cancelar=findViewById(R.id.cancelar);
        titulo=findViewById(R.id.tituloMarca);

        //intento
        Intent intentoValores=getIntent();
        String accion = intentoValores.getStringExtra("accion");
        if (accion.equals("modificar")){
            titulo.setText("Modificar una marca");
            btnGuardar.setText("Modificar");
            int idMarca=intentoValores.getIntExtra("idMarca",0);
            Marcas marca = new Marcas(this, idMarca);
            nombre.setText(marca.getNombre());
            desc.setText(marca.getDescripcion());
        }


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento=new Intent(ActivityIngresarMarca.this,MainActivity.class);
                startActivity(intento);
            }
        });

        //funcion del boton listar
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(ActivityIngresarMarca.this,ActivityListaMarcas.class);
                startActivity(intento);
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreV=nombre.getText().toString();
                String descV=desc.getText().toString();

                //verificamos la accion
                Intent intentoValores = getIntent();
                String accion = intentoValores.getStringExtra("accion");
                Marcas marcaobj;
                if (accion.equals("modificar")){
                    int idMarca=intentoValores.getIntExtra("idMarca",0);
                    marcaobj = new Marcas(ActivityIngresarMarca.this,idMarca);
                }else{
                    // Creo un objeto de tipo marca
                    marcaobj=new Marcas();
                }

                marcaobj.setDescripcion(descV);
                marcaobj.setNombre(nombreV);
                if (accion.equals("modificar")){
                    marcaobj.modificar(ActivityIngresarMarca.this);
                    Toast.makeText(ActivityIngresarMarca.this, "SE MODIFICÓ LA MARCA: "+nombreV, Toast.LENGTH_SHORT).show();
                }else{
                marcaobj.crearMarca(ActivityIngresarMarca.this);
                    Toast.makeText(ActivityIngresarMarca.this, "SE CREO LA MARCA: "+nombreV, Toast.LENGTH_SHORT).show();
                }
                Intent intento = new Intent(ActivityIngresarMarca.this, ActivityListaMarcas.class);
                startActivity(intento);
            }
        });
    }
}