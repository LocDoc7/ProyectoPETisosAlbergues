package com.ay.proyectopetisos.TabItems.Opciones;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.TabItems.Inicio.AnimalesPorAlbergueFragment;

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
        }
    }
}