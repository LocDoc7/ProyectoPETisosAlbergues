package com.ay.proyectopetisosalbergue.TabItems.Opciones;

import android.os.Bundle;

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
import com.ay.proyectopetisosalbergue.Adapters.ConsejosAdapter;
import com.ay.proyectopetisosalbergue.Model.Consejos;
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsejosSaludFragment extends Fragment {
    RecyclerView rvConsejos;
    ArrayList<Consejos> consejosList;
    RequestQueue requestQueue;
    ConsejosAdapter consejosAdapter;
    JsonObjectRequest jsonObjectRequest;

    public ConsejosSaludFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consejos_salud, container, false);
        rvConsejos = (RecyclerView) view.findViewById(R.id.rv_consejosSalud);
        consejosList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultar_consejos_albergue.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                rvConsejos.setLayoutManager(manager);
                Consejos consejos = null;
                JSONArray json = response.optJSONArray("consulta");

                try {
                    for (int i = 0; i<json.length();i++){
                        consejos = new Consejos();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        consejos.setNomAlbergue(jsonObject.optString("albNombre"));
                        consejos.setIcAlbergue(jsonObject.optString("albImgLogo"));
                        consejos.setTituloConsejo(jsonObject.optString("tituloConsejo"));
                        consejos.setDescConsejo(jsonObject.optString("descConsejo"));
                        consejos.setImgConsejo(jsonObject.optString("imgConsejo"));
                        consejos.setVinculoConsejo(jsonObject.optString("vinculoConsejo"));
                        consejosList.add(consejos);

                    }
                    consejosAdapter = new ConsejosAdapter(getContext(),consejosList);
                    rvConsejos.setAdapter(consejosAdapter);

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
}