package com.ay.proyectopetisos.TabItems.Tienda;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.ProductosAdapter;
import com.ay.proyectopetisos.Model.Productos;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.InicioFragment;
import com.ay.proyectopetisos.Util.Util;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class IntercambiarProductoFragment extends Fragment {
    TextView tvCantCollaresIntercambio,tvNombreProducto,tvPrecioCollar,tvDescripcionProducto,tvPrecioSoles,tvNombAlbergue,tvSubTotal,tvTotal;
    ImageView imgProducto,icPrecioTotal,icPrecioSubtotal;
    ImageButton btnComoLlegar;
    Spinner spCantidad;
    Button btnSiguienteIntercambio;
    CheckBox chCollares,chSoles;
    int idProducto,idAlbergue,cantCollares,cantidadProducto=1,modoPago=2,idPersona;
    float precioCollares,precioSoles, PreciosubTotal,PrecioTotal,costoDiferencial=0;
    private boolean precioTipoCollares=true;
    String nomProducto,descProducto,proImg,lat,lng,fechaVencimiento;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    Dialog Mydialog;

    public IntercambiarProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intercambiar_producto, container, false);
        SharedPreferences sp1 = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idPersona = sp1.getInt("idUsuario",0);

        Bundle bundle = this.getArguments();
        cantCollares = bundle.getInt("canCollares");
        nomProducto = bundle.getString("nomProducto");
        descProducto = bundle.getString("descProducto");
        proImg = bundle.getString("proImg");
        precioCollares = bundle.getFloat("preCollares");
        precioSoles = bundle.getFloat("preReal");
        idAlbergue = bundle.getInt("idAlbergue");
        idProducto = bundle.getInt("idProducto");
        requestQueue = Volley.newRequestQueue(getContext());

        tvDescripcionProducto = view.findViewById(R.id.tvDescProducto_intercambio);
        tvCantCollaresIntercambio = view.findViewById(R.id.tv_canMonedasIntercambio);
        tvNombreProducto = view.findViewById(R.id.tvNomProducto_intercambio);
        tvPrecioCollar = view.findViewById(R.id.tv_precio_collares_intercambio);
        tvPrecioSoles = view.findViewById(R.id.tv_precio_soles_intercambio);
        tvNombAlbergue = view.findViewById(R.id.tvNombreAlbergue_intercambio);
        tvSubTotal = view.findViewById(R.id.tv_precio_subtotal);
        tvTotal = view.findViewById(R.id.tv_precio_total);
        imgProducto = view.findViewById(R.id.img_producto_intercambio);
        icPrecioTotal = view.findViewById(R.id.ic_precio_total);
        icPrecioSubtotal = view.findViewById(R.id.ic_precio_subtotal);
        btnComoLlegar = view.findViewById(R.id.btn_comollegar_albergue_intercambio);
        spCantidad = view.findViewById(R.id.sp_cant);
        btnSiguienteIntercambio = view.findViewById(R.id.btn_siguiente_intercambio);
        chCollares = view.findViewById(R.id.check_collares);
        chSoles = view.findViewById(R.id.check_soles);
        tvCantCollaresIntercambio.setText(String.valueOf(cantCollares));
        cargarDatosProducto();

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
        chCollares.setChecked(true);
        chSoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chCollares.setChecked(false);
                precioTipoCollares = false;
                modoPago = 1;
                calcularPrecios();
            }
        });
        chCollares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chSoles.setChecked(false);
                precioTipoCollares = true;
                modoPago = 2;
                calcularPrecios();
            }
        });
        spCantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cantidadProducto = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
                calcularPrecios();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSiguienteIntercambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarCantCollares();
            }
        });

        //calcularPrecios();

        return view;
    }

    private void comprobarCantCollares() {
        if (precioTipoCollares){
            if (cantCollares<PrecioTotal){
                Toast.makeText(getContext(), "Ups!. No cuenta con collares suficientes :C", Toast.LENGTH_SHORT).show();
            }else {
                registrarIntercambioProducto();
                costoDiferencial = PrecioTotal;
                //cargarDialog();
            }
        }else{
            registrarIntercambioProducto();
            //cargarDialog();
        }
    }

    private void registrarIntercambioProducto() {
        float collaresRestantes = cantCollares - costoDiferencial;
        String url = Util.RUTA+"registrarIntercambioProducto.php?id_persona="+idPersona+"&id_modo_pago="+modoPago+"&cant_pro="+cantidadProducto+"&precio_total="+PrecioTotal+"&id_producto="+idProducto+"&collares_restantes="+collaresRestantes;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    fechaVencimiento = jsonObject.optString("venFecha");
                    SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat o = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = i.parse(fechaVencimiento);
                        fechaVencimiento = o.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int idVenta = jsonObject.optInt("IdVenta");
                    String venEstado = jsonObject.optString("venEstado");
                    String proNombre = jsonObject.optString("proNombre");
                    int detCantidad = jsonObject.optInt("detCantidad");
                    double detPrecio = jsonObject.optDouble("detPrecio");
                    cargarDialog(idVenta,venEstado,proNombre,detCantidad,detPrecio,collaresRestantes);
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

    private void cargarDialog(int id_Venta,String ven_Estado,String pro_Nombre,int det_cantidad,double det_precio,float collaresRest) {
        SharedPreferences preferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("cantCollares",(int) collaresRest);
        editor.commit();
        Mydialog = new Dialog(getContext());
        Mydialog.setContentView(R.layout.dialog_producto_intercambiado);
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(id_Venta+"|"+ven_Estado+"|"+pro_Nombre+"|"+det_cantidad+"|"+det_precio, BarcodeFormat.QR_CODE,750,750);
            ((ImageView) Mydialog.findViewById(R.id.img_qrProductoIntercambiado)).setImageBitmap(bitmap);
        }catch (Exception e){
          e.printStackTrace();
        }
        ((TextView) Mydialog.findViewById(R.id.tvNombreProductoIntercambiado)).setText(nomProducto);
        ((TextView) Mydialog.findViewById(R.id.tvCantidadProductoIntercambiado)).setText(String.valueOf(cantidadProducto));
        ((TextView) Mydialog.findViewById(R.id.tvFechaValidaProductoIntercambiado)).setText(fechaVencimiento);
        Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Mydialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Mydialog.findViewById(R.id.btnLoTengoQRIntercambio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new TiendaFragment();
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = 0;i < fm.getBackStackEntryCount();++i){
                    fm.popBackStack();
                }
                fm.beginTransaction().replace(R.id.rootTienda_frame,fragment).commit();
                Mydialog.dismiss();
            }
        });
        Mydialog.show();
    }

    private void calcularPrecios() {
        if (precioTipoCollares){
            PreciosubTotal = precioCollares;
            PrecioTotal = PreciosubTotal * cantidadProducto;
            icPrecioSubtotal.setBackgroundResource(R.drawable.collar);
            icPrecioTotal.setBackgroundResource(R.drawable.collar);
        }else {
            PreciosubTotal = precioSoles;
            PrecioTotal = PreciosubTotal * cantidadProducto;
            icPrecioSubtotal.setBackgroundResource(R.drawable.ic_soles);
            icPrecioTotal.setBackgroundResource(R.drawable.ic_soles);
        }
        tvSubTotal.setText(String.valueOf(PreciosubTotal));
        tvTotal.setText(String.valueOf(PrecioTotal));
    }

    private void cargarDatosProducto() {
        String url = Util.RUTA+"consultarDatosProductoAlbergue.php?id_producto="+idProducto;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    tvNombAlbergue.setText(jsonObject.optString("albNombre"));
                    lat = (jsonObject.optString("ubiLatitud"));
                    lng = (jsonObject.optString("ubiLongitud"));
                    tvNombreProducto.setText(nomProducto);
                    tvDescripcionProducto.setText(descProducto);
                    Glide.with(getContext()).load(String.valueOf(RUTA+proImg)).into(imgProducto);
                    tvPrecioCollar.setText(String.valueOf(precioCollares));
                    tvPrecioSoles.setText(String.valueOf(precioSoles));
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.cantidad, R.layout.color_sp_layout);
                    adapter.setDropDownViewResource(R.layout.sp_dropdown_layout);
                    spCantidad.setAdapter(adapter);
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