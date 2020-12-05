package com.example.proyec_final_movil.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.AgregarTareas;
import com.example.proyec_final_movil.MainActivity;
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

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox tarea;
        Button acct;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            tarea=itemView.findViewById(R.id.chetareas);
            acct=itemView.findViewById(R.id.btntacc);
            acct.setOnClickListener(this);
        }

        public void asiganadatos(Tareas tareas) {
            tarea.setText(tareas.getTitulo());
            if (tareas.getStatus()==1){
                tarea.setChecked(true);
            }else{
                tarea.setChecked(false);
            }


        }
Context context;
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.mn_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.item_actualizar:
                            Intent intent=new Intent(context, AgregarTareas.class);
                            intent.putExtra("pos",listtareas.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                            notifyDataSetChanged();
                            return true;
                        case  R.id.item_eliminar:
                            DaoTareas daoTareas=new DaoTareas(context);
                            if (daoTareas.eliminartare(listtareas.get(getAdapterPosition()).getId())){
                                notifyDataSetChanged();
                                Intent intent1=new Intent(context, MainActivity.class);
                                context.startActivity(intent1);
                                Toast.makeText(v.getContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                                //listNotas.remove(getAdapterPosition());
                            }
                            return true;
                        default:
                            return false;
                }
            }
        });
            popupMenu.show();
    }
}
}
