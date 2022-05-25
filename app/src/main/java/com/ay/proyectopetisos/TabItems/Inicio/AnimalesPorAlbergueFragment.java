package com.ay.proyectopetisos.TabItems.Inicio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.CanesAdapter;
import com.ay.proyectopetisos.Adapters.CategoriaCanesAdapter;
import com.ay.proyectopetisos.Model.Canes;
import com.ay.proyectopetisos.Model.CategoriaCanes;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnimalesPorAlbergueFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView tvNombreAlbergue,tvCategoria,tvCantidad;
    String nombreAlbergue,nombreCategoria;
    int idAlbergue,idCategoria,cantCanes;
    RecyclerView rvCaninos;
    ArrayList<Canes> canesArrayList;
    RequestQueue requestQueue;
    CanesAdapter canesAdapter;
    JsonObjectRequest jsonObjectRequest;


    public AnimalesPorAlbergueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animales_por_albergue, container, false);
        Bundle bundle = this.getArguments();

        idAlbergue = bundle.getInt("idAlbergue");
        idCategoria = bundle.getInt("idCategoria");
        nombreAlbergue = bundle.getString("nombreAlbergue");
        nombreCategoria = bundle.getString("nombreCategoria");
        cantCanes = bundle.getInt("cantCaninos");
        tvNombreAlbergue = view.findViewById(R.id.tv_nombreAlbergueLista);
        tvCategoria = view.findViewById(R.id.tv_nombreCategoriaLista);
        tvCantidad = view.findViewById(R.id.tv_num_animales_albergue_lista);


        tvNombreAlbergue.setText(nombreAlbergue);
        tvCantidad.setText(String.valueOf(cantCanes));
        tvCategoria.setText(nombreCategoria);

        rvCaninos = (RecyclerView) view.findViewById(R.id.rv_lista_animales_albergue);
        rvCaninos.setHasFixedSize(true);
        canesArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarCanesAlbergue.php?idalbergue="+idAlbergue+"&idcategoria="+idCategoria;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        rvCaninos.setLayoutManager(manager);
        Canes canes = null;
        JSONArray json = response.optJSONArray("caninos");

        try {
            for (int i = 0; i<json.length();i++){
                canes = new Canes();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                canes.setIdCanino(jsonObject.optInt("IdCanino"));
                canes.setNombreCanino(jsonObject.optString("canNombre"));
                canes.setHistoriaCanino(jsonObject.optString("canHistoria"));
                canes.setImgCanino(jsonObject.optString("canImg"));
                canes.setSexoCanino(jsonObject.optString("canSexo"));
                canes.setEdadCanino(jsonObject.optString("canEdad"));
                canes.setRazaCanino(jsonObject.optString("canRaza"));
                canes.setEstadoCanino(jsonObject.optString("canEstado"));
                canes.setIdAlbergue(idAlbergue);
                canes.setIdCategoria(idCategoria);
                canesArrayList.add(canes);

            }
            canesAdapter = new CanesAdapter(canesArrayList, this.getContext(), new CanesAdapter.onClick() {
                @Override
                public void onClick(Canes canes) {
                    /*Toast.makeText(getContext(), "Visita", Toast.LENGTH_SHORT).show();*/
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment fragment = new RegistrarVisitaFragment();
                    Bundle bundle = new Bundle();
                    String nomAlbergue = nombreAlbergue;
                    String nomCanino = canes.getNombreCanino();
                    String sexoCanino = canes.getSexoCanino();
                    String edadCanino = canes.getEdadCanino();
                    String imgCanino  =canes.getImgCanino();
                    int idCanino = canes.getIdCanino();
                    bundle.putString("imgCanino",imgCanino);
                    bundle.putString("nomAlbergue",nomAlbergue);
                    bundle.putString("nomCanino",nomCanino);
                    bundle.putString("sexoCanino",sexoCanino);
                    bundle.putString("edadCanino",edadCanino);
                    bundle.putInt("idCanino",idCanino);
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.root_frame,fragment).addToBackStack(null).commit();
                }
            });
            rvCaninos.setAdapter(canesAdapter);

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