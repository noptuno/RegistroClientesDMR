package com.example.registroclientesdmr.principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registroclientesdmr.MainActivity;
import com.example.registroclientesdmr.R;
import com.example.registroclientesdmr.clase.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistroClientes extends AppCompatActivity {

    private Handler m_handler = new Handler(); // Main thread
    private ProgressDialog dialog;


    private FirebaseAuth mAut;
    private FirebaseFirestore firebasefirestore;
    private FirebaseUser firebaseuser;

    private Button cancelar,btnnuevo,btnguardar,btnmodificar,btnlista,btnimprimir;

    private String email_usuario,fecha,nombre,empresa,cargo,telefono,email,nota;

    private EditText etnombre,etempresa,etcargo,ettelefono,etemail,etnota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clientes);
        botones();
        initFireBase();
    }


    private void registrar_firebase(Cliente cliente) {

        EnableDialog(true,"Registro");

        Map<String, Object> temp = new HashMap<>();

        temp.put("nombre", cliente.getNombre().toUpperCase());
        temp.put("empresa", cliente.getEmpresa().toUpperCase());
        temp.put("cargo", cliente.getCargo().toUpperCase());
        temp.put("telefono", cliente.getTelefono().toUpperCase());
        temp.put("email", cliente.getEmail().toUpperCase());
        temp.put("nota", cliente.getNota().toUpperCase());
        temp.put("email_usuario", cliente.getEmail_usuario().toUpperCase());
        temp.put("fecha_registro", cliente.getFecha_registro().toUpperCase());


        firebasefirestore.collection("TABLA_CLIENTES")
                .add(temp)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                        showToast("Registro Exitoso");
                        restablecer();
                        EnableDialog(false,"Registro");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        showToast("Hubo un error de conexion");
                        EnableDialog(false,"Registro");
                    }
                });
    }

    private void showToast(String mensaje) {

        Toast.makeText(RegistroClientes.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void restablecer() {

        etnombre.setText("");
        etempresa.setText("");
        etcargo.setText("");
        ettelefono.setText("");
        etemail.setText("");
        etnota.setText("");
        btnnuevo.setEnabled(true);
        btnguardar.setEnabled(true);
        btnmodificar.setEnabled(false);
        btnlista.setEnabled(true);
        btnimprimir.setEnabled(false);
        cancelar.setEnabled(true);

        etnombre.requestFocus();

    }


    private void initFireBase() {
        mAut = FirebaseAuth.getInstance();
        firebasefirestore = FirebaseFirestore.getInstance();
        firebaseuser = mAut.getCurrentUser();


        if (firebaseuser != null) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            email_usuario = firebaseuser.getEmail();
            fecha = date;

        } else {
            Log.w("ERROR:", "No existe un UID");
        }

    }


    private void botones() {

        etnombre = findViewById(R.id.et_nombre);
        etempresa = findViewById(R.id.et_empresa);
        etcargo = findViewById(R.id.et_cargo);
        ettelefono = findViewById(R.id.et_telefono);
        etemail = findViewById(R.id.et_email);
        etnota = findViewById(R.id.et_nota);


        btnnuevo = findViewById(R.id.btn_nuevo);
        btnguardar = findViewById(R.id.btn_guardar);
        btnmodificar = findViewById(R.id.btn_modificar);
        btnlista = findViewById(R.id.btn_Lista);
        btnimprimir = findViewById(R.id.btn_imprimir);
        cancelar = findViewById(R.id.btn_Atras);



        btnnuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = etnombre.getText().toString();
                empresa= etempresa.getText().toString();
                cargo= etcargo.getText().toString();
                telefono= ettelefono.getText().toString();
                email= etemail.getText().toString();
                nota= etnota.getText().toString();

                if (!email_usuario.isEmpty() && !nombre.isEmpty()){
                    Cliente cliente = new Cliente(nombre,empresa,cargo,telefono,email,nota,email_usuario,fecha);
                    registrar_firebase(cliente);
                }


            }
        });
        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        btnimprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


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


}