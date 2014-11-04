package com.github.tachesimazzoca.android.example.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.View;
import android.widget.Toast;

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void showDialog(View view) {
        DialogFragment dialog = new YesNoDialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    public static class YesNoDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity())
                    .setTitle("Yes/No Dialog")
                    .setMessage("This is a simple YES/NO dialog.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "The positive button is pressed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "The negative button is pressed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
        }
    }
}
