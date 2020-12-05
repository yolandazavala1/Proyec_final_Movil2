package com.example.proyec_final_movil.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.Agregar;
import com.example.proyec_final_movil.MainActivity;
import com.example.proyec_final_movil.NotasFragment;
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
    Context context;
    @Override
    public void onBindViewHolder(@NonNull Adaptadorlista.ViewHolderDatos holder, int position) {
     holder.titulo.setText(listNotas.get(position).getTitulo());

    }

    @Override
    public int getItemCount() {
        return listNotas.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;
        Button action;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            titulo=itemView.findViewById(R.id.iddato);
            action=itemView.findViewById(R.id.btnAccion);
            action.setOnClickListener(this);

        }

        public void asiganadatos(Notas notas) {
            titulo.setText(notas.getTitulo());
        }
       int id;
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.mn_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.item_actualizar:
                            Intent intent=new Intent(context, Agregar.class);
                            intent.putExtra("pos",listNotas.get(getAdapterPosition()).getId());
                             context.startActivity(intent);
                             notifyDataSetChanged();
                            return true;
                        case  R.id.item_eliminar:
                            DaoNotas daoNotas=new DaoNotas(context);
                            if (daoNotas.eliminarnita(listNotas.get(getAdapterPosition()).getId())){
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
