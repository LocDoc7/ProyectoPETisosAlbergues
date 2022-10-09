package com.ay.proyectopetisosalbergue.ui.Registro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.proyectopetisosalbergue.MapsActivityReg;
import com.ay.proyectopetisosalbergue.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro2Activity extends AppCompatActivity {
    Button btnsiguiente;
    ImageButton btn_mapa;
    TextView tvUbicacion;
    CircleImageView img_user;
    EditText edtDescripcion;
    int SELECT_IMAGE_CODE = 1;
    private boolean hayError;
    ProgressBar progressBar;
    Bitmap bitmap;
    Uri uri;
    String apellidos,nombres,edad,numero,dni,ubicacion,ubilatitud,ubilongitud, pathimg = null, descripcion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        btnsiguiente = findViewById(R.id.btn_siguiente_reg2);
        btn_mapa = findViewById(R.id.btn_mapa_reg2);
        tvUbicacion = findViewById(R.id.tv_ubicacion_reg2);
        edtDescripcion = findViewById(R.id.edt_descripcion_user_reg2);
        progressBar = findViewById(R.id.progress_bar_reg2);
        img_user = findViewById(R.id.user_img_circle);

        apellidos = getIntent().getStringExtra("apellidos");
        nombres = getIntent().getStringExtra("nombres");
        edad = getIntent().getStringExtra("edad");
        numero = getIntent().getStringExtra("numero");
        dni = getIntent().getStringExtra("dni");

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Title"),SELECT_IMAGE_CODE);
            }
        });

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registro2Activity.this, MapsActivityReg.class);
                startActivityForResult(i,100);
            }
        });

        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();

            }
        });

    }

    private void registrar() {
        ubicacion = tvUbicacion.getText().toString();
        descripcion = edtDescripcion.getText().toString();
        hayError = false;

        if (ubicacion.isEmpty()) {
            Toast.makeText(this, "Por favor seleccione una ubicación", Toast.LENGTH_SHORT).show();
            hayError = true;
        }
        if (pathimg == null) {
            pathimg = "";
        }
        if (descripcion.isEmpty()) {
            descripcion = "";
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
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("img",pathimg);
                editor.commit();

                Intent i = new Intent(Registro2Activity.this, Registro3Activity.class);
                i.putExtra("apellidos",apellidos);
                i.putExtra("nombres",nombres);
                i.putExtra("edad",edad);
                i.putExtra("numero",numero);
                i.putExtra("dni",dni);
                i.putExtra("ubicacion",ubicacion);
                i.putExtra("ubilatitud",ubilatitud);
                i.putExtra("ubilongitud",ubilongitud);
                i.putExtra("descripcion",descripcion);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        },1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (resultCode == Activity.RESULT_OK){
                Bundle datos = data.getExtras();
                ubicacion = datos.getString("ubidescripcion");
                ubilatitud = datos.getString("latitud");
                ubilongitud = datos.getString("longitud");
                tvUbicacion.setText(ubicacion);
            }
        }
        if (requestCode == 1){
            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img_user.setScaleType(ImageView.ScaleType.CENTER_CROP);
                img_user.setImageBitmap(bitmap);
                pathimg = getStringImage(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private String getStringImage (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);
        byte[] imagenBytes = baos.toByteArray();
        return Base64.encodeToString(imagenBytes,Base64.DEFAULT);
    }


}