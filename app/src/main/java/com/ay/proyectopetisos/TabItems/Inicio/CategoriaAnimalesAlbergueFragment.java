package com.ay.proyectopetisos.TabItems.Inicio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.AlberguesAdapter;
import com.ay.proyectopetisos.Adapters.CategoriaCanesAdapter;
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.Model.CategoriaCanes;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriaAnimalesAlbergueFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView tvNombreAlbergue;
    String nombreAlbergue;
    int idAlbergue;
    RecyclerView rvCategorias;
    ArrayList<CategoriaCanes> categoriaCanesArrayList;
    RequestQueue requestQueue;
    CategoriaCanesAdapter categoriaCanesAdapter;
    JsonObjectRequest jsonObjectRequest;


    public CategoriaAnimalesAlbergueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categoria_animales_albergue, container, false);
        Bundle bundle = this.getArguments();
        tvNombreAlbergue = view.findViewById(R.id.tv_nombreAlbergueCategoria);
        idAlbergue = bundle.getInt("idAlbrgue");
        nombreAlbergue = bundle.getString("nombreAlbergue");
        tvNombreAlbergue.setText(nombreAlbergue);
        rvCategorias = (RecyclerView) view.findViewById(R.id.rv_categoria_animales);
        rvCategorias.setHasFixedSize(true);
        categoriaCanesArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarCategoriaPerros.php?idalbergue="+idAlbergue;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        rvCategorias.setLayoutManager(manager);
        CategoriaCanes categoriaCanes = null;
        JSONArray json = response.optJSONArray("categorias");

        try {
            for (int i = 0; i<json.length();i++){
                categoriaCanes = new CategoriaCanes();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                categoriaCanes.setIdCategoria(jsonObject.optInt("IdCategoriaCan"));
                categoriaCanes.setDescCategoria(jsonObject.optString("catDesc"));
                categoriaCanes.setRangoCategoria(jsonObject.optString("catRangoEdad"));
                categoriaCanes.setImgCategoria(jsonObject.optString("catImagen"));
                categoriaCanes.setCantidadCanesCategoria(jsonObject.optInt("cantCanes"));
                categoriaCanesArrayList.add(categoriaCanes);

            }
            categoriaCanesAdapter = new CategoriaCanesAdapter(categoriaCanesArrayList, this.getContext(), new CategoriaCanesAdapter.ItemClickListener() {
                @Override
                public void onItemClick(CategoriaCanes categoriaCanes) {
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment fragment = new AnimalesPorAlbergueFragment();
                    Bundle bundle = new Bundle();
                    String nombreCategoria = categoriaCanes.getDescCategoria();
                    int cantCaninos = categoriaCanes.getCantidadCanesCategoria();
                    int idCategoria = categoriaCanes.getIdCategoria();
                    bundle.putInt("idAlbergue",idAlbergue);
                    bundle.putInt("idCategoria",idCategoria);
                    bundle.putInt("cantCaninos",cantCaninos);
                    bundle.putString("nombreAlbergue",nombreAlbergue);
                    bundle.putString("nombreCategoria",nombreCategoria);
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.root_frame,fragment).addToBackStack(null).commit();
                }
            });
            rvCategorias.setAdapter(categoriaCanesAdapter);

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