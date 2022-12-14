package com.ay.proyectopetisosalbergue.TabItems.Inicio;

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
import com.ay.proyectopetisosalbergue.Adapters.AlberguesAdapter;
import com.ay.proyectopetisosalbergue.Model.Albergues;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InicioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RecyclerView rvAlbergues;
    ArrayList<Albergues> alberguesList;
    RequestQueue requestQueue;
    AlberguesAdapter alberguesAdapter;
    JsonObjectRequest jsonObjectRequest;
    AdView adView;


    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        rvAlbergues = (RecyclerView) view.findViewById(R.id.rv_inicio_parent);
        rvAlbergues.setHasFixedSize(true);
        alberguesList = new ArrayList<>();
        adView = (AdView) view.findViewById(R.id.adView);
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();
        cargarAnuncio();



        return view;
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

    private void cargarWebService() {
        String url = Util.RUTA+"consultarAlbergues.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        rvAlbergues.setLayoutManager(manager);
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
            alberguesAdapter = new AlberguesAdapter(alberguesList, this.getContext(), new AlberguesAdapter.ItemClickListener() {
                @Override
                public void onItemClick(Albergues albergues) {
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment fragment = new CategoriaAnimalesAlbergueFragment();
                    Bundle bundle = new Bundle();
                    int idAlbergue = albergues.getIdAlbergue();
                    String nombreAlbergue = albergues.getNombreAlbergue();
                    bundle.putInt("idAlbergue",idAlbergue);
                    bundle.putString("nombreAlbergue",nombreAlbergue);
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.root_frame,fragment).addToBackStack(null).commit();
                }
            });
            rvAlbergues.setAdapter(alberguesAdapter);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }

}