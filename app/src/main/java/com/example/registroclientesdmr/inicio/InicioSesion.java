package com.example.registroclientesdmr.inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registroclientesdmr.MainActivity;
import com.example.registroclientesdmr.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnsesion, btngoogle;
    private EditText txtemail;
    private EditText txtpassword;
    private ProgressDialog dialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);


        btngoogle = findViewById(R.id.btn_google);
        btnsesion = findViewById(R.id.btn_sesion);
        txtemail = findViewById(R.id.et_correo);
        txtpassword = findViewById(R.id.et_contrasena);
        progressDialog = new ProgressDialog(this);
        dialog = new ProgressDialog(this);
        iniciar_sesion();

        mAuth = FirebaseAuth.getInstance();


    }

    private void iniciar_sesion() {

        btnsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();

                if (email.length() > 0 && password.length() > 0) {
                    progressDialog.setMessage("Iniciando Sesion en linea...");
                    progressDialog.show();


                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(InicioSesion.this, "invalido", Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }

                            });


                } else {
                    Toast.makeText(InicioSesion.this, "Datos Inválidos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {

        hideProgressDialog();

        if (user != null) {

            Intent intent = new Intent(InicioSesion.this, MainActivity.class);
            startActivity(intent);


            SharedPreferences pref = getSharedPreferences("LOGINDMR", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("EMAIL", user.getEmail());
            editor.putString("ESTADO_LOGIN", "SI");
            editor.apply();
            finish();

        }
    }

    private void showProgressDialog() {


        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void hideProgressDialog() {

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.hide();
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
}