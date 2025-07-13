package com.example.sonrisasaludable.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.SesionActivity;
import com.example.sonrisasaludable.data.models.UsuarioResponse;
import com.example.sonrisasaludable.interfaces.OnPerfilActionsListener;
import com.example.sonrisasaludable.utilidades.SessionManager;

public class ConfiguracionFragment extends Fragment implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener,
        OnPerfilActionsListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UsuarioResponse usuarioActual;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declaración de las variables de los componentes de la vista
    Spinner cboIdiomas; // Para el ComboBox
    CheckBox chkNotificaciones; // Para el CheckBox
    TextView lblSonido,tvCorreoUsuario,tvTelefonoUsuario,tvNombreUsuario; // Para la etiqueta de Sonido
    SeekBar barSonido; // Para la barra de sonido
    Button btnAplicar, btnRestaurar, btnEditarPerfil, btnCerrarSesion; // Para el botón aplicar y Restaurar
    private ImageView ivFotoPaciente;
    private SessionManager sessionManager;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionFragment newInstance(String param1, String param2) {
        ConfiguracionFragment fragment = new ConfiguracionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onEditarPerfil() {
        if (usuarioActual != null) {
            Bundle bundle = new Bundle();
            bundle.putString("dni", usuarioActual.getDni());
            bundle.putString("nombres", usuarioActual.getNombres());
            bundle.putString("apellidos", usuarioActual.getApellidos());
            bundle.putString("correo", usuarioActual.getCorreo());
            bundle.putString("telefono", usuarioActual.getTelefono());
            bundle.putString("direccion", usuarioActual.getDireccion());
            bundle.putString("fechanacimiento", usuarioActual.getFechanacimiento());
            bundle.putString("sexo", usuarioActual.getSexo());
            bundle.putString("foto_perfil", usuarioActual.getFoto_perfil());

            EditPerfilPaciente editFragment = new EditPerfilPaciente();
            editFragment.setArguments(bundle);

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.contenedorPerfil, editFragment)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Los datos del perfil aún no están cargados", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onPerfilActualizado() {
        cargarDatosUsuario(); // vuelve a cargar los datos desde SessionManager
    }
    public void onCancelarEdicion() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.contenedorPerfil, new PerfilPaciente())
                .commit();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);
        sessionManager = SessionManager.getInstance(getContext());
        // Inicializar los componentes de la vista
        cboIdiomas = vista.findViewById(R.id.frgCfgcboIdioma); // Spinner (ComboBox)
        chkNotificaciones = vista.findViewById(R.id.froCfgchkNotificacines); // Checkbox
        lblSonido = vista.findViewById(R.id.frgCfgCblSonido); // Etiqueta de Sonido
        barSonido = vista.findViewById(R.id.frgCfgBarSonido); // Barra de sonido (SeekBar)

        // Botones
        btnAplicar = vista.findViewById(R.id.frgCfg8tnAplicar); // Botón Aplicar
        btnRestaurar = vista.findViewById(R.id.frgCfgBtnRestaurar); // Botón Restaurar

        btnAplicar.setOnClickListener(this);
        btnRestaurar.setOnClickListener(this);
        barSonido.setOnSeekBarChangeListener(this);


        //Botón cerrar sesión
        Button btnCerrarSesion = vista.findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SesionActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        cargarPreferencias();
        cargarDatosUsuario();
        return vista;
    }


    private void cargarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        int idioma =preferences.getInt("idioma",0);
        boolean notificaciones = preferences.getBoolean("notificaciones",false);
        int sonido = preferences.getInt("sonido",100);

        cboIdiomas.setSelection(idioma);
        chkNotificaciones.setChecked(notificaciones);
        barSonido.setProgress(sonido);

    }
    public UsuarioResponse getUsuarioActual() {
        return usuarioActual;
    }
    private void cargarDatosUsuario() {
        usuarioActual = sessionManager.getUsuario(); // ← ahora accedes desde SessionManager

        if (usuarioActual != null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.contenedorPerfil, new PerfilPaciente())
                    .commit();
        } else {
            Toast.makeText(getContext(), "No se pudieron cargar los datos del perfil", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.frgCfg8tnAplicar)
            aplicar();
        else if (v.getId() == R.id.frgCfgBtnRestaurar)
            restaurar();
    }

    private void cambiaridioma(int index_idioma) {
        int posicion = index_idioma;
        String codigoIdioma = "es";

        if (posicion == 1) {
            codigoIdioma = "en";
        } else if (posicion == 2) {
            codigoIdioma = "ru";
        } else if (posicion == 3) {
            codigoIdioma = "zh";
        }

        // Cambiamos el idioma
        com.example.sonrisasaludable.utilidades.LocaleHelper.cambiarIdioma(getActivity(), codigoIdioma);

        // Recargamos_Todo XD
        getActivity().recreate();
    }

    private void aplicar() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int index_idioma = cboIdiomas.getSelectedItemPosition();
        editor.putInt("idioma", index_idioma);
        editor.putBoolean("notificaciones", chkNotificaciones.isChecked());
        editor.putInt("sonido", barSonido.getProgress());

        cambiaridioma(index_idioma);

        editor.apply();
        Toast.makeText(getContext(), "Preferencias quardadas", Toast.LENGTH_SHORT).show();
    }


    private void restaurar() {
        cboIdiomas.setSelection(0);
        chkNotificaciones.setChecked(true);
        barSonido.setProgress(100);
        Toast.makeText(getContext(), "Preferencias restablecidas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar == barSonido) {
            String volumen = "Volumen: ";
            switch (progress) {
                case 0:
                    volumen += "Minimo";
                    break;
                case 100:
                    volumen += "Máximo";
                    break;
                default:
                    volumen += String.valueOf(progress);
                    break;
            }
            lblSonido.setText(volumen);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}













