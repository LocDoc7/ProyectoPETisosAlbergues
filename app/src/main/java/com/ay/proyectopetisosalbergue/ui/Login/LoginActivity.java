package com.ay.proyectopetisosalbergue.ui.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisosalbergue.MainActivity;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;
import com.ay.proyectopetisosalbergue.ui.RecupearContraseĆ±a.RecuperarActivity;
import com.ay.proyectopetisosalbergue.ui.Registro.Registro1Activity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.DeviceLoginButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button btn_registrar, btn_iniciar;
    //EditText edtUsuario, edtContrasenia;
    TextView tvOlvidasteContraseĆ±a;
    DeviceLoginButton btnLogin;
    CallbackManager callbackManager;
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
        tvOlvidasteContraseĆ±a = findViewById(R.id.tv_olvide_contra_login);
        btn_iniciar = findViewById(R.id.btn_iniciar_login);
        btnLogin = findViewById(R.id.btnLoginFB);
        //edtUsuario = findViewById(R.id.edt_usuario_login);
        //edtContrasenia = findViewById(R.id.edt_password_login);
        progressBar = findViewById(R.id.progress_bar_login);
        requestQueue = Volley.newRequestQueue(this);

        comprobarShared();

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificarUsuario();
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

        tvOlvidasteContraseĆ±a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar_pantalla();
                Intent i = new Intent(LoginActivity.this, RecuperarActivity.class);
                startActivity(i);

            }
        });

        callbackManager = CallbackManager.Factory.create();

        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Authenthicacion Cancelada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull FacebookException e) {
                Toast.makeText(LoginActivity.this, "Ha ocurrido en error en la authenticacion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void comprobarShared() {
        SharedPreferences sp1 = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String user = sp1.getString("ususario","");
        String pwd = sp1.getString("contraseĆ±a","");
        if (!user.isEmpty()  && !pwd.isEmpty()){
            /*edtUsuario.setText(user);
            edtContrasenia.setText(pwd);
            edtUsuario.setEnabled(false);
            edtContrasenia.setEnabled(false);*/
            btn_iniciar.setEnabled(false);
            btn_iniciar.setTextColor(Color.parseColor("#9E9E9E"));
            btn_registrar.setEnabled(false);
            tvOlvidasteContraseĆ±a.setEnabled(false);
            /*edtContrasenia.setTextColor(Color.parseColor("#9E9E9E"));
            edtUsuario.setTextColor(Color.parseColor("#9E9E9E"));*/
            btn_registrar.setTextColor(Color.parseColor("#9E9E9E"));
            tvOlvidasteContraseĆ±a.setTextColor(Color.parseColor("#9E9E9E"));
            //verificarUsuario();
        }

    }

    /*private void verificarUsuario() {

        String usuario = edtUsuario.getText().toString();
        String pass = edtContrasenia.getText().toString();
        hayError = false;

        if (usuario.isEmpty()) {
            edtUsuario.setError("Ingrese un usuario");
            hayError = true;
        }
        if (pass.isEmpty()) {
            edtContrasenia.setError("Ingrese una contraseĆ±a");
            hayError = true;
        }
        if (!hayError) {
            cargarWebService(usuario,pass);
        }

    }*/

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
                /*editor.putString("ususario",edtUsuario.getText().toString());
                editor.putString("contraseĆ±a",edtContrasenia.getText().toString());*/
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
                /*edtUsuario.setEnabled(true);
                edtContrasenia.setEnabled(true);*/
                btn_iniciar.setEnabled(true);
                btn_registrar.setEnabled(true);
                tvOlvidasteContraseĆ±a.setEnabled(true);
                /*edtUsuario.setTextColor(Color.parseColor("#FFFFFF"));
                edtContrasenia.setTextColor(Color.parseColor("#FFFFFF"));
                edtContrasenia.setText("");
                edtContrasenia.setError("Ingrese una contraseĆ±a valida");*/
                btn_iniciar.setTextColor(Color.parseColor("#FFFFFF"));
                btn_registrar.setTextColor(Color.parseColor("#FFFFFF"));
                tvOlvidasteContraseĆ±a.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void limpiar_pantalla(){
        /*edtUsuario.setText("");
        edtContrasenia.setText("");*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}