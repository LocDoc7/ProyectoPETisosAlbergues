package com.ay.proyectopetisos.TabItems.Opciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;
import com.ay.proyectopetisos.ui.Login.LoginActivity;

public class OpcionesFragment extends Fragment implements View.OnClickListener {
    CardView cdUsuario,cdVisitas,cdCodigos,cdConsejos,cdAyuda,cdCerrarSesión;

    public OpcionesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opciones, container, false);
        cdUsuario = view.findViewById(R.id.card_usuario);
        cdVisitas = view.findViewById(R.id.card_visitas_programadas);
        cdCodigos = view.findViewById(R.id.card_codigos);
        cdConsejos = view.findViewById(R.id.card_consejos_salud);
        cdAyuda = view.findViewById(R.id.card_ayuda);
        cdCerrarSesión = view.findViewById(R.id.card_cerrar_sesion);

        cdVisitas.setOnClickListener(this);
        cdCodigos.setOnClickListener(this);
        cdConsejos.setOnClickListener(this);
        cdUsuario.setOnClickListener(this);
        cdAyuda.setOnClickListener(this);
        cdCerrarSesión.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_visitas_programadas:{
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new VisitasRegistradasFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                break;
            }
            case R.id.card_codigos:{
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new CodigosIntercambioFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                break;
            }
            case R.id.card_consejos_salud:{
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new ConsejosSaludFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                break;
            }
            case R.id.card_usuario:{
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new UsuarioFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                break;
            }
            case R.id.card_ayuda:{
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment fragment = new AyudaFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootOpciones_frame,fragment).addToBackStack(null).commit();
                break;
            }
            case R.id.card_cerrar_sesion:{
                SharedPreferences preferences = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ususario","");
                editor.putString("contraseña","");
                editor.commit();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
            }
        }
    }
}