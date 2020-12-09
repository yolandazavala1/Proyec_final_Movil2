package com.example.proyec_final_movil.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.R;

import java.net.URI;
import java.util.ArrayList;

public class Adaptadorvideo extends RecyclerView.Adapter<Adaptadorvideo.ViewHolderDatos> {
    ArrayList<NotasMedia> lista;

    public Adaptadorvideo(ArrayList<NotasMedia> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public Adaptadorvideo.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itmvideo,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptadorvideo.ViewHolderDatos holder, int position) {
        Uri uri= Uri.parse(lista.get(position).getUri().toString());
        holder.video.setVideoURI(uri);
        //holder.video.setVideoPath((lista.get(position).getUri()));
        //Bitmap bitmap= BitmapFactory.decodeFile(listmedia.get(position).getUri());
        holder.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.video.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    Context context;
    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {
        VideoView video;
        public ViewHolderDatos(@NonNull View itemView){
            super(itemView);
            context=itemView.getContext();
            video=itemView.findViewById(R.id.revideo);
            video.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            video.start();
        }
    }
}
