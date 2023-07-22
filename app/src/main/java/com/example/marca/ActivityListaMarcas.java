package com.example.marca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.productos.Models.Marcas;

import java.util.List;

public class ActivityListaMarcas extends AppCompatActivity {
    ListView listaMarcasView;
    Button btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_marcas);
        listaMarcasView=findViewById(R.id.listaMarcas);
        btnVolver=findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(ActivityListaMarcas.this,MainActivity.class);
                startActivity(intento);
            }
        });

        List<Marcas> listMarcas= Marcas.listarMArcasObj(this,null, null);

        ArrayAdapter<Marcas> miAdaptador=new ArrayAdapter<Marcas>(this, android.R.layout.simple_list_item_1,listMarcas);
        listaMarcasView.setAdapter(miAdaptador);

        //evento de la lista
        listaMarcasView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí obtienes el producto seleccionado
                Marcas marcaSeleccionada = (Marcas) parent.getItemAtPosition(position);

                // Creas el AlertDialog
                CharSequence[] opciones = new CharSequence[]{"Modificar", "Detalle", "Eliminar", "Cancelar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaMarcas.this);
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
                                        intento = new Intent(ActivityListaMarcas.this,ActivityIngresarMarca.class);
                                        intento.putExtra("accion","modificar");
                                        intento.putExtra("idMarca",marcaSeleccionada.getId());
                                        startActivity(intento);
                                        break;
                                    case 1:
                                        // Detalle
                                        intento = new Intent(ActivityListaMarcas.this,ActivityDetalleMarca.class);
                                        intento.putExtra("idMarca",marcaSeleccionada.getId());
                                        startActivity(intento);

                                        break;
                                    case 2:
                                        // Eliminar
                                        marcaSeleccionada.eliminar(ActivityListaMarcas.this);
                                        miAdaptador.remove(marcaSeleccionada);
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