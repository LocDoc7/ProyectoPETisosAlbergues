package com.ay.proyectopetisosalbergue.TabItems.Opciones;

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
import com.ay.proyectopetisosalbergue.Adapters.CodigoIntercambioAdapter;
import com.ay.proyectopetisosalbergue.Model.CodigoIntercambio;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CodigosIntercambioFragment extends Fragment {
    RecyclerView rvCodigoIntercambio;
    ArrayList<CodigoIntercambio> codigoIntercambiosList;
    RequestQueue requestQueue;
    CodigoIntercambioAdapter codigoIntercambioAdapter;
    JsonObjectRequest jsonObjectRequest;
    int idPersona;

    public CodigosIntercambioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_codigos_intercambio, container, false);
        rvCodigoIntercambio = (RecyclerView) view.findViewById(R.id.rv_codigosIntercambio);
        codigoIntercambiosList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences sp1 = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idPersona = sp1.getInt("idUsuario",0);
        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultar_lista_codigos_intercambio.php?id_persona="+idPersona;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                rvCodigoIntercambio.setLayoutManager(manager);
                CodigoIntercambio codigoIntercambio = null;
                JSONArray json = response.optJSONArray("consulta");

                try {
                    for (int i = 0; i<json.length();i++){
                        codigoIntercambio = new CodigoIntercambio();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        codigoIntercambio.setIdVenta(jsonObject.optInt("IdVenta"));
                        codigoIntercambio.setImgProductoIntercambio(jsonObject.optString("proImagen"));
                        codigoIntercambio.setNomProductoIntercambio(jsonObject.optString("proNombre"));
                        codigoIntercambio.setNomAlbergueIntercambio(jsonObject.optString("albNombre"));
                        codigoIntercambio.setFechaValidaProductoIntercambio(jsonObject.optString("venFecha"));
                        codigoIntercambiosList.add(codigoIntercambio);

                    }
                    codigoIntercambioAdapter = new CodigoIntercambioAdapter(codigoIntercambiosList, getContext(), new CodigoIntercambioAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(CodigoIntercambio codigoIntercambio) {
                            AppCompatActivity activity = (AppCompatActivity) getContext();
                            Fragment fragment = new CodigosIntercambioDetalleFragment();
                            Bundle bundle = new Bundle();
                            int idVenta = codigoIntercambio.getIdVenta();
                            String imgProductoIntercambio = codigoIntercambio.getImgProductoIntercambio();
                            String proNombre = codigoIntercambio.getNomProductoIntercambio();
                            String albergueNombre = codigoIntercambio.getNomAlbergueIntercambio();
                            String fechaValida = codigoIntercambio.getFechaValidaProductoIntercambio();
                            bundle.putInt("idVenta", idVenta);
                            bundle.putString("imgProductoIntercambio", imgProductoIntercambio);
                            bundle.putString("proNombre", proNombre);
                            bundle.putString("albergueNombre", albergueNombre);
                            bundle.putString("fechaValida", fechaValida);
                            fragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame, fragment).addToBackStack(null).commit();
                        }
                    });

                    rvCodigoIntercambio.setAdapter(codigoIntercambioAdapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Sin resultados", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}