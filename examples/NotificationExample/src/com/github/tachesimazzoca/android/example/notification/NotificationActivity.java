package com.github.tachesimazzoca.android.example.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class NotificationActivity extends Activity {
    private static final String TAG = "NotificationActivity";
    private static final int NOTIFICATION_ID = 1;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRadioGroup.check(R.id.single_top_radio);

        Button sendBtn = (Button) findViewById(R.id.send_notification_button);
        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification(view);
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancel_notification_button);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification(view);
            }
        });
    }

    public void sendNotification(View view) {
        Context context = getApplicationContext();
        PendingIntent contentIntent;
        switch (mRadioGroup.getCheckedRadioButtonId()) {
        case R.id.single_top_radio:
            // The activity will not be launched if it's already running at the
            // top of the history stack.
            Intent intent = new Intent(context, getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            contentIntent = PendingIntent.getActivity(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            break;
        default:
            // Rebuild a new task stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
            stackBuilder.addNextIntent(new Intent(context, getClass()));
            contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            break;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext())
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setTicker("Hello Notification!")
                .setContentTitle(getClass().getSimpleName())
                .setContentText("This is a simple notification.")
                .setContentIntent(contentIntent);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(
                NOTIFICATION_ID, builder.build());
    }

    public void cancelNotification(View view) {
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(NOTIFICATION_ID);
    }
}
