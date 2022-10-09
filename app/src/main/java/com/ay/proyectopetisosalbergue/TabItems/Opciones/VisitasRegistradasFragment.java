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
import com.ay.proyectopetisosalbergue.Adapters.VisitasRegistradasAdapter;
import com.ay.proyectopetisosalbergue.Model.VisitasRegistradas;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisitasRegistradasFragment extends Fragment {
    RecyclerView rvVisitasReg;
    ArrayList<VisitasRegistradas> visitasRegistradasList;
    RequestQueue requestQueue;
    VisitasRegistradasAdapter visitasAdapter;
    JsonObjectRequest jsonObjectRequest;
    int idPersona;

    public VisitasRegistradasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visitas_registradas, container, false);
        rvVisitasReg = (RecyclerView) view.findViewById(R.id.rv_visitasRegistradas);
        visitasRegistradasList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences sp1 = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idPersona = sp1.getInt("idUsuario",0);
        cargarWebService();
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarVisitasRegistradas.php?id_persona="+idPersona;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                rvVisitasReg.setLayoutManager(manager);
                VisitasRegistradas visitasRegistradas = null;
                JSONArray json = response.optJSONArray("consulta");

                try {
                    for (int i = 0; i<json.length();i++){
                        visitasRegistradas = new VisitasRegistradas();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        visitasRegistradas.setIdVisita(jsonObject.optInt("IdVisita"));
                        visitasRegistradas.setIdCanino(jsonObject.optInt("IdCanino"));
                        visitasRegistradas.setNomAlbergue(jsonObject.optString("albNombre"));
                        visitasRegistradas.setCaninoNombre(jsonObject.optString("canNombre"));
                        visitasRegistradas.setCaninoImg(jsonObject.optString("canImg"));
                        visitasRegistradas.setVisitaFecha(jsonObject.optString("visFecha"));
                        visitasRegistradas.setVisitaHora(jsonObject.optString("visHora"));
                        visitasRegistradas.setVisitaEstado(jsonObject.optString("visEstado"));
                        visitasRegistradasList.add(visitasRegistradas);

                    }
                    visitasAdapter = new VisitasRegistradasAdapter(visitasRegistradasList, getContext(), new VisitasRegistradasAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(VisitasRegistradas visitasRegistradas) {
                            AppCompatActivity activity = (AppCompatActivity) getContext();
                            Fragment fragment = new EditarVisitaFragment();
                            Bundle bundle = new Bundle();
                            int idVisita = visitasRegistradas.getIdVisita();
                            int idCanino = visitasRegistradas.getIdCanino();
                            String nomAlbergue = visitasRegistradas.getNomAlbergue();
                            String nomCanino = visitasRegistradas.getCaninoNombre();
                            String visFecha = visitasRegistradas.getVisitaFecha();
                            String visHora = visitasRegistradas.getVisitaHora();
                            String visEstado = visitasRegistradas.getVisitaEstado();
                            String canImg = visitasRegistradas.getCaninoImg();
                            bundle.putInt("idVisita",idVisita);
                            bundle.putInt("idCanino",idCanino);
                            bundle.putString("nomAlbergue",nomAlbergue);
                            bundle.putString("nomCanino",nomCanino);
                            bundle.putString("visFecha",visFecha);
                            bundle.putString("visHora",visHora);
                            bundle.putString("visEstado",visEstado);
                            bundle.putString("canImg",canImg);
                            fragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                        }
                    });
                    rvVisitasReg.setAdapter(visitasAdapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Aún no tienen visitas registradas", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}