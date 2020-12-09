package com.example.proyec_final_movil.data;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyec_final_movil.R;

import java.io.IOException;
import java.util.ArrayList;

public class Adaptadoraudio extends RecyclerView.Adapter<Adaptadoraudio.ViewHolderDatos> {
    ArrayList<NotasMedia>listaaudio=new ArrayList<>();

    public Adaptadoraudio(ArrayList<NotasMedia> listaaudio) {
        this.listaaudio = listaaudio;
    }

    @NonNull
    @Override
    public Adaptadoraudio.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itmaudio,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
    holder.nombreaudio.setText(listaaudio.get(position).getUri());
    holder.reproducir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MediaPlayer mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(listaaudio.get(position).getUri());
                mediaPlayer.prepare();
            }catch (IOException e){
                e.printStackTrace();
            }
            mediaPlayer.start();
            Toast.makeText(context,"Reproduciendo",Toast.LENGTH_SHORT).show();
        }
    });
    }

    @Override
    public int getItemCount() {
        return listaaudio.size();
    }
    Context context;
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombreaudio;
        Button reproducir;
        public ViewHolderDatos(@NonNull View itemView){
            super(itemView);
            context=itemView.getContext();
            nombreaudio=itemView.findViewById(R.id.texnomaudio);
            reproducir=itemView.findViewById(R.id.btnrepro);

        }
    }
}
