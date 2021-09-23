package com.example.registroclientesdmr.principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.registroclientesdmr.R;
import com.example.registroclientesdmr.adapters.AdapterCliente;
import com.example.registroclientesdmr.clase.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListaClientes extends AppCompatActivity {
    private Button cancelar;
    private AdapterCliente adapter;
    private FirebaseAuth firebaseauth;
    private FirebaseFirestore firebasefirestore;
    private FirebaseUser firebaseuser;
    private TextView cliente_seleccionado;


    ArrayList<Cliente> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        botones();
        initFireBase();
        Mostrar_Series();



        adapter = new AdapterCliente();
        adapter.setOnNoteSelectedListener(new AdapterCliente.OnNoteSelectedListener() {
            @Override
            public void onClick(Cliente note) {

                String ID = note.getId();
                cliente_seleccionado.setText(note.getEmail());
              //  idtablaserie.setText(note.getId());
               // numeroserie.setText(note.getNserie());
            }

        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reycler_list_clientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    private void botones() {

        cliente_seleccionado = findViewById(R.id.txt_NombreCliente);

        cancelar = findViewById(R.id.btn_cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }

    private void Mostrar_Series() {

        firebasefirestore.collection("TABLA_CLIENTES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> resultado) {

                        if (resultado.getResult().size() > 0) {

                            list.clear();

                            for (DocumentSnapshot tablaseries : resultado.getResult().getDocuments()) {

                                Cliente cliente = new Cliente();

                                cliente.setId(tablaseries.getId());
                                cliente.setNombre(tablaseries.getString("nombre"));
                                cliente.setEmpresa(tablaseries.getString("empresa"));
                                cliente.setCargo(tablaseries.getString("cargo"));
                                cliente.setTelefono(tablaseries.getString("telefono"));
                                cliente.setEmail(tablaseries.getString("email"));
                                cliente.setNota(tablaseries.getString("nota"));
                                cliente.setEmail_usuario(tablaseries.getString("email_usuario"));
                                cliente.setFecha_registro(tablaseries.getString("fecha_registro"));
                                list.add(cliente);

                            }

                            actualizarReciclerView();

                        }
                    }
                });

    }

    public void actualizarReciclerView() {
        adapter.setNotes(list);
        adapter.notifyDataSetChanged();
    }

    private void initFireBase() {

        firebaseauth = FirebaseAuth.getInstance();
        firebasefirestore = FirebaseFirestore.getInstance();
        firebaseuser = firebaseauth.getCurrentUser();

    }
}