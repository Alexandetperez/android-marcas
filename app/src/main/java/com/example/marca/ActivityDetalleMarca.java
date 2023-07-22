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

public class ActivityDetalleMarca extends AppCompatActivity {
    TextView tituloDM, textDesc;
    Button btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_marca);
        tituloDM=findViewById(R.id.tituloDM);
        textDesc=findViewById(R.id.textDesc);
        btnvolver=findViewById(R.id.btnvolver);

        Intent intento=getIntent();
        int id=intento.getIntExtra("idMarca", 0);
        Marcas marca=new Marcas(this, id);
        tituloDM.setText(marca.getNombre());

        List<Articulos> articulos = Articulos.listarArticuloObj(this,"idMarca="+marca.getId()
        ,null);

        textDesc.setText(marca.getDescripcion()+"\n"+(long) articulos.size()+" productos relacionado");

        //evento volver
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(ActivityDetalleMarca.this, ActivityListaMarcas.class);
                startActivity(intento);
            }
        });
    }
}