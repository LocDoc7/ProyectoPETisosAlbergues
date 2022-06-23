package com.ay.proyectopetisos.ui.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.CategoriaCanesAdapter;
import com.ay.proyectopetisos.MainActivity;
import com.ay.proyectopetisos.MapsActivityReg;
import com.ay.proyectopetisos.Model.CategoriaCanes;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;
import com.ay.proyectopetisos.Util.Util;
import com.ay.proyectopetisos.splash;
import com.ay.proyectopetisos.ui.RecupearContraseña.RecuperarActivity;
import com.ay.proyectopetisos.ui.Registro.Registro1Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    Button btn_registrar, btn_iniciar;
    EditText edtUsuario, edtContrasenia;
    TextView tvOlvidasteContraseña;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    int idusuario,cantDonaciones, cantCollares,perScore;
    private boolean hayError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_registrar = findViewById(R.id.btn_registrate_login);
        tvOlvidasteContraseña = findViewById(R.id.tv_olvide_contra_login);
        btn_iniciar = findViewById(R.id.btn_iniciar_login);
        edtUsuario = findViewById(R.id.edt_usuario_login);
        edtContrasenia = findViewById(R.id.edt_password_login);
        progressBar = findViewById(R.id.progress_bar_login);
        requestQueue = Volley.newRequestQueue(this);

        comprobarShared();

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarUsuario();
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar_pantalla();
                Intent i = new Intent(LoginActivity.this, Registro1Activity.class);
                startActivity(i);

            }
        });

        tvOlvidasteContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar_pantalla();
                Intent i = new Intent(LoginActivity.this, RecuperarActivity.class);
                startActivity(i);

            }
        });
    }

    private void comprobarShared() {
        SharedPreferences sp1 = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String user = sp1.getString("ususario","");
        String pwd = sp1.getString("contraseña","");
        if (!user.isEmpty()  && !pwd.isEmpty()){
            edtUsuario.setText(user);
            edtContrasenia.setText(pwd);
            edtUsuario.setEnabled(false);
            edtContrasenia.setEnabled(false);
            btn_iniciar.setEnabled(false);
            btn_iniciar.setTextColor(Color.parseColor("#9E9E9E"));
            btn_registrar.setEnabled(false);
            tvOlvidasteContraseña.setEnabled(false);
            edtContrasenia.setTextColor(Color.parseColor("#9E9E9E"));
            edtUsuario.setTextColor(Color.parseColor("#9E9E9E"));
            btn_registrar.setTextColor(Color.parseColor("#9E9E9E"));
            tvOlvidasteContraseña.setTextColor(Color.parseColor("#9E9E9E"));
            verificarUsuario();
        }

    }

    private void verificarUsuario() {

        String usuario = edtUsuario.getText().toString();
        String pass = edtContrasenia.getText().toString();
        hayError = false;

        if (usuario.isEmpty()) {
            edtUsuario.setError("Ingrese un usuario");
            hayError = true;
        }
        if (pass.isEmpty()) {
            edtContrasenia.setError("Ingrese una contraseña");
            hayError = true;
        }
        if (!hayError) {
            cargarWebService(usuario,pass);
        }

    }

    private void cargarWebService(String usuario, String contra) {
        SharedPreferences sp1 = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String token = sp1.getString("token","");
        progressBar.setVisibility(View.VISIBLE);
        String url = Util.RUTA+"consultar_usuario.php?usuario="+usuario+"&contrasenia="+contra+"&token="+token;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                JSONArray json = response.optJSONArray("consulta");
                try {
                    for (int i = 0; i<json.length();i++){
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        idusuario = jsonObject.optInt("IdPersona");
                        cantDonaciones = jsonObject.optInt("perCantDonaciones");
                        cantCollares = jsonObject.optInt("perCantCollares");
                        perScore = jsonObject.optInt("perScore");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error" + response, Toast.LENGTH_SHORT).show();
                }
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idUsuario",idusuario);
                editor.putInt("cantDonaciones",cantDonaciones);
                editor.putString("ususario",edtUsuario.getText().toString());
                editor.putString("contraseña",edtContrasenia.getText().toString());
                editor.putInt("cantCollares",cantCollares);
                editor.putInt("cantScore",perScore);
                editor.commit();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Ups!, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
                edtUsuario.setEnabled(true);
                edtContrasenia.setEnabled(true);
                btn_iniciar.setEnabled(true);
                btn_registrar.setEnabled(true);
                tvOlvidasteContraseña.setEnabled(true);
                edtUsuario.setTextColor(Color.parseColor("#FFFFFF"));
                edtContrasenia.setTextColor(Color.parseColor("#FFFFFF"));
                edtContrasenia.setText("");
                edtContrasenia.setError("Ingrese una contraseña valida");
                btn_iniciar.setTextColor(Color.parseColor("#FFFFFF"));
                btn_registrar.setTextColor(Color.parseColor("#FFFFFF"));
                tvOlvidasteContraseña.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void limpiar_pantalla(){
        edtUsuario.setText("");
        edtContrasenia.setText("");
    }

}