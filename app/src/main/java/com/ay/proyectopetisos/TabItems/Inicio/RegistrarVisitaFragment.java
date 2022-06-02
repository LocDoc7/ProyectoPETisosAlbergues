package com.ay.proyectopetisos.TabItems.Inicio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.Adapters.CanesAdapter;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;
import com.ay.proyectopetisos.ui.Login.LoginActivity;
import com.ay.proyectopetisos.ui.Registro.Registro3Activity;
import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ay.proyectopetisos.Util.Util.RUTA;

public class RegistrarVisitaFragment extends Fragment {

    ImageView img_canino;
    TextView tv_nombreAlbergue,tv_nombreCanino,tv_sexoCanino,tv_edadCanino;
    EditText edt_Fecha,edt_Hora,edt_Descripcion;
    Button btn_registrarVisita;
    ProgressBar progressBar;
    int idCanino,idPersona;
    String nombreAlbergue,nombreCanino,sexoCanino,edadCanino,imgenCanino,hora_visita,fecha_visita="";
    int T1Hour,T1Minute;
    private boolean hayError;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public RegistrarVisitaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_visita, container, false);
        SharedPreferences sp1 = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        idPersona = sp1.getInt("idUsuario",0);

        Bundle bundle = this.getArguments();
        idCanino = bundle.getInt("idCanino");
        nombreAlbergue = bundle.getString("nomAlbergue");
        nombreCanino = bundle.getString("nomCanino");
        sexoCanino = bundle.getString("sexoCanino");
        edadCanino = bundle.getString("edadCanino");
        imgenCanino = bundle.getString("imgCanino");

        img_canino = view.findViewById(R.id.img_canino_visita);
        tv_nombreAlbergue = view.findViewById(R.id.tv_nombreAlbergueVisita);
        tv_nombreCanino = view.findViewById(R.id.tvNombreCanino_reg_visita);
        tv_sexoCanino = view.findViewById(R.id.tv_sexo_canin_reg_visita);
        tv_edadCanino = view.findViewById(R.id.tv_edad_canino_reg_visita);
        edt_Fecha = view.findViewById(R.id.edt_fecha_reg_visita);
        edt_Hora = view.findViewById(R.id.edt_hora_reg_visita);
        edt_Descripcion = view.findViewById(R.id.edt_descripcion_reg_visita);
        btn_registrarVisita = view.findViewById(R.id.btn_registar_visita_reg_visita);
        progressBar = view.findViewById(R.id.progress_bar_reg_visita);

        tv_nombreAlbergue.setText(nombreAlbergue);
        tv_nombreCanino.setText(nombreCanino);
        tv_sexoCanino.setText(sexoCanino);
        tv_edadCanino.setText(edadCanino);
        Glide.with(getContext()).load(String.valueOf(RUTA+imgenCanino)).into(img_canino);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        edt_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        fecha_visita = year+"/"+month+"/"+dayOfMonth;
                        edt_Fecha.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        edt_Hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        T1Hour = hourOfDay;
                        T1Minute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,T1Hour,T1Minute);
                        edt_Hora.setText(DateFormat.format("hh:mm aa",calendar));
                    }
                },12,0,false);
                timePickerDialog.updateTime(T1Hour,T1Minute);
                timePickerDialog.show();
            }
        });

        btn_registrarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarDatos();
            }
        });

        return view;
    }

    private void comprobarDatos() {

        hora_visita = edt_Hora.getText().toString();
        hayError = false;

        if (hora_visita.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, seleccione la hora de visita", Toast.LENGTH_SHORT).show();
            hayError = true;
        }else if (T1Hour<8 || T1Hour>20){
            Toast.makeText(getContext(), "Por favor, seleccione una hora entre el rango de 8 am y 8 pm", Toast.LENGTH_SHORT).show();
            edt_Hora.setText("");
            edt_Hora.setHint("Seleccione una hora valida");
            hayError = true;
        }
        if (fecha_visita.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, seleccione la fecha de visita", Toast.LENGTH_SHORT).show();
            hayError = true;
        }
        if (!hayError) {
            cargarWebService();
            btn_registrarVisita.setEnabled(false);
            //enviarDatos();
        }
    }

    private void cargarWebService() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Util.RUTA+"reg_visita.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Se ha registrado tu visita!", Toast.LENGTH_SHORT).show();
                        enviarDatos();
                    }
                }, 1000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idpersona",String.valueOf(idPersona));
                params.put("idcanino",String.valueOf(idCanino));
                params.put("fecha_reg",fecha_visita);
                params.put("hora_reg",hora_visita);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void enviarDatos() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment fragment = new UbicacionAlbergueFragment();
        Bundle bundle = new Bundle();
        String nomAlbergue = nombreAlbergue;
        int idcan = idCanino;
        bundle.putInt("idCanino",idcan);
        bundle.putString("nombreAlbergue",nomAlbergue);
        fragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.root_frame,fragment).commit();
        btn_registrarVisita.setEnabled(true);
    }

}