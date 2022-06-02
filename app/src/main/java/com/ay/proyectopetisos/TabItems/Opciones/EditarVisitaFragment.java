package com.ay.proyectopetisos.TabItems.Opciones;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.VisitasRegistradasAdapter;
import com.ay.proyectopetisos.Model.VisitasRegistradas;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.InicioFragment;
import com.ay.proyectopetisos.TabItems.Inicio.UbicacionAlbergueFragment;
import com.ay.proyectopetisos.Util.Util;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class EditarVisitaFragment extends Fragment {
    ImageView imgCaninoEdit;
    TextView tvNomCanino,tvSexoCanino,tvEdadCanino,tvNomAlbergue;
    EditText edtFecha,edtHora,edtComentario;
    ImageButton btnUbicacion;
    ProgressBar progressBar;
    int idVisita,idCanino;
    String nomAlbergue,nomCanino,visFecha,visHora,visEstado,canImgag,hora_visita,fecha_visita="";
    Double lat,lng;
    Button btnActualizar,btnCancelar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    Bitmap bitmapimg;
    Dialog Mydialog;
    int T1Hour,T1Minute;
    private boolean hayError;
    
    public EditarVisitaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_visita, container, false);
        Bundle bundle = this.getArguments();
        idVisita = bundle.getInt("idVisita");
        nomAlbergue = bundle.getString("nomAlbergue");
        nomCanino = bundle.getString("nomCanino");
        visFecha = bundle.getString("visFecha");
        visHora = bundle.getString("visHora");
        visEstado = bundle.getString("visEstado");
        canImgag = bundle.getString("canImg");
        idCanino = bundle.getInt("idCanino");
        progressBar = view.findViewById(R.id.progress_bar_edit_visita);
        imgCaninoEdit = view.findViewById(R.id.img_canino_edit_visita);
        tvNomCanino = view.findViewById(R.id.tvNombreCanino_edit_visita);
        tvSexoCanino = view.findViewById(R.id.tv_sexo_canin_edit_visita);
        tvEdadCanino = view.findViewById(R.id.tv_edad_canino_edit_visita);
        tvNomAlbergue = view.findViewById(R.id.tvNombreAlbergue_edit_visita);
        edtFecha = view.findViewById(R.id.edt_fecha_edit_visita);
        edtHora = view.findViewById(R.id.edt_hora_edit_visita);
        edtComentario = view.findViewById(R.id.edt_descripcion_edit_visita);
        btnUbicacion = view.findViewById(R.id.btn_comollegar_editar_visita);
        btnActualizar = view.findViewById(R.id.btn_actualizar_visita_edit_visita);
        btnCancelar = view.findViewById(R.id.btn_cancelar_visita_edit_visita);
        requestQueue = Volley.newRequestQueue(getContext());
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        cargarWebService();
        comprobarEstadoVisita();
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });
        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        //String date = dayOfMonth+"/"+month+"/"+year;
                        fecha_visita = year+"/"+month+"/"+dayOfMonth;
                        edtFecha.setText(fecha_visita);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        T1Hour = hourOfDay;
                        T1Minute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,T1Hour,T1Minute);
                        edtHora.setText(DateFormat.format("hh:mm aa",calendar));
                    }
                },12,0,false);
                timePickerDialog.updateTime(T1Hour,T1Minute);
                timePickerDialog.show();
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarRegistroVisita();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarRegistroVisita();
            }
        });

        return view;
    }

    private void comprobarEstadoVisita() {
        if (visEstado.equals("CONFIRMADO")){
            edtFecha.setEnabled(false);
            edtHora.setEnabled(false);
            btnActualizar.setEnabled(false);
            edtComentario.setEnabled(false);
            edtFecha.setTextColor(Color.parseColor("#9E9E9E"));
            edtHora.setTextColor(Color.parseColor("#9E9E9E"));
            btnActualizar.setTextColor(Color.parseColor("#9E9E9E"));
            edtComentario.setTextColor(Color.parseColor("#9E9E9E"));
        }
    }

    private void eliminarRegistroVisita() {
        cargarDialog();
    }

    private void cargarDialog() {
        Mydialog = new Dialog(getContext());
        Mydialog.setContentView(R.layout.dialog_confirmar);
        ((TextView) Mydialog.findViewById(R.id.tvTittle)).setText("Cancelar visita");
        ((TextView) Mydialog.findViewById(R.id.tvMensaje)).setText("¿Está seguro que desea cancelar su visita?");
        ((Button) Mydialog.findViewById(R.id.btnConfirmarDialog)).setText("Si");
        ((Button) Mydialog.findViewById(R.id.btnRechazarDialog)).setText("No");
        Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Mydialog.findViewById(R.id.btnConfirmarDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebServiceEliminar();
            }
        });
        Mydialog.findViewById(R.id.btnRechazarDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydialog.dismiss();
            }
        });
        Mydialog.show();
    }

    private void cargarWebServiceEliminar() {
        String url = RUTA+"cancelarVisita.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Visita Cancelada!", Toast.LENGTH_SHORT).show();
                volverInicio();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idvisita",String.valueOf(idVisita));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actualizarRegistroVisita() {
        hayError = false;
        hora_visita = edtHora.getText().toString();
        fecha_visita = edtFecha.getText().toString();
        if (hora_visita.equals(visHora) && fecha_visita.equals(visFecha) ){
            Toast.makeText(getContext(), "No se ha realizado ningun cambio", Toast.LENGTH_SHORT).show();
            hayError = true;
        }
        if (!hayError){
            btnActualizar.setEnabled(false);
            cargarWebServiceActulizar();
        }
        
    }

    private void cargarWebServiceActulizar() {
        progressBar.setVisibility(View.VISIBLE);
        String url = RUTA+"actualizarVisita.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Visita Actualizada!", Toast.LENGTH_SHORT).show();
                        volverInicio();
                    }
                }, 1000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnActualizar.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idvisita",String.valueOf(idVisita));
                params.put("fecha_up",fecha_visita);
                params.put("hora_up",hora_visita);
                params.put("comentario_up",edtComentario.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void volverInicio() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentManager fm = activity.getSupportFragmentManager();
        fm.popBackStackImmediate();
        btnActualizar.setEnabled(true);
    }

    private void cargarWebService() {
        String url = RUTA+"consultarDetalleVisitaRegistrada.php?id_visita="+idVisita;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("consulta");
                try {
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(0);
                        tvSexoCanino.setText(jsonObject.optString("canSexo"));
                        tvEdadCanino.setText(jsonObject.optString("canEdad"));
                        lat = Double.parseDouble(jsonObject.optString("ubiLatitud"));
                        lng = Double.parseDouble(jsonObject.optString("ubiLongitud"));
                        Glide.with(getContext()).load(String.valueOf(RUTA+canImgag)).into(imgCaninoEdit);
                        tvNomCanino.setText(nomCanino);
                        tvNomAlbergue.setText(nomAlbergue);
                        edtHora.setText(visHora);
                        edtFecha.setText(visFecha);
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

    private void enviarDatos() {
        Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+lat+","+lng+"&mode=l"));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getContext().getPackageManager())!=null){
            startActivity(intent);
        }
    }

}