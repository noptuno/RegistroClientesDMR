package com.example.registroclientesdmr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.registroclientesdmr.inicio.InicioSesion;
import com.example.registroclientesdmr.principal.ListaClientes;
import com.example.registroclientesdmr.principal.RegistroClientes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private String estado = "NO";
    private FirebaseAuth mAuth;
    private SharedPreferences pref;
    private Button btnregistrarclientes,btnlistacleintes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("LOGINDMR", Context.MODE_PRIVATE);
        estado = pref.getString("ESTADO_LOGIN", "NO");
        mAuth = FirebaseAuth.getInstance();

        btnregistrarclientes = findViewById(R.id.btn_Registrar_Clientes);
        btnlistacleintes = findViewById(R.id.btn_Lista_Clientes);


        btnregistrarclientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ints = new Intent(MainActivity.this, RegistroClientes.class);
                startActivity(ints);

            }
        });

        btnlistacleintes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ints = new Intent(MainActivity.this, ListaClientes.class);
                startActivity(ints);

            }
        });

        if (estado.equals("NO")) {
            Intent ints = new Intent(MainActivity.this, InicioSesion.class);
            startActivity(ints);
            MainActivity.this.finish();
        }else{

            String email = mAuth.getCurrentUser().getEmail();

        }



        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {

          //  Intent v = new Intent(MainActivity.this, Configuracion_Impresora.class);
          //  startActivity(v);
           // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            return true;
        } else if (id == R.id.action_sesion) {

            AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
            build.setMessage("Desea Cerrar Sesión").setPositiveButton("Si", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {

                    revokeAccess();

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = build.create();
            alertDialog.show();
            return true;

        }else if (id == R.id.about) {

                DisplayAboutDialog();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void revokeAccess() {

            // Firebase sign out
        mAuth.signOut();
        Intent ints = new Intent(MainActivity.this, InicioSesion.class);
        startActivity(ints);
        SharedPreferences pref = getSharedPreferences("LOGINDMR", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ESTADO_LOGIN", "NO");
        editor.apply();
        MainActivity.this.finish();

    }

    void DisplayAboutDialog() {
        final AppCompatDialog about = new AppCompatDialog(MainActivity.this);
        about.setContentView(R.layout.doabout);
        about.setCancelable(true);
        about.setTitle(R.string.about);

        // get version of the application.
        PackageInfo pinfo;
        try
        {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            if (pinfo != null) {


                TextView descTextView = (TextView) about.findViewById(R.id.AboutDescription);


                String descString = " " + getString(R.string.app_name) + "\n"
                        + " Version Code:"
                        + String.valueOf(pinfo.versionCode) + "\n"
                        + " Version Name:" + pinfo.versionName+"\n"
                        + " Copyright: 2018" + "\n"
                        + " Lipiner S.A (DMR mil rollos)" + "\r\n"
                        + " Dirección: Convenio 828"  + "\r\n"
                        + " Teléfono: (+598) 2209 19 21"  + "\r\n"
                        + " Contacto: desarrollo@dmr.com.uy";

                if(descTextView != null)
                    descTextView.setText(descString);

                // set up the image view
                ImageView AboutImgView = (ImageView) about
                        .findViewById(R.id.AboutImageView);

                if (AboutImgView != null)
                    AboutImgView.setImageResource(R.mipmap.ic_launcher);

                // set up button
                Button closeButton = (Button) about.findViewById(R.id.AboutCloseButton);
                if (closeButton != null) {
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            about.dismiss();
                        }
                    });
                }

                about.show();
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}