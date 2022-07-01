package com.ay.proyectopetisos.TabItems.Donacion;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
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
import com.ay.proyectopetisos.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DonacionesMediosDetalleBCPFragment extends Fragment {
    TextView tvcantDonaciones,tvNombreAlbergue,tvNumeroCuenta,tvNumeroCCI;
    ImageButton btnCopyCuenta,btnCopyCCI;
    Button btn_donacion_finalizada;
    String albergueNombre,numCuenta,numCCI;
    int idAlbergue,cantDonacion;
    RequestQueue requestQueue;
    Dialog Mydialog;
    MediaPlayer mediaPlayer;
    JsonObjectRequest jsonObjectRequest;

    public DonacionesMediosDetalleBCPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donaciones_medios_detalle_b_c_p, container, false);
        tvcantDonaciones = view.findViewById(R.id.tv_canDonaciones_DetalleBCP);
        tvNombreAlbergue = view.findViewById(R.id.tv_nombreAlbergueDonacionDetalleBCP);
        tvNumeroCuenta = view.findViewById(R.id.tv_numeroCuenta);
        tvNumeroCCI = view.findViewById(R.id.tv_numeroCCI);
        btnCopyCuenta = view.findViewById(R.id.btn_copyCuenta);
        btnCopyCCI = view.findViewById(R.id.btn_copyCCI);
        btn_donacion_finalizada = view.findViewById(R.id.btn_donacion_finalizada_bcp);
        requestQueue = Volley.newRequestQueue(getContext());
        Bundle bundle = this.getArguments();
        cantDonacion = bundle.getInt("cantDonaciones");
        albergueNombre = bundle.getString("nombreAlbergue");
        idAlbergue = bundle.getInt("idAlbergue");
        cargarWebService();
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.success);
        tvcantDonaciones.setText(String.valueOf(cantDonacion));
        tvNombreAlbergue.setText(albergueNombre);

        btn_donacion_finalizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDialogGracias();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new DonacionesFragment();
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = 0;i < fm.getBackStackEntryCount();++i){
                    fm.popBackStack();
                }
                fm.beginTransaction().replace(R.id.rootDonacion_frame,fragment).commit();
            }
        });
        btnCopyCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView",tvNumeroCuenta.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copiado!", Toast.LENGTH_SHORT).show();
            }
        });
        btnCopyCCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView",tvNumeroCCI.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copiado!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void cargarWebService() {
        String url = Util.RUTA+"consultarMedioBcp.php?idAlbergue="+idAlbergue;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    numCuenta = jsonObject.optString("donNumCuenta");
                    numCCI = jsonObject.optString("donCci");

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
                tvNumeroCuenta.setText(numCuenta);
                tvNumeroCCI.setText(numCCI);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void cargarDialogGracias() {
        mediaPlayer.start();
        Mydialog = new Dialog(getContext());
        Mydialog.setContentView(R.layout.dialog_gracias);
        ((TextView) Mydialog.findViewById(R.id.tvTittleGracias)).setText("¡Gracias por tu apoyo!");
        ((TextView) Mydialog.findViewById(R.id.tvMensajeGracias)).setText("Muchas gracias por ayudarnos a seguir ayudando, tu donación es muy bien recibida por todos nuestros peluditos en el albergue");
        ((Button) Mydialog.findViewById(R.id.btnConfirmarDialogGracias)).setText("Continuar");
        Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Mydialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Mydialog.findViewById(R.id.btnConfirmarDialogGracias).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydialog.dismiss();
            }
        });
        Mydialog.show();
    }
}