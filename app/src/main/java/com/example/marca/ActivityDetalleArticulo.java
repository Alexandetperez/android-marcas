package com.example.marca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.productos.Models.Articulos;
import com.example.productos.Models.Marcas;

import java.util.List;

public class ActivityDetalleArticulo extends AppCompatActivity {
    TextView tituloDP, precioP, marcaP;
    Button volverP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);
        tituloDP=findViewById(R.id.tituloDP);
        precioP=findViewById(R.id.precioP);
        marcaP=findViewById(R.id.marcaP);
        volverP=findViewById(R.id.volverP);

        Intent intento=getIntent();
        int id=intento.getIntExtra("idProdu", 0);
        Articulos articulo=new Articulos(this,id);
        tituloDP.setText(articulo.getNombre());

        precioP.setText("Precio: \n"+String.valueOf(articulo.getPrecio()));
        Marcas marca = new Marcas(this,articulo.getIdMarca());
        marcaP.setText("Marca:\n"+marca.getNombre());

        volverP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento =new Intent(ActivityDetalleArticulo.this,ActivityListaProductos.class);
                startActivity(intento);
            }
        });
    }
}