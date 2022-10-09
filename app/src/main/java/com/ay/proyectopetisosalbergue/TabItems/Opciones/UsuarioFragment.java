package com.ay.proyectopetisosalbergue.TabItems.Opciones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.ay.proyectopetisosalbergue.MapsActivityReg;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ay.proyectopetisosalbergue.Util.Util.RUTA;

public class UsuarioFragment extends Fragment {
    CircleImageView img_user;
    ImageView btn_edit_img_user;
    EditText edtApellidos,edtNombres,edtDescripcion;
    TextView tvUbicacion;
    ImageButton btnMap;
    Button btnGuardar;
    final static int SELECT_IMAGE_CODE = 1;
    final static int SELECT_UBI_CODE = 100;
    boolean hayError,hayCambios;
    int id_persona;
    String lat_user,lng_user,ubidesc,pathimg = "",apellidos_user,nombres_user,desc_User,ap_user,nom_user,desc_user,img_user_consulta,ubi_desc;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    Uri uri;
    Bitmap bitmap;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        SharedPreferences sp1 = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        id_persona = sp1.getInt("idUsuario",0);
        img_user = view.findViewById(R.id.img_user_perfil);
        btn_edit_img_user = view.findViewById(R.id.btn_edi_img_perfil);
        edtApellidos = view.findViewById(R.id.edt_ap_perfil);
        edtNombres = view.findViewById(R.id.edt_nom_perfil);
        edtDescripcion = view.findViewById(R.id.edt_desc_perfil);
        tvUbicacion = view.findViewById(R.id.tv_ubi_perfil);
        btnMap = view.findViewById(R.id.btn_mapa_ubicacion_perfil);
        btnGuardar = view.findViewById(R.id.btn_guardar_cambios_perfil);
        requestQueue = Volley.newRequestQueue(getContext());
        cargarDatos();
        btn_edit_img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Title"),SELECT_IMAGE_CODE);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MapsActivityReg.class);
                startActivityForResult(i,SELECT_UBI_CODE);
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCambiosRealizados();
            }
        });
        return view;
    }

    private void validarCambiosRealizados() {
        btnGuardar.setEnabled(false);
        hayError = false;
        ap_user = edtApellidos.getText().toString();
        nom_user = edtNombres.getText().toString();
        desc_user = edtDescripcion.getText().toString();
        ubi_desc = tvUbicacion.getText().toString();
        if (!hayCambios && ap_user.equals(apellidos_user) && nom_user.equals(nombres_user) && desc_user.equals(desc_User)){
            Toast.makeText(getContext(), "No se ha realizado ningun cambio", Toast.LENGTH_SHORT).show();
            hayError = true;
            btnGuardar.setEnabled(true);
        }
        if (!hayError){
            actualizarDatos();
        }
    }

    private void actualizarDatos() {
        String url = Util.RUTA + "actualizar_datos_usuario.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                fm.popBackStackImmediate();
                btnGuardar.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al actualizar" + error.toString(), Toast.LENGTH_SHORT).show();
                btnGuardar.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("id_persona",String.valueOf(id_persona));
                params.put("per_apellido",ap_user);
                params.put("per_nombre",nom_user);
                params.put("per_desc",desc_user);
                params.put("per_lat",lat_user);
                params.put("per_lng",lng_user);
                params.put("ubi_desc",ubi_desc);
                params.put("rutaimg",img_user_consulta);
                params.put("pathimg",pathimg);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void cargarDatos() {
        String url = Util.RUTA+"consultar_datos_usuario.php?id_persona="+id_persona;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    img_user_consulta = jsonObject.optString("perImgPerfil");
                    if (!img_user_consulta.isEmpty()){
                        Glide.with(getContext()).load(String.valueOf(RUTA+img_user_consulta)).apply(RequestOptions.skipMemoryCacheOf(true)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(img_user);
                    }
                    apellidos_user = jsonObject.optString("perApellidos");
                    edtApellidos.setText(apellidos_user);
                    nombres_user = jsonObject.optString("perNombres");
                    edtNombres.setText(nombres_user);
                    desc_User = jsonObject.optString("perDescripcion");
                    edtDescripcion.setText(desc_User);
                    lat_user =(jsonObject.optString("ubiLatitud"));
                    lng_user = (jsonObject.optString("ubiLongitud"));
                    ubi_desc = jsonObject.optString("ubiDescripcion");
                    tvUbicacion.setText(ubi_desc);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (resultCode == Activity.RESULT_OK){
                Bundle datos = data.getExtras();
                ubidesc = datos.getString("ubidescripcion");
                lat_user = datos.getString("latitud");
                lng_user = datos.getString("longitud");
                tvUbicacion.setText(ubidesc);
                hayCambios = true;
            }
        }
        if (requestCode == 1) {
            if (data != null) {
                uri = data.getData();
                if (uri != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        img_user.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        img_user.setImageBitmap(bitmap);
                        pathimg = getStringImage(bitmap);
                        hayCambios = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private String getStringImage (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10,baos);
        byte[] imagenBytes = baos.toByteArray();
        return Base64.encodeToString(imagenBytes,Base64.DEFAULT);
    }
}