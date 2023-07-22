package com.example.marca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productos.Models.Marcas;

public class MainActivity extends AppCompatActivity {

    Button agregar,ListarProdu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listar los productos
        ListarProdu=findViewById(R.id.ListarProdu);
        ListarProdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this,ActivityListaProductos.class);
                startActivity(intento);
            }
        });

        //Agregar marcas
        agregar=findViewById(R.id.agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this,ActivityIngresarMarca.class);
                intento.putExtra("accion","agregar");
                startActivity(intento);
            }
});
    }
}