package com.example.registroclientesdmr.principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.registroclientesdmr.R;
import com.example.registroclientesdmr.adapters.AdapterCliente;
import com.example.registroclientesdmr.clase.Cliente;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaClientes extends AppCompatActivity {

    private Handler m_handler = new Handler(); // Main thread
    private ProgressDialog dialog;

private EditText filtro;
    private Button cancelar, btnlistar;
    private AdapterCliente adapter;
    private FirebaseAuth firebaseauth;
    private FirebaseFirestore firebasefirestore;
    private FirebaseUser firebaseuser;
    private TextView cliente_seleccionado;

    private String emailusaurio;

 private Cliente clienteSeleccionado = null;

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
                clienteSeleccionado = note;

              //  idtablaserie.setText(note.getId());
               // numeroserie.setText(note.getNserie());
            }

        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reycler_list_clientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    private void botones() {

        btnlistar = findViewById(R.id.btn_detalle);

        filtro = findViewById(R.id.et_filtro);

        cliente_seleccionado = findViewById(R.id.txt_NombreCliente);

        cancelar = findViewById(R.id.btn_cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filtro.getText().length()>0){
                    filtrar(filtro.getText().toString().trim());
                }else{
                    actualizarReciclerView();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cliente_seleccionado!= null){

/*
                    Intent i = new Intent(ListaClientes.this, RegistroClientes.class);
                    i.putExtra("sampleObject", (Serializable) cliente_seleccionado);
                    startActivity(i);
*/
                }


            }
        });

    }

    private void Mostrar_Series() {

        EnableDialog(true,"Cargando");

        firebasefirestore.collection("TABLA_CLIENTES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> resultado) {

                        if (resultado.getResult().size() > 0) {

                            list.clear();

                            for (DocumentSnapshot tablaseries : resultado.getResult().getDocuments()) {

                                if (tablaseries.getString("email_usuario").equals(emailusaurio)) {

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

                            }

                            actualizarReciclerView();
                            EnableDialog(false,"Cargando");
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
            emailusaurio = firebaseuser.getEmail().toUpperCase();
    }

    private void createCancelProgressDialog(String title, String message) {

        dialog = new ProgressDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void EnableDialog(final boolean value, final String mensaje) {
        m_handler.post(new Runnable() {
            @Override
            public void run() {
                if (value) {
                    createCancelProgressDialog("Cargando...", mensaje);

                } else {
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        });
    }

    private void filtrar(String s) {

        List<Cliente> seriesfilter = new ArrayList<>();
        List<Cliente> seriesfilter2 = new ArrayList<>();

        for (Cliente item : list) {

            if (item.getNombre().contains(s.toUpperCase())) {

                seriesfilter.add(item);

            }

        }
        adapter.setNotes(seriesfilter);
        adapter.notifyDataSetChanged();

    }


}