package com.example.osobiste_powitanie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import org.w3c.dom.CDATASection;

public class PowiadomieniaHelper {
    public static final String KANAL_NISKI = "kanal_niski";

    private static final int KOD_ZADANIA_UPRAWNIEN_POWIADOMIEN = 1;

    public static void utworzKanalyPowiadomien(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(stworzKanal(KANAL_NISKI, "low", NotificationManager.IMPORTANCE_LOW));
            }
        }
    }

    private static NotificationChannel stworzKanal(String id, String nazwa, int importance) {
        NotificationChannel kanal = new NotificationChannel(id, nazwa, importance);
        kanal.setDescription("Kanał dla powiadomień: " + nazwa);
        return kanal;
    }

    public static void pokazPowiadomienie(Context context, String tytul, String tresc, int styl, String kanalId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (context instanceof MainActivity) {
                    ActivityCompat.requestPermissions(
                            (MainActivity) context,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                            KOD_ZADANIA_UPRAWNIEN_POWIADOMIEN
                    );
                }
                return;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, kanalId)
                .setContentTitle(tytul)
                .setContentText(tresc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.sym_def_app_icon);

        switch (styl) {
            case 1:
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(tresc));
                break;
        }

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}