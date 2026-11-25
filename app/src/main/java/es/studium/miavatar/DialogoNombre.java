package es.studium.miavatar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoNombre extends DialogFragment {

    OnAvatarListener onAvatarListener;
    EditText edtNombre;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dialogo_nombre, null);
        edtNombre = myView.findViewById(R.id.edtNombre);

        builder.setView(myView);
        builder.setTitle(R.string.titulo_nombre).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String nombre = edtNombre.getText().toString().trim();
                if (nombre.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.error_nombre_vacio, Toast.LENGTH_SHORT).show();
                } else {
                    onAvatarListener.onNombre(nombre);
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
