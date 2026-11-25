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

public class DialogoSexo extends DialogFragment {

    OnAvatarListener onAvatarListener;
    RadioButton rbHombre;
    RadioButton rbMujer;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dialogo_sexo, null);
        rbHombre = myView.findViewById(R.id.rbHombre);
        rbMujer = myView.findViewById(R.id.rbMujer);

        builder.setView(myView);
        builder.setTitle(R.string.titulo_sexo).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (rbHombre.isChecked()) {
                    onAvatarListener.onSexo(0);
                } else if (rbMujer.isChecked()) {
                    onAvatarListener.onSexo(1);
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
