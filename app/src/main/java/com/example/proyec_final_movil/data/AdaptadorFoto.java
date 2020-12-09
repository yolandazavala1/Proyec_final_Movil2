package com.example.proyec_final_movil.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.R;

import java.util.ArrayList;

public class AdaptadorFoto extends RecyclerView.Adapter<AdaptadorFoto.ViewHolderDatos> {
    ArrayList<NotasMedia> listmedia;

    public AdaptadorFoto(ArrayList<NotasMedia> listmedia) {
        this.listmedia = listmedia;
    }

    @NonNull
    @Override
    public AdaptadorFoto.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itmfoto,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFoto.ViewHolderDatos holder, int position) {
        Bitmap bitmap= BitmapFactory.decodeFile(listmedia.get(position).getUri());
        holder.imgfoto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listmedia.size();
    }

    Context context;
    public class ViewHolderDatos extends RecyclerView.ViewHolder  {
        ImageView imgfoto;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            imgfoto=itemView.findViewById(R.id.rimgfoto);

        }
    }

    }
