package com.ay.proyectopetisos.TabItems.Notificaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.AlberguesAdapter;
import com.ay.proyectopetisos.Adapters.NotificacionesAdapter;
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.Model.Notificaciones;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Donacion.InfoAlbergueFragment;
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificacionesFragment extends Fragment {
    RecyclerView rvNotificaciones;
    ArrayList<Notificaciones> notificacionesArrayList;
    RequestQueue requestQueue;
    NotificacionesAdapter notificacionesAdapter;
    JsonObjectRequest jsonObjectRequest;
    int idpersona;

    public NotificacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        rvNotificaciones = view.findViewById(R.id.rv_notificaciones);
        notificacionesArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences sp1 = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idpersona = sp1.getInt("idUsuario",0);
        cargarWebService();
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultar_notificacion_usuario.php?id_persona="+idpersona;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                rvNotificaciones.setLayoutManager(manager);
                Notificaciones notificaciones = null;
                JSONArray json = response.optJSONArray("consulta");

                try {
                    for (int i = 0; i<json.length();i++){
                        notificaciones = new Notificaciones();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        notificaciones.setNotTituto(jsonObject.optString("notTitulo"));
                        notificaciones.setNotDescripcion(jsonObject.optString("notDescripcion"));
                        notificaciones.setNotHora(jsonObject.optString("notFechaHora"));
                        notificaciones.setImgAlbergueLogo(jsonObject.optString("albImgLogo"));
                        notificacionesArrayList.add(notificaciones);

                    }
                    notificacionesAdapter = new NotificacionesAdapter(notificacionesArrayList, getContext());
                    rvNotificaciones.setAdapter(notificacionesAdapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Aún no tienes notificaciones", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}