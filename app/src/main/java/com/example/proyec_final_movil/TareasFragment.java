package com.example.proyec_final_movil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.proyec_final_movil.data.AdaptadorTareas;
import com.example.proyec_final_movil.data.Adaptadorlista;
import com.example.proyec_final_movil.data.DaoNotas;
import com.example.proyec_final_movil.data.DaoTareas;
import com.example.proyec_final_movil.data.Notas;
import com.example.proyec_final_movil.data.Tareas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TareasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TareasFragment extends Fragment {

    RecyclerView recyclerView;
   FloatingActionButton fabtareas;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

        public TareasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TareasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TareasFragment newInstance(String param1, String param2) {
        TareasFragment fragment = new TareasFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parent=inflater.inflate(R.layout.fragment_tareas, container, false);
        fabtareas=parent.findViewById(R.id.Buttontareas);

        recyclerView=parent.findViewById(R.id.recytareas);
        actualizar();
        fabtareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AgregarTareas.class);
                startActivity(intent);
            }
        });
        return parent;

    }
    public void actualizar(){
        DaoTareas daoTareas=new DaoTareas(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdaptadorTareas adapt=new AdaptadorTareas((ArrayList<Tareas>) daoTareas.buscarTodos1());
        recyclerView.setAdapter(adapt);
    }

}