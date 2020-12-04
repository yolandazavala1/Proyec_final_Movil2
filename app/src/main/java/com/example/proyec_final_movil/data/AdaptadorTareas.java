package com.example.proyec_final_movil.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.R;

import java.util.ArrayList;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.ViewHolderDatos> {
    ArrayList<Tareas> listtareas;

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itmtarea,parent,false);
        return new ViewHolderDatos(view);

    }
    public AdaptadorTareas( ArrayList<Tareas> listtareas) {
        this.listtareas = listtareas;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.tarea.setText(listtareas.get(position).getTitulo());
        if (listtareas.get(position).getStatus()==1){
            holder.tarea.setChecked(true);
        }else {
            holder.tarea.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return listtareas.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        CheckBox tarea;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tarea=itemView.findViewById(R.id.chetareas);
        }

        public void asiganadatos(Tareas tareas) {
            tarea.setText(tareas.getTitulo());
            if (tareas.getStatus()==1){
                tarea.setChecked(true);
            }else{
                tarea.setChecked(false);
            }


        }
    }
}
