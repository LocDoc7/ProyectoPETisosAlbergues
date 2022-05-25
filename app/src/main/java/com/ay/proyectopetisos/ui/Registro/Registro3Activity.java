package com.ay.proyectopetisos.ui.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;
import com.ay.proyectopetisos.ui.Login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class Registro3Activity extends AppCompatActivity {
    EditText edtUsuario, edtContra1, edtiContra2;
    Button btnRegistrar;
    private boolean hayError;
    String apellidos,nombres,edad,numero,dni,ubicacion,ubilatitud,ubilongitud, pathimg, descripcion, usuario, pwd1, pwd2;
    ProgressBar progressBar;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        edtUsuario = findViewById(R.id.edt_usuario_reg3);
        edtContra1 = findViewById(R.id.edt_contraseña_1_reg3);
        edtiContra2 = findViewById(R.id.edt_contraseña_2_reg3);
        btnRegistrar = findViewById(R.id.btn_registrarse_reg3);
        progressBar = findViewById(R.id.progress_bar_reg3);

        apellidos = getIntent().getStringExtra("apellidos");
        nombres = getIntent().getStringExtra("nombres");
        edad = getIntent().getStringExtra("edad");
        numero = getIntent().getStringExtra("numero");
        dni = getIntent().getStringExtra("dni");
        ubicacion = getIntent().getStringExtra("ubicacion");
        ubilatitud = getIntent().getStringExtra("ubilatitud");
        ubilongitud = getIntent().getStringExtra("ubilongitud");
        descripcion = getIntent().getStringExtra("descripcion");
        SharedPreferences sharedPreferences = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        pathimg = sharedPreferences.getString("img",pathimg);




        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarDatos();
            }
        });

    }

    private void comprobarDatos() {

        usuario = edtUsuario.getText().toString();
        pwd1 = edtContra1.getText().toString();
        pwd2 = edtiContra2.getText().toString();

        hayError = false;

        if (usuario.isEmpty()) {
            edtUsuario.setError("Ingrese un usuario");
            hayError = true;
        }
        if (pwd1.isEmpty()) {
            edtContra1.setError("Ingrese una contraseña");
            hayError = true;
        }
        if (pwd2.isEmpty() || !pwd2.equals(pwd1)) {
            edtiContra2.setError("Por favor, repita la contraseña");
            hayError = true;
        }

        if (!hayError) {
            cargarWebService();
        }

    }

    private void cargarWebService() {
        progressBar.setVisibility(View.VISIBLE);

        String url = Util.RUTA + "reg_user.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Registro3Activity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Registro3Activity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },1000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Registro3Activity.this, "Error al registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("apellidos",apellidos);
                params.put("nombres",nombres);
                params.put("edad",edad);
                params.put("numero",numero);
                params.put("dni",dni);
                params.put("ubicacion",ubicacion);
                params.put("ubilatitud",ubilatitud);
                params.put("ubilongitud",ubilongitud);
                params.put("pathimg",pathimg);
                params.put("descripcion",descripcion);
                params.put("usuario",usuario);
                params.put("pwd1",pwd1);

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}