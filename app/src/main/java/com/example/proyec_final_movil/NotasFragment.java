package com.example.proyec_final_movil;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.proyec_final_movil.data.Adaptadorlista;
import com.example.proyec_final_movil.data.DaoNotas;
import com.example.proyec_final_movil.data.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.R.layout.*;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends Fragment implements SearchView.OnQueryTextListener{
 FloatingActionButton fabnotas;
RecyclerView listanotas;
ArrayList<Notas> listanota;
SearchView shvi;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotasFragment newInstance(String param1, String param2) {
        NotasFragment fragment = new NotasFragment();
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

   private static int ACTIVITY_NOTAS=1000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View parent=inflater.inflate(R.layout.fragment_notas, container, false);
        fabnotas=parent.findViewById(R.id.Buttonnotas);
        listanotas= parent.findViewById(R.id.listaNR);
        shvi=parent.findViewById(R.id.buscarnota);

         actualizarRecicler();
        listener();
        fabnotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Agregar.class);
                startActivity(intent);
            }
        });
        return parent;
    }
    public void actualizarRecicler(){
        DaoNotas daoNotas=new DaoNotas(getContext());
        listanotas.setLayoutManager(new LinearLayoutManager(getContext()));
        Adaptadorlista ada=new Adaptadorlista((ArrayList<Notas>) daoNotas.buscarTodos());
        listanotas.setAdapter(ada);
    }
    private void listener(){
        shvi.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        DaoNotas daoNotas=new DaoNotas(getContext());
        Adaptadorlista a=new Adaptadorlista((ArrayList<Notas>) daoNotas.buscarTodos());
        a.filter(newText);
        return false;
    }
}
