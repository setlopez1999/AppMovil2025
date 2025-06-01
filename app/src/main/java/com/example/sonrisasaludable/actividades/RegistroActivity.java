package com.example.sonrisasaludable.actividades;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.example.sonrisasaludable.data.dao.UsuarioDao;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.models.RegisterRequest;
import com.example.sonrisasaludable.data.models.UsuarioResponse;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.utilidades.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {

    private EditText
            etDNI, etNombres, etApellidos,
            etCorreo, etClave, etConfirmarClave,
            etTelefono, etDireccion, etFecha;

    private Spinner
            spinnerSexo;
    private TextView tvTerminos;
    private Button btnRegistrar;
    private CheckBox checkBox;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgPerfil;
    private Uri imageUri;
    private Calendar fechaSeleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        aplicarInsets();
        inicializarComponentes();

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

        configurarTerminosYCondiciones();
        configurarFechaNacimiento();
        configurarBotonSalir();
        configurarBotonRegistrar();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgPerfil.setImageURI(imageUri);
        }
    }

    private void aplicarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarComponentes() {
        checkBox = findViewById(R.id.checkBox);
        tvTerminos = findViewById(R.id.tvTerminos);
        etFecha = findViewById(R.id.etFecha);
        btnRegistrar = findViewById(R.id.sesRBtnCrear);

        imgPerfil = findViewById(R.id.imgPerfil);
        etDNI = findViewById(R.id.etDNI);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etFecha = findViewById(R.id.etFecha);
        spinnerSexo = findViewById(R.id.spinnerSexo);
        etCorreo = findViewById(R.id.etCorreo);
        etClave = findViewById(R.id.etClave);
        etConfirmarClave = findViewById(R.id.etConfirmarClave);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        checkBox = findViewById(R.id.checkBox);
    }

    private void configurarTerminosYCondiciones() {
        String frase = getString(R.string.terminos_condiciones_frase);
        String subfrase = getString(R.string.terminos_condiciones_subfrase);
        int start = frase.indexOf(subfrase);
        int end = start + subfrase.length();
        SpannableString texto = new SpannableString(frase);

        ClickableSpan clickTerminos = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mostrarDialogoTerminos();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(RegistroActivity.this, R.color.purple_700));
                ds.setUnderlineText(true);
            }
        };

        if (start >= 0) {
            texto.setSpan(clickTerminos, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvTerminos.setText(texto);
            tvTerminos.setMovementMethod(LinkMovementMethod.getInstance());
            tvTerminos.setHighlightColor(Color.TRANSPARENT);
        } else {
            tvTerminos.setText(frase);
        }
    }

    private void mostrarDialogoTerminos() {
        new AlertDialog.Builder(RegistroActivity.this)
                .setTitle(getString(R.string.titulo_terminos))
                .setMessage(getString(R.string.mensaje_terminos))
                .setPositiveButton(getString(R.string.aceptar), (dialog, which) -> checkBox.setChecked(true))
                .setNegativeButton(getString(R.string.cancelar), null)
                .show();
    }

    private void configurarFechaNacimiento() {
        etFecha.setOnClickListener(v -> {
            final Calendar calendario = Calendar.getInstance();
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    RegistroActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar fecha = Calendar.getInstance();
                        fecha.set(year, month, dayOfMonth);

                        // Validar edad mínima de 5 años
                        Calendar hoy = Calendar.getInstance();
                        hoy.add(Calendar.YEAR, -5);
                        if (fecha.after(hoy)) {
                            Toast.makeText(this, "Debes tener al menos 5 años.", Toast.LENGTH_SHORT).show();
                            etFecha.setText("");
                            fechaSeleccionada = null;
                        } else {
                            fechaSeleccionada = fecha;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            etFecha.setText(sdf.format(fecha.getTime()));
                        }
                    },
                    anio, mes, dia
            );

            datePicker.show();
        });
    }

    private void configurarBotonSalir() {
        Button btnSalir = findViewById(R.id.sesRBtnSalir);
        btnSalir.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroActivity.this, SesionActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void configurarBotonRegistrar() {


        if (imageUri != null && !Uri.EMPTY.equals(imageUri)) {
            String path = imageUri.getPath();
        }





        btnRegistrar.setOnClickListener(v -> {



            String dni = etDNI.getText().toString();
            String nombres = etNombres.getText().toString();
            String apellidos = etApellidos.getText().toString();
            String correo = etCorreo.getText().toString();
            String clave = etClave.getText().toString();
            String confirmarClave = etConfirmarClave.getText().toString();
            String telefono = etTelefono.getText().toString();
            String direccion = etDireccion.getText().toString();
            String fecha = etFecha.getText().toString();
            String sexo = spinnerSexo.getSelectedItem().toString();


            if (!checkBox.isChecked()) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (clave.isEmpty()){
                mostrar("Agregue una contraseña valida");
                return;
            } else if (!clave.equals(confirmarClave)) {
                mostrar("Las contraseñas no coinciden");
                return;
            }

            if (fechaSeleccionada == null) {
                Toast.makeText(this, "Por favor selecciona una fecha válida.", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest request = new RegisterRequest(
                    dni, nombres, apellidos, correo, clave, telefono,
                    direccion, fecha, sexo
            );
            if (!(NetworkUtils.isNetworkAvailable(getApplicationContext()))) {
                //guardarLocalmente(request); Deprecado ya q no guardara usuario si no esta en red
                mostrar("Error de Red");
                return;
            }
            Log.d("RegistroDebug", "Botón REGISTRAR presionado");
            registrarEnServidor(request);

        });
    }


    private void registrarEnServidor(RegisterRequest request) {
        ApiService api = RetrofitClient.getApiService();

        // Crear partes de texto
        RequestBody dni = toPart(request.getDni());
        RequestBody nombres = toPart(request.getNombres());
        RequestBody apellidos = toPart(request.getApellidos());
        RequestBody correo = toPart(request.getCorreo());
        RequestBody clave = toPart(request.getClave());
        RequestBody telefono = toPart(request.getTelefono());
        RequestBody direccion = toPart(request.getDireccion());
        RequestBody fechaNacimiento = toPart(request.getFechaNacimiento());
        RequestBody sexo = toPart(request.getSexo());


        MultipartBody.Part imagenPart = null;

        if (imageUri != null && !Uri.EMPTY.equals(imageUri)) {
            File file = new File(Objects.requireNonNull(getRealPathFromURI(imageUri)));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            imagenPart = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);
        } else {
            // Imagine Defeat Dragon
            RequestBody defaultImage = RequestBody.create(MediaType.parse("text/plain"), "Sin imagen");
            imagenPart = MultipartBody.Part.createFormData("imagen", "SinImagen.txt", defaultImage);
        }

        Call<UsuarioResponse> call = api.registrarUsuarioConImagen(
                dni, nombres, apellidos, correo, clave, telefono, direccion, fechaNacimiento, sexo, imagenPart
        );
        Log.d("RegistroDebug", "Ejecutando llamada registrarUsuarioConImagen()");
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse usuarioRegistrado = response.body();

                    // Agregamos el usuario

                    UsuarioEntity entity = new UsuarioEntity(
                            usuarioRegistrado.getId(),
                            usuarioRegistrado.getNombres(),
                            usuarioRegistrado.getApellidos(),
                            usuarioRegistrado.getCorreo(),
                            usuarioRegistrado.getClave(),
                            usuarioRegistrado.getTelefono(),
                            usuarioRegistrado.getFoto_perfil(),
                            usuarioRegistrado.getCreado_en(),
                            usuarioRegistrado.getRol_id(),
                            usuarioRegistrado.getDni(),
                            usuarioRegistrado.getDireccion(),
                            usuarioRegistrado.getSexo(),
                            usuarioRegistrado.getFechanacimiento()
                    );
                    UsuarioDao usuarioDao = AppDatabase.getInstance(getApplicationContext()).usuarioDao();
                    new Thread(() -> usuarioDao.insert(entity)).start();
                    Intent iSesion = new Intent(getApplicationContext(), SesionActivity.class);
                    startActivity(iSesion);
                    finish();
                    Toast.makeText(getApplicationContext(), "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método auxiliar para texto
    private RequestBody toPart(String valor) {
        return RequestBody.create(MediaType.parse("text/plain"), valor);
    }




    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    public void mostrar(String mensaje){
        Toast.makeText(RegistroActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
