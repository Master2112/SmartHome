package com.marcokoopman.weatherapp2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Marco on 8-6-2016.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Oops! sorry..")
                .setMessage("There was an error. Please try again")
                .setPositiveButton("ok", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
