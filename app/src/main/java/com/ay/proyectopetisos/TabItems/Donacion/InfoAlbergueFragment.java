package com.ay.proyectopetisos.TabItems.Donacion;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.AlberguesAdapter;
import com.ay.proyectopetisos.Adapters.CanesAdapter;
import com.ay.proyectopetisos.Model.Albergues;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;

import static com.ay.proyectopetisos.Util.Util.RUTA;


public class InfoAlbergueFragment extends Fragment {
    TextView tv_cantDonaciones,tv_nomAlbergue,tv_descripcionAlbergue;
    ImageView img_albergue;
    String nomAlbergue,imgAlbergue,albergueCell,descAlbergue;
    CardView cdYape,cdBCP,wsp,phone;
    int cantDonacion,idAlbergue;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    Bitmap bitmapimg;

    public InfoAlbergueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_albergue, container, false);
        tv_cantDonaciones = view.findViewById(R.id.tv_canDonaciones_info);
        tv_nomAlbergue = view.findViewById(R.id.tv_nombreAlbergueCategoria);
        tv_descripcionAlbergue = view.findViewById(R.id.tv_desc_albergue_donacion);
        img_albergue = view.findViewById(R.id.img_albergueDonacionInfo);
        cdYape = view.findViewById(R.id.btn_yape);
        cdBCP = view.findViewById(R.id.btn_bcp);
        wsp = view.findViewById(R.id.btn_whatsapp);
        phone = view.findViewById(R.id.btn_call);
        requestQueue = Volley.newRequestQueue(getContext());
        Bundle bundle = this.getArguments();
        cantDonacion = bundle.getInt("cantDonaciones");
        nomAlbergue = bundle.getString("nombreAlbergue");
        idAlbergue = bundle.getInt("idAlbergue");
        imgAlbergue = bundle.getString("imgAlbergue");
        tv_cantDonaciones.setText(String.valueOf(cantDonacion));
        tv_nomAlbergue.setText(nomAlbergue);
        Glide.with(getContext()).load(String.valueOf(RUTA+imgAlbergue)).into(img_albergue);
        cargarWebService();
        cdBCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new DonacionesMediosDetalleBCPFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cantDonaciones",cantDonacion);
                bundle.putInt("idAlbergue",idAlbergue);
                bundle.putString("nombreAlbergue",nomAlbergue);
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootDonacion_frame,fragment).addToBackStack(null).commit();
            }
        });
        cdYape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new DonacionesMediosDetalleFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cantDonaciones",cantDonacion);
                bundle.putInt("idAlbergue",idAlbergue);
                bundle.putString("nombreAlbergue",nomAlbergue);
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootDonacion_frame,fragment).addToBackStack(null).commit();
            }
        });
        wsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWhatsappInstalled()){
                    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://api.whatsapp.com/send?phone=+51"+albergueCell+"&text=Hola. Vengo de la App Petisos, y quiero ayudar! :D"));
                    startActivity(i);
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+albergueCell));
                startActivity(i);
            }
        });

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarInfoAlbergueDonar.php?idAlbergue="+idAlbergue;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(0);
                        descAlbergue = jsonObject.optString("albDesc");
                        albergueCell = jsonObject.optString("albCell");

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

    private boolean isWhatsappInstalled(){
        PackageManager packageManager = getContext().getPackageManager();
        boolean whatsappIsntalled;
        try {
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappIsntalled = true;
        }catch (PackageManager.NameNotFoundException e){
            whatsappIsntalled  = false;
        }
        return whatsappIsntalled;
    }
}