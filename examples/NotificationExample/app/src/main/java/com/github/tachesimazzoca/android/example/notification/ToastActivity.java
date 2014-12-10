package com.github.tachesimazzoca.android.example.notification;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ToastActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void showToast(View view) {
        Toast.makeText(getApplicationContext(), "Hello Toast", Toast.LENGTH_SHORT).show();
    }

    public void showCustomToast(View view) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.LEFT | Gravity.BOTTOM, 8, 8);
        ViewGroup parent = null;
        View v = getLayoutInflater().inflate(R.layout.view_custom_toast, parent);
        TextView textView = (TextView) v.findViewById(R.id.custom_toast_text_view);
        textView.setText("Hello Custom Toast");
        toast.setView(v);
        toast.show();
    }
}
