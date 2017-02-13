package com.example.eneasseven.acenco.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.eneasseven.acenco.R;
import com.example.eneasseven.acenco.actPrincipal;
import com.example.eneasseven.acenco.dto.DtoDadosRecebidos;

public class BroadCastNotificacao extends BroadcastReceiver {
    public BroadCastNotificacao() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        DtoDadosRecebidos dtoDadosRecebidos = (DtoDadosRecebidos)intent.getSerializableExtra("dados");
        String camera =  "Camera: " + dtoDadosRecebidos.getParametros().split(",")[4];
        String nomeCamera = dtoDadosRecebidos.getParametros().split(",")[2];

        Log.i("Servico","BroadCast Notificacao: " + dtoDadosRecebidos.getClasse());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent itRetorno = new Intent(context,actPrincipal.class);
        itRetorno.putExtra("dados",dtoDadosRecebidos);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,itRetorno,0);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
        notificationCompat.setTicker("Acenco");
        notificationCompat.setContentTitle("Aviso Acenco");
        notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
        //notificationCompat.setLargeIcon(R.mipmap.ic_launcher);
        notificationCompat.setContentIntent(pendingIntent);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] descricoes = new String[]{camera, nomeCamera, dtoDadosRecebidos.getClasse(), dtoDadosRecebidos.getDescricao(),};
        for(int n = 0; n < descricoes.length; n++){
            inboxStyle.addLine(descricoes[n]);
        }
        notificationCompat.setStyle(inboxStyle);

        Notification notification = notificationCompat.build();
        notification.vibrate = new long[]{150,300,150,600};

        notificationManager.notify(R.mipmap.ic_launcher,notification); //o primeiro parametro é um inteiro para identificar a notificacao, estou usando oa inteiro da imagem.

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context,som);
            toque.play();

        }catch (Exception ex){
            Log.e("Servico",ex.getMessage());
        }


    }
}
