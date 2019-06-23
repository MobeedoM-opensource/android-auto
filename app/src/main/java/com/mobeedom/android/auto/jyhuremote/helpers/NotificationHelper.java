package com.mobeedom.android.auto.jyhuremote.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.mobeedom.android.auto.jyhuremote.R;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    public static final int SERVICE_NOTIFICATION_ID = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification buildServiceNotification(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel("service_id", "Background Service", NotificationManager.IMPORTANCE_LOW);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "service_id");
//        builder.setContentTitle("This is heading");
//        builder.setContentText("This is description");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        Notification notification = builder.build();
        return notification;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startForeground(Service service) {
        service.startForeground(SERVICE_NOTIFICATION_ID, buildServiceNotification(service));

    }
}
