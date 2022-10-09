package com.ay.proyectopetisosalbergue.ui.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ay.proyectopetisosalbergue.R;

public class Registro1Activity extends AppCompatActivity {
    Button btnsiguiente;
    EditText edtApellidos,edtNombres,edtEdad,edtNumero,edtDNI;
    private boolean hayError;
    ProgressBar progressBar;
    String apellidos,nombres,edad,numero,dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);

        btnsiguiente = findViewById(R.id.btn_siguiente_reg1);
        edtApellidos = findViewById(R.id.edt_apellidos_reg1);
        edtNombres = findViewById(R.id.edt_nombres_user_reg1);
        edtEdad = findViewById(R.id.edt_edad_user_reg1);
        edtNumero = findViewById(R.id.edt_celular_user_reg1);
        edtDNI = findViewById(R.id.edt_DNI_user_reg1);
        progressBar = findViewById(R.id.progress_bar_reg1);


        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar1();
            }
        });

    }

    private void registrar1() {

        apellidos = edtApellidos.getText().toString();
        nombres = edtNombres.getText().toString();
        edad = edtEdad.getText().toString();
        numero = edtNumero.getText().toString();
        dni = edtDNI.getText().toString();

        hayError = false;

        if (apellidos.isEmpty()) {
            edtApellidos.setError("Ingrese un Apellido");
            hayError = true;
        }
        if (nombres.isEmpty()) {
            edtNombres.setError("Ingrese un Nombre");
            hayError = true;
        }
        if (edad.isEmpty() || Integer.parseInt(edad)<10 || Integer.parseInt(edad)>90) {
            edtEdad.setError("Ingrese una Edad válida");
            hayError = true;
        }
        if (numero.isEmpty() || numero.length()<9) {
            edtNumero.setError("Ingrese un teléfono válido");
            hayError = true;
        }
        if (dni.isEmpty() || dni.length()<8) {
            edtDNI.setError("Ingrese un DNI válido");
            hayError = true;
        }
        if (!hayError) {
            pasarDatos();
        }

    }

    private void pasarDatos() {
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Registro1Activity.this, Registro2Activity.class);
                i.putExtra("apellidos",apellidos);
                i.putExtra("nombres",nombres);
                i.putExtra("edad",edad);
                i.putExtra("numero",numero);
                i.putExtra("dni",dni);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        },1000);

    }
}