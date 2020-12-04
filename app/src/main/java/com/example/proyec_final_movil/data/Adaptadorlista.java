package com.example.proyec_final_movil.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adaptadorlista extends RecyclerView.Adapter<Adaptadorlista.ViewHolderDatos> {
    ArrayList<Notas> listNotas;
    ArrayList<Notas> original;
    LayoutInflater inflater;

    public Adaptadorlista( ArrayList<Notas> listNotas) {
        this.listNotas = listNotas;
        this.original=new ArrayList<>();
        original.addAll(listNotas);
    }
     public void filter(String string){
        if (string.length()==0){
            listNotas.clear();
            listNotas.addAll(original);
        }else{
            List<Notas> collect=original.stream().filter(i->i.getTitulo().toLowerCase().contains(string)).collect(Collectors.toList());
            listNotas.clear();
            listNotas.addAll(collect);
        }
        notifyDataSetChanged();
     }
    @NonNull
    @Override
    public Adaptadorlista.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itmlis,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptadorlista.ViewHolderDatos holder, int position) {
     holder.titulo.setText(listNotas.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return listNotas.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView titulo;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.iddato);
        }

        public void asiganadatos(Notas notas) {
            titulo.setText(notas.getTitulo());
        }
    }

}
