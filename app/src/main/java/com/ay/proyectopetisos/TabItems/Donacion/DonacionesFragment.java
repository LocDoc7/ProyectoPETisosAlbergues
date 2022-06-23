package com.ay.proyectopetisos.TabItems.Donacion;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.AlberguesAdapter;
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;
import com.ay.proyectopetisos.TabItems.Inicio.CategoriaAnimalesAlbergueFragment;
import com.ay.proyectopetisos.Util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DonacionesFragment extends Fragment {
    AdView adView;
    RecyclerView rvAlberguesDonacion;
    TextView cantDonacionesUsuario;
    ArrayList<Albergues> alberguesList;
    RequestQueue requestQueue;
    AlberguesAdapter alberguesAdapter;
    JsonObjectRequest jsonObjectRequest;
    int idpersona,cantDonaciones;


    public DonacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donaciones, container, false);
        rvAlberguesDonacion = (RecyclerView) view.findViewById(R.id.rv_alberguesDonacion);
        cantDonacionesUsuario = view.findViewById(R.id.tv_canDonaciones);
        adView = (AdView) view.findViewById(R.id.adViewDonaciones);
        rvAlberguesDonacion.setHasFixedSize(true);
        alberguesList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences sp1 = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idpersona = sp1.getInt("idUsuario",0);
        cantDonaciones = sp1.getInt("cantDonaciones",0);
        cantDonacionesUsuario.setText(String.valueOf(cantDonaciones));
        cargarWebService();
        cargarAnuncio();
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarAlbergues.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                rvAlberguesDonacion.setLayoutManager(manager);
                Albergues albergues = null;
                JSONArray json = response.optJSONArray("albergues");

                try {
                    for (int i = 0; i<json.length();i++){
                        albergues = new Albergues();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        albergues.setIdAlbergue(jsonObject.optInt("idAlbergue"));
                        albergues.setNombreAlbergue(jsonObject.optString("albNombre"));
                        albergues.setCantPerritosAlbergue(jsonObject.optString("albCantAnimales"));
                        albergues.setCellAlbergue(jsonObject.optString("albCell"));
                        albergues.setImgAlbergue(jsonObject.optString("albImgLogo"));
                        albergues.setUbiAlbergue(jsonObject.optString("ubicacionDesc"));
                        alberguesList.add(albergues);

                    }
                    alberguesAdapter = new AlberguesAdapter(alberguesList, getContext(), new AlberguesAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(Albergues albergues) {
                            AppCompatActivity activity = (AppCompatActivity) getContext();
                            Fragment fragment = new InfoAlbergueFragment();
                            Bundle bundle = new Bundle();
                            String nombreAlbergue = albergues.getNombreAlbergue();
                            String imgAlbergue = albergues.getImgAlbergue();
                            int idAlbergue = albergues.getIdAlbergue();
                            bundle.putInt("cantDonaciones",cantDonaciones);
                            bundle.putInt("idAlbergue",idAlbergue);
                            bundle.putString("imgAlbergue",imgAlbergue);
                            bundle.putString("nombreAlbergue",nombreAlbergue);
                            fragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootDonacion_frame,fragment).addToBackStack(null).commit();
                        }
                    });
                    rvAlberguesDonacion.setAdapter(alberguesAdapter);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No existen albergues Registrados", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void cargarAnuncio() {
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}