package com.ay.proyectopetisos.TabItems.Donacion;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.InicioFragment;
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class DonacionesMediosDetalleFragment extends Fragment {
    TextView tvcantDonaciones,tvNombreAlbergue,tvNumeroCell;
    ImageButton btnCopyNumero;
    Button btn_donacion_finalizada;
    ImageView imgQR;
    String albergueNombre,numeroCelular,imgQRYape;
    int idAlbergue,cantDonacion;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    Bitmap bitmapimg;

    public DonacionesMediosDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donaciones_medios_detalle, container, false);
        tvcantDonaciones = view.findViewById(R.id.tv_canDonaciones_Detalle_Yape);
        tvNombreAlbergue = view.findViewById(R.id.tv_nombreAlbergueDonacionDetalleYape);
        tvNumeroCell = view.findViewById(R.id.tv_numeroYape);
        btnCopyNumero = view.findViewById(R.id.btn_copyNumero);
        btn_donacion_finalizada =view.findViewById(R.id.btn_donacion_finalizada);
        imgQR = view.findViewById(R.id.img_qrdonacion);
        requestQueue = Volley.newRequestQueue(getContext());
        Bundle bundle = this.getArguments();
        cantDonacion = bundle.getInt("cantDonaciones");
        albergueNombre = bundle.getString("nombreAlbergue");
        idAlbergue = bundle.getInt("idAlbergue");
        cargarWebService();
        tvcantDonaciones.setText(String.valueOf(cantDonacion));
        tvNombreAlbergue.setText(albergueNombre);

        btn_donacion_finalizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new DonacionesFragment();
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = 0;i < fm.getBackStackEntryCount();++i){
                    fm.popBackStack();
                }
                fm.beginTransaction().replace(R.id.rootDonacion_frame,fragment).commit();
            }
        });

        btnCopyNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView",tvNumeroCell.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copiado!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarMedioYape.php?idAlbergue="+idAlbergue;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    imgQRYape = jsonObject.optString("donImg");
                    numeroCelular = jsonObject.optString("donCellYape");

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
                tvNumeroCell.setText(numeroCelular);
                new GetImageFromURL(imgQR).execute(String.valueOf(RUTA+imgQRYape));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public class GetImageFromURL extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public GetImageFromURL(ImageView imgv){
            this.imageView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmapimg = null;
            try {
                InputStream ist = new java.net.URL(urldisplay).openStream();
                bitmapimg = BitmapFactory.decodeStream(ist);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return bitmapimg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}