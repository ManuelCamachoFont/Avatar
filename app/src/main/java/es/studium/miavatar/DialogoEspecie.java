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

public class DialogoEspecie extends DialogFragment {

    OnAvatarListener onAvatarListener;
    RadioButton rbElfo;
    RadioButton rbEnano;
    RadioButton rbHobbit;
    RadioButton rbHumano;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dialogo_especie, null);
        rbElfo = myView.findViewById(R.id.rbElfo);
        rbEnano = myView.findViewById(R.id.rbEnano);
        rbHobbit = myView.findViewById(R.id.rbHobbit);
        rbHumano = myView.findViewById(R.id.rbHumano);

        builder.setView(myView);
        builder.setTitle(R.string.titulo_especie).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (rbElfo.isChecked()) {
                    onAvatarListener.onEspecie(0);
                } else if (rbEnano.isChecked()) {
                    onAvatarListener.onEspecie(1);
                } else if (rbHobbit.isChecked()) {
                    onAvatarListener.onEspecie(2);
                } else if (rbHumano.isChecked()) {
                    onAvatarListener.onEspecie(3);
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
