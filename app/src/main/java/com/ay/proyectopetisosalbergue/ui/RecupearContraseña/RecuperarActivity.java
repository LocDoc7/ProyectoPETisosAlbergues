package com.ay.proyectopetisosalbergue.ui.RecupearContraseña;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;
import com.ay.proyectopetisosalbergue.ui.Login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecuperarActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText edtDni, edtPwd1, edtPwd2;
    Button btnCambiar;
    ProgressBar progressBar;
    private boolean hayError;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    String dni, pwd;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        edtDni = findViewById(R.id.edt_dni_recuperar);
        edtPwd1 = findViewById(R.id.edt_contraseña_1_recuperar);
        edtPwd2 = findViewById(R.id.edt_contraseña_2_recuperar);
        btnCambiar = findViewById(R.id.btn_cambiar_recuperar);
        progressBar = findViewById(R.id.progress_bar_recuperar);
        requestQueue = Volley.newRequestQueue(this);
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });


    }

    private void validarCampos() {
        hayError = false;
        if (edtDni.getText().toString().isEmpty() || edtDni.getText().length()<8) {
            edtDni.setError("Ingrese un DNI válido");
            hayError = true;
        }
        if (edtPwd1.getText().toString().isEmpty()) {
            edtPwd1.setError("Ingrese una contraseña");
            hayError = true;
        }
        if (edtPwd2.getText().toString().isEmpty()  || !edtPwd2.getText().toString().equals(edtPwd1.getText().toString())) {
            edtPwd2.setError("Repita la contraseña");
            hayError = true;
        }
        if (!hayError) {
            dni = edtDni.getText().toString();
            pwd = edtPwd1.getText().toString();
            cargarWebService();
        }


    }

    private void cargarWebService() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Util.RUTA + "change_pwd.php?dni_user="+dni+"&pwd_user="+pwd;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json =  response.optJSONArray("resultado");
        try {
            for (int i = 0; i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                result = jsonObject.optInt("retorno");
            }
            if (result == 0){
                mensajeError();
            }else {
                mensajeExitoso();
            }
            progressBar.setVisibility(View.GONE);
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "No hay conexion" + response, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void mensajeExitoso() {
        Toast.makeText(this, "Actualización exitosa", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RecuperarActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void mensajeError() {
        Toast.makeText(this, "No se ha encontrado registro del DNI ingresado", Toast.LENGTH_SHORT).show();
        edtDni.setError("DNI no registrado");
        edtDni.setText("");
        edtPwd1.setText("");
        edtPwd2.setText("");
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}