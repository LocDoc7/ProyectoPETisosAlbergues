package com.ay.proyectopetisos.TabItems.Opciones;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ay.proyectopetisos.Adapters.VisitasRegistradasAdapter;
import com.ay.proyectopetisos.Model.VisitasRegistradas;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class CodigosIntercambioDetalleFragment extends Fragment {
    ImageView imgProducto,imgQRProducto,icPrecio;
    TextView tvNomProducto,tvCantidad,tvFechaValida,tvPrecio,tvNomAlbergue;
    ImageButton btnComoLlegar;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    int idVenta,det_cantidad,modo_pago;
    double det_precio;
    String imgProductoIntercambio,proNombre,albergueNombre,fechaValida,lat,lng,proEstado;

    public CodigosIntercambioDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_codigos_intercambio_detalle, container, false);
        Bundle bundle = this.getArguments();
        idVenta = bundle.getInt("idVenta");
        imgProductoIntercambio = bundle.getString("imgProductoIntercambio");
        proNombre = bundle.getString("proNombre");
        albergueNombre = bundle.getString("albergueNombre");
        fechaValida = bundle.getString("fechaValida");
        requestQueue = Volley.newRequestQueue(getContext());
        imgProducto = view.findViewById(R.id.img_producto_intercambio_codigo_detalle);
        imgQRProducto = view.findViewById(R.id.img_qr_codigo_intercambiado_detalle);
        icPrecio = view.findViewById(R.id.ic_precio_producto_intercambiado);
        tvNomProducto = view.findViewById(R.id.tvNomProducto_intercambio_detalle);
        tvCantidad = view.findViewById(R.id.tv_cantidad_producto_intercambiado);
        tvFechaValida = view.findViewById(R.id.tv_fecha_valida_codigo_intercambio_detalle);
        tvPrecio = view.findViewById(R.id.tv_precio_producto_intercambiado);
        tvNomAlbergue = view.findViewById(R.id.tvNombreAlbergue_producto_intercambio_detalle);
        btnComoLlegar = view.findViewById(R.id.btn_comollegar_albergue_intercambiado_detalle);

        cargarWebService();

        btnComoLlegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+lat+","+lng+"&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getContext().getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultar_detalle_producto_intercambiado.php?id_venta="+idVenta;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {

                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    tvNomProducto.setText(proNombre);
                    det_cantidad = jsonObject.optInt("detCantidad");
                    tvCantidad.setText(String.valueOf(det_cantidad));
                    det_precio = jsonObject.optDouble("detPrecio");
                    modo_pago = jsonObject.optInt("idModoPago");
                    tvPrecio.setText(String.valueOf(det_precio));
                    tvNomAlbergue.setText(albergueNombre);
                    tvFechaValida.setText(fechaValida);
                    lat = String.valueOf(jsonObject.optString("ubiLatitud"));
                    lng = String.valueOf(jsonObject.optString("ubiLongitud"));
                    proEstado = String.valueOf(jsonObject.optString("venEstado"));
                    Glide.with(getContext()).load(String.valueOf(RUTA+imgProductoIntercambio)).into(imgProducto);

                    if (modo_pago == 1){
                        icPrecio.setBackgroundResource(R.drawable.ic_soles);
                    }else if (modo_pago == 2){
                        icPrecio.setBackgroundResource(R.drawable.collar);
                    }

                    try {
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.encodeBitmap(idVenta+"|"+proEstado+"|"+proNombre+"|"+det_cantidad+"|"+det_precio, BarcodeFormat.QR_CODE,750,750);
                        imgQRProducto.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

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