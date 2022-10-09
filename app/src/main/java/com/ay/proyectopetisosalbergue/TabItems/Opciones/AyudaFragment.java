package com.ay.proyectopetisosalbergue.TabItems.Opciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ay.proyectopetisosalbergue.R;

public class AyudaFragment extends Fragment {


    public AyudaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ayuda, container, false);


        return view;
    }
}