package es.studium.miavatar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnAvatarListener {

    Button btnNombre, btnSexo, btnEspecie, btnProfesion, btnGenerar;
    ImageView imgAvatar;
    LinearLayout layoutEstadisticas;
    TextView txtNombreAvatar, txtVida, txtMagia, txtFuerza, txtVelocidad;
    String nombre = "";
    int sexo = -1;
    int especie = -1;
    int profesion = -1;
    boolean generado = false;
    int vida, magia, fuerza, velocidad;
    int[][][] avatares = {
            {
                    {R.drawable.avatar_hombre_elfo_arquero, R.drawable.avatar_hombre_elfo_guerrero, R.drawable.avatar_hombre_elfo_mago, R.drawable.avatar_hombre_elfo_herrero},
                    {R.drawable.avatar_hombre_enano_arquero, R.drawable.avatar_hombre_enano_guerrero, R.drawable.avatar_hombre_enano_mago, R.drawable.avatar_hombre_enano_herrero},
                    {R.drawable.avatar_hombre_hobbit_arquero, R.drawable.avatar_hombre_hobbit_guerrero, R.drawable.avatar_hombre_hobbit_mago, R.drawable.avatar_hombre_hobbit_herrero},
                    {R.drawable.avatar_hombre_humano_arquero, R.drawable.avatar_hombre_humano_guerrero, R.drawable.avatar_hombre_humano_mago, R.drawable.avatar_hombre_humano_herrero}
            },
            {
                    {R.drawable.avatar_mujer_elfo_arquero, R.drawable.avatar_mujer_elfo_guerrero, R.drawable.avatar_mujer_elfo_mago, R.drawable.avatar_mujer_elfo_herrero},
                    {R.drawable.avatar_mujer_enano_arquero, R.drawable.avatar_mujer_enano_guerrero, R.drawable.avatar_mujer_enano_mago, R.drawable.avatar_mujer_enano_herrero},
                    {R.drawable.avatar_mujer_hobbit_arquero, R.drawable.avatar_mujer_hobbit_guerrero, R.drawable.avatar_mujer_hobbit_mago, R.drawable.avatar_mujer_hobbit_herrero},
                    {R.drawable.avatar_mujer_humano_arquero, R.drawable.avatar_mujer_humano_guerrero, R.drawable.avatar_mujer_humano_mago, R.drawable.avatar_mujer_humano_herrero}
            }
    };

    int[] limites = {100, 10, 20, 5};
    int[][] minimos = {
            {50, 1, 6, 3},
            {70, 0, 14, 2},
            {40, 7, 2, 1},
            {60, 0, 12, 1}
    };
    int[][] maximos = {
            {80, 4, 12, 5},
            {100, 2, 20, 4},
            {70, 10, 7, 3},
            {90, 3, 18, 2}
    };

    int[][] bonus = {
            {0, 2, 0, 1},
            {10, 0, 2, 0},
            {10, 0, 0, 1},
            {0, 1, 1, 0}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnNombre = findViewById(R.id.btnNombre);
        btnSexo = findViewById(R.id.btnSexo);
        btnEspecie = findViewById(R.id.btnEspecie);
        btnProfesion = findViewById(R.id.btnProfesion);
        btnGenerar = findViewById(R.id.btnGenerar);
        imgAvatar = findViewById(R.id.imgAvatar);
        layoutEstadisticas = findViewById(R.id.layoutEstadisticas);
        txtNombreAvatar = findViewById(R.id.txtNombreAvatar);
        txtVida = findViewById(R.id.txtVida);
        txtMagia = findViewById(R.id.txtMagia);
        txtFuerza = findViewById(R.id.txtFuerza);
        txtVelocidad = findViewById(R.id.txtVelocidad);

        if (savedInstanceState != null) {
            nombre = savedInstanceState.getString("nombre");
            sexo = savedInstanceState.getInt("sexo");
            especie = savedInstanceState.getInt("especie");
            profesion = savedInstanceState.getInt("profesion");
            generado = savedInstanceState.getBoolean("generado");
            vida = savedInstanceState.getInt("vida");
            magia = savedInstanceState.getInt("magia");
            fuerza = savedInstanceState.getInt("fuerza");
            velocidad = savedInstanceState.getInt("velocidad");
            if (generado) {
                mostrarAvatar();
            }
        }

        btnNombre.setOnClickListener(v -> mostrarDialogo(new DialogoNombre(), "nombre"));
        btnSexo.setOnClickListener(v -> mostrarDialogo(new DialogoSexo(), "sexo"));
        btnEspecie.setOnClickListener(v -> mostrarDialogo(new DialogoEspecie(), "especie"));
        btnProfesion.setOnClickListener(v -> mostrarDialogo(new DialogoProfesion(), "profesion"));

        btnGenerar.setOnClickListener(v -> {
            if (generado) {
                reiniciar();
            } else {
                generar();
            }
        });
    }

    private void mostrarDialogo(DialogFragment dialogo, String tag) {
        dialogo.setCancelable(false);
        dialogo.show(getSupportFragmentManager(), tag);
    }

    private void generar() {

        StringBuilder faltan = new StringBuilder();
        if (nombre.isEmpty()) {
            anadirFalta(faltan, R.string.btn_nombre);
        }
        if (sexo == -1) {
            anadirFalta(faltan, R.string.btn_sexo);
        }
        if (especie == -1) {
            anadirFalta(faltan, R.string.btn_especie);
        }
        if (profesion == -1) {
            anadirFalta(faltan, R.string.btn_profesion);
        }

        if (faltan.length() > 0) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), getString(R.string.faltan_campos, faltan.toString()), Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.cuero_oscuro));
            snackbar.setTextColor(ContextCompat.getColor(this, R.color.pergamino));
            snackbar.setAnchorView(btnGenerar);
            snackbar.show();
            return;
        }

        Random random = new Random();
        vida = calcularPoder(0, random);
        magia = calcularPoder(1, random);
        fuerza = calcularPoder(2, random);
        velocidad = calcularPoder(3, random);
        generado = true;
        mostrarAvatar();
    }

    private void anadirFalta(StringBuilder faltan, int idTexto) {
        if (faltan.length() > 0) {
            faltan.append(getString(R.string.separador));
        }
        faltan.append(getString(idTexto));
    }

    private int calcularPoder(int poder, Random random) {
        int min = minimos[profesion][poder];
        int max = maximos[profesion][poder];
        int valor = min + random.nextInt(max - min + 1) + bonus[especie][poder];
        return Math.min(valor, limites[poder]);
    }

    private void mostrarAvatar() {
        imgAvatar.setImageResource(avatares[sexo][especie][profesion]);
        txtNombreAvatar.setText(nombre);
        txtVida.setText(getString(R.string.formato_estadistica, getString(R.string.vida), vida));
        txtMagia.setText(getString(R.string.formato_estadistica, getString(R.string.magia), magia));
        txtFuerza.setText(getString(R.string.formato_estadistica, getString(R.string.fuerza), fuerza));
        txtVelocidad.setText(getString(R.string.formato_estadistica, getString(R.string.velocidad), velocidad));
        layoutEstadisticas.setVisibility(View.VISIBLE);
        btnGenerar.setText(R.string.btn_reiniciar);
        activarOpciones(false);
    }

    private void reiniciar() {
        nombre = "";
        sexo = -1;
        especie = -1;
        profesion = -1;
        generado = false;
        imgAvatar.setImageResource(R.drawable.avatar_incognita);
        layoutEstadisticas.setVisibility(View.GONE);
        btnGenerar.setText(R.string.btn_generar);
        activarOpciones(true);
    }

    private void activarOpciones(boolean activar) {
        btnNombre.setEnabled(activar);
        btnSexo.setEnabled(activar);
        btnEspecie.setEnabled(activar);
        btnProfesion.setEnabled(activar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nombre", nombre);
        outState.putInt("sexo", sexo);
        outState.putInt("especie", especie);
        outState.putInt("profesion", profesion);
        outState.putBoolean("generado", generado);
        outState.putInt("vida", vida);
        outState.putInt("magia", magia);
        outState.putInt("fuerza", fuerza);
        outState.putInt("velocidad", velocidad);
    }

    @Override
    public void onNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void onSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public void onEspecie(int especie) {
        this.especie = especie;
    }

    @Override
    public void onProfesion(int profesion) {
        this.profesion = profesion;
    }
}
