package com.ay.proyectopetisos.TabItems.Opciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ay.proyectopetisos.R;

public class VisitasRegistradasFragment extends Fragment {

    public VisitasRegistradasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visitas_registradas, container, false);
    }
}