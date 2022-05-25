package com.ay.proyectopetisos.TabItems.Tienda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.AlberguesAdapter;
import com.ay.proyectopetisos.Adapters.ProductosAdapter;
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.Model.Productos;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Donacion.InfoAlbergueFragment;
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TiendaFragment extends Fragment {
    GridView gridViewProductos;
    RecyclerView rv_productos;
    TextView catCollaresUsuario;
    int cantCollares;
    ArrayList<Productos> productosArrayList;
    RequestQueue requestQueue;
    ProductosAdapter productosAdapter;
    JsonObjectRequest jsonObjectRequest;
    LinearLayoutManager manager;
    public TiendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);
        SharedPreferences sp1 = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        cantCollares = sp1.getInt("cantCollares",0);
        //gridViewProductos = (GridView) view.findViewById(R.id.gridView);
        rv_productos = (RecyclerView) view.findViewById(R.id.rv_productos_tienda);
        rv_productos.setHasFixedSize(true);
        catCollaresUsuario = view.findViewById(R.id.tv_canMonedas);
        productosArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        catCollaresUsuario.setText(String.valueOf(cantCollares));
        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarProductos.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                manager = new GridLayoutManager(getContext(),2);
                rv_productos.setLayoutManager(manager);
                Productos productos = null;
                JSONArray json = response.optJSONArray("consulta");

                try {
                    for (int i = 0; i<json.length();i++){
                        productos = new Productos();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        productos.setIdProducto(jsonObject.optInt("IdProducto"));
                        productos.setProNombre(jsonObject.optString("proNombre"));
                        productos.setProDescripcion(jsonObject.optString("proDescripcion"));
                        productos.setProPrecioReal(Float.parseFloat(jsonObject.optString("proPrecioReal")));
                        productos.setProPrecioCollares(Float.parseFloat(jsonObject.optString("proPrecioCollares")));
                        productos.setImgProducto(jsonObject.optString("proImagen"));
                        productos.setId_Albergue(jsonObject.optInt("IdAlbergue"));
                        productosArrayList.add(productos);

                    }
                    productosAdapter = new ProductosAdapter(productosArrayList, getContext(), new ProductosAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(Productos productos) {
                            Toast.makeText(getContext(), "Click en:"+productos.getIdProducto(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    rv_productos.setAdapter(productosAdapter);
                    /*gridViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "Click en:", Toast.LENGTH_SHORT).show();
                        }
                    });*/

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