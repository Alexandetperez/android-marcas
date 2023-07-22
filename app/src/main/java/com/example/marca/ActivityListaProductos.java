package com.example.marca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.productos.Models.Articulos;
import com.example.productos.Models.Marcas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ActivityListaProductos extends AppCompatActivity {
    ListView ListaProdu;
    Button volver2;
    FloatingActionButton agregarP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        ListaProdu=findViewById(R.id.ListaProdu);
        volver2=findViewById(R.id.volver2);
        agregarP=findViewById(R.id.agregarP);

        agregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(ActivityListaProductos.this,ActivityAgregarProducto.class);
                intento.putExtra("accion","agregar");
                startActivity(intento);
            }
        });

        volver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(ActivityListaProductos.this,MainActivity.class);
                startActivity(intento);
            }
        });

        List<Articulos> listArticulos = Articulos.listarArticuloObj(this,null,null);

        ArrayAdapter<Articulos> miAdaptador=new ArrayAdapter<Articulos>(this, android.R.layout.simple_list_item_1,listArticulos);

        ListaProdu.setAdapter(miAdaptador);

        //evento de la lista
        ListaProdu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí obtienes el producto seleccionado
                Articulos articuloSeleccionado = (Articulos) parent.getItemAtPosition(position);

                // Creas el AlertDialog
                CharSequence[] opciones = new CharSequence[]{"Modificar", "Detalle", "Eliminar", "Cancelar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaProductos.this);
                builder.setTitle("Selecciona una opción");
                builder.setItems(opciones,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                Intent intento;
                                switch (which) {
                                    case 0:
                                        //Modificar
                                        intento = new Intent(ActivityListaProductos.this,ActivityAgregarProducto.class);
                                        intento.putExtra("accion","modificar");
                                        intento.putExtra("idProdu",articuloSeleccionado.getId());
                                        startActivity(intento);
                                        break;
                                    case 1:
                                        // Detalle
                                        intento = new Intent(ActivityListaProductos.this,ActivityDetalleArticulo.class);
                                        intento.putExtra("idProdu",articuloSeleccionado.getId());
                                        startActivity(intento);

                                        break;
                                    case 2:
                                        // Eliminar
                                        articuloSeleccionado.eliminar(ActivityListaProductos.this);
                                        miAdaptador.remove(articuloSeleccionado);
                                        miAdaptador.notifyDataSetChanged();
                                        break;
                                    case 3:
                                        // Cancelar
                                        // Cierro el alertdialog
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        });
                builder.show();
            }
        });
    }
}


