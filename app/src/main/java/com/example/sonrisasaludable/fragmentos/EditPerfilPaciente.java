package com.example.sonrisasaludable.fragmentos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.models.UsuarioResponse;
import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.interfaces.OnPerfilActionsListener;
import com.example.sonrisasaludable.utilidades.RealPathUtil;
import com.example.sonrisasaludable.utilidades.SessionManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPerfilPaciente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPerfilPaciente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnPerfilActionsListener listener;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgPerfil;
    private Uri imageUri;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditPerfilPaciente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPerfilPaciente.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPerfilPaciente newInstance(String param1, String param2) {
        EditPerfilPaciente fragment = new EditPerfilPaciente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment(); // importante para fragmentos hijos

        if (parentFragment instanceof OnPerfilActionsListener) {
            listener = (OnPerfilActionsListener) parentFragment;
        } else {
            throw new ClassCastException(parentFragment + " debe implementar OnPerfilActionsListener");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Guarda la URI seleccionada
            imgPerfil.setImageURI(imageUri); // Muestra la imagen seleccionada en el ImageView
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_perfil_paciente, container, false);
        super.onViewCreated(view, savedInstanceState);
        EditText etDNI = view.findViewById(R.id.etDNI);
        EditText etNombres = view.findViewById(R.id.etNombres);
        EditText etApellidos = view.findViewById(R.id.etApellidos);
        EditText etTelefono = view.findViewById(R.id.etTelefono);
        EditText etDireccion = view.findViewById(R.id.etDireccion);
        Spinner spinnerSexo = view.findViewById(R.id.spinnerSexo);
        imgPerfil = view.findViewById(R.id.imgPerfil);
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir galería
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona imagen"), PICK_IMAGE_REQUEST);
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            etDNI.setText(args.getString("dni", ""));
            etNombres.setText(args.getString("nombres", ""));
            etApellidos.setText(args.getString("apellidos", ""));
            etTelefono.setText(args.getString("telefono", ""));
            etDireccion.setText(args.getString("direccion", ""));

            String sexo = args.getString("sexo", "");
            if (sexo.equalsIgnoreCase("Masculino")) {
                spinnerSexo.setSelection(0);
            } else if (sexo.equalsIgnoreCase("Femenino")) {
                spinnerSexo.setSelection(1);
            }

            String fotoUrl = args.getString("foto_perfil", "");
            if (!fotoUrl.isEmpty()) {
                //String imagenConTimestamp = fotoUrl + "?t=" + System.currentTimeMillis();
                Glide.with(getContext()).load(fotoUrl).into(imgPerfil);
            }
        }
        Button btnCancelar = view.findViewById(R.id.btnCancelarEditarPerfilP); // Asegúrate de que exista en tu XML
        btnCancelar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelarEdicion(); // Llama al método del padre
            }
        });
        Button btnGuardar = view.findViewById(R.id.btnConfirmarEditarPerfilP);
        btnGuardar.setOnClickListener(v -> {
            int userId = SessionManager.getInstance(requireContext()).getUserId();
            ApiService apiService = RetrofitClient.getApiService();

            // Convertir campos a RequestBody
            RequestBody nombres = RequestBody.create(MediaType.parse("text/plain"), etNombres.getText().toString());
            RequestBody apellidos = RequestBody.create(MediaType.parse("text/plain"), etApellidos.getText().toString());
            RequestBody telefono = RequestBody.create(MediaType.parse("text/plain"), etTelefono.getText().toString());
            RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), etDireccion.getText().toString());
            RequestBody sexo = RequestBody.create(MediaType.parse("text/plain"), spinnerSexo.getSelectedItem().toString());
            RequestBody dni = RequestBody.create(MediaType.parse("text/plain"), etDNI.getText().toString());

            MultipartBody.Part imagenPart = null;

            // Si hay imagen nueva seleccionada
            if (imageUri != null) {
                String filePath = RealPathUtil.getRealPathFromURI(requireContext(), imageUri); // Te doy el método abajo
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imagenPart = MultipartBody.Part.createFormData("foto", file.getName(), requestFile);
            } else {
                // Si no se seleccionó imagen, se puede mandar una vacía
                imagenPart = MultipartBody.Part.createFormData("foto", "", RequestBody.create(MediaType.parse("application/octet-stream"), new byte[0]));
            }

            apiService.updateUsuarioPorId(userId, imagenPart, nombres, apellidos, telefono, direccion, sexo, dni)
                    .enqueue(new Callback<UsuarioResponse>() {
                        @Override
                        public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
                                SessionManager.getInstance(requireContext()).guardarUsuario(response.body());
                                if (listener != null) {
                                    listener.onPerfilActualizado();
                                    listener.onCancelarEdicion();
                                }
                            } else {
                                Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Fallo de red", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        return view;
    }
}