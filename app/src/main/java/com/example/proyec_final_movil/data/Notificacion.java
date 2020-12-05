package com.example.proyec_final_movil.data;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.proyec_final_movil.MainActivity;
import com.example.proyec_final_movil.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Notificacion  extends Worker{

    public Notificacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    public  static  void guardanot(Long duracion,Data data,String tag){
        OneTimeWorkRequest noti=new OneTimeWorkRequest.Builder(Notificacion.class)
                .setInitialDelay(duracion,TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();
        WorkManager instance= WorkManager.getInstance();

    }
    @NonNull
    @Override
    public Result doWork() {
        String titulo=getInputData().getString("titulo");
        String descripcion=getInputData().getString("descripcion");
        int id=(int)getInputData().getLong("id_tarea",0);

        return Result.success();
    }
    private void oreo(String t,String d){
        String id="mendaje";
        NotificationManager nm=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),id);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.BASE){
            NotificationChannel nc=new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setDescription("Notificacion FCM");
            nc.setShowBadge(true);
            assert nm!=null;
            nm.createNotificationChannel(nc);
        }
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
       // PendingIntent pendingIntent = new PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setAutoCancel(true).setWhen(System.currentTimeMillis()).setContentTitle(t).setTicker("Nueva notificacion")
                .setSmallIcon(R.mipmap.ic_launcher);
        Random random =new Random();
        int idnoty=random.nextInt(8000);
        assert nm!=null;
        nm.notify(idnoty,builder.build());

    }

}
