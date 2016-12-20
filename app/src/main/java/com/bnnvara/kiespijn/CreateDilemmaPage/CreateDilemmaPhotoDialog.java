package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bnnvara.kiespijn.R;

public class CreateDilemmaPhotoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_deadline, null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Hello")
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }
}
