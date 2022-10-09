package com.ay.proyectopetisosalbergue.TabItems.Inicio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.ay.proyectopetisosalbergue.R;
import com.ay.proyectopetisosalbergue.Util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UbicacionAlbergueFragment extends Fragment {
    TextView tv_nomAlbergue;
    String nombreAlbergue,ubi_desc,ubi_lat,ubi_long;
    int idCanino;
    SupportMapFragment supportMapFragment;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    Button btn_LoTengo;
    ImageButton btn_comoLlegar;

    public UbicacionAlbergueFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ubicacion_albergue, container, false);

        Bundle bundle = this.getArguments();
        nombreAlbergue = bundle.getString("nombreAlbergue");
        idCanino = bundle.getInt("idCanino");
        requestQueue = Volley.newRequestQueue(getContext());
        tv_nomAlbergue = view.findViewById(R.id.tv_nombreAlbergueUbicacion);
        btn_comoLlegar = view.findViewById(R.id.btn_comoLlegar);
        btn_LoTengo = view.findViewById(R.id.btn_lotengo);
        tv_nomAlbergue.setText(nombreAlbergue);
        cargarWebService();

        btn_LoTengo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new InicioFragment();
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = 0;i < fm.getBackStackEntryCount();++i){
                    fm.popBackStack();
                }
                fm.beginTransaction().replace(R.id.root_frame,fragment).commit();
            }
        });
        btn_comoLlegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+ubi_lat+","+ubi_long+"&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getContext().getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),callback);

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarUbicacionVisita.php?id_canino="+idCanino;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(0);
                        ubi_desc = jsonObject.optString("ubiDescripcion");
                        ubi_lat = jsonObject.optString("ubiLatitud");
                        ubi_long = jsonObject.optString("ubiLongitud");
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
                supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_albergue);

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(Double.parseDouble(ubi_lat),Double.parseDouble(ubi_long));
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(ubi_desc)
                        .icon(bitmapDescriptorFromVector(getContext(),R.drawable.ic_ubi_visita)));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectirResId){
        Drawable vectorDrawable  = ContextCompat.getDrawable(context,vectirResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}