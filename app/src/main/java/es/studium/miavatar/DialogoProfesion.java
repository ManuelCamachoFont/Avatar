package es.studium.miavatar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoProfesion extends DialogFragment {

    OnAvatarListener onAvatarListener;
    RadioButton rbArquero;
    RadioButton rbGuerrero;
    RadioButton rbMago;
    RadioButton rbHerrero;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dialogo_profesion, null);
        rbArquero = myView.findViewById(R.id.rbArquero);
        rbGuerrero = myView.findViewById(R.id.rbGuerrero);
        rbMago = myView.findViewById(R.id.rbMago);
        rbHerrero = myView.findViewById(R.id.rbHerrero);

        builder.setView(myView);
        builder.setTitle(R.string.titulo_profesion).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (rbArquero.isChecked()) {
                    onAvatarListener.onProfesion(0);
                } else if (rbGuerrero.isChecked()) {
                    onAvatarListener.onProfesion(1);
                } else if (rbMago.isChecked()) {
                    onAvatarListener.onProfesion(2);
                } else if (rbHerrero.isChecked()) {
                    onAvatarListener.onProfesion(3);
                } else {
                    Toast.makeText(getActivity(), R.string.error_sin_opcion, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onAvatarListener = (OnAvatarListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnAvatarListener");
        }
    }
}
