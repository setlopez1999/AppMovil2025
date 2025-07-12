package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sonrisasaludable.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String sede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        sede = getIntent().getStringExtra("nombreSede");

        // Botón atrás
        Button btnAtras = findViewById(R.id.btnAtrasMapa);
        btnAtras.setOnClickListener(v -> finish());

        // Cargar el mapa dinámicamente
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ubicacion = null;
        String titulo = "";
        HabilitarOpciones();

        if (sede != null) {
            switch (sede) {
                case "Sede Central":
                    ubicacion = new LatLng(-12.0464, -77.0428); // Ejemplo: Lima
                    titulo = "Sede Central";
                    break;
                case "Sede Norte":
                    ubicacion = new LatLng(-11.928, -77.072); // Ejemplo: Sede Norte
                    titulo = "Sede Norte";
                    break;
                case "Sede Sur":
                    ubicacion = new LatLng(-12.200, -76.936); // Ejemplo: Sede Sur
                    titulo = "Sede Sur";
                    break;
                default:
                    ubicacion = new LatLng(-12.0464, -77.0428);
                    titulo = "Ubicación desconocida";
            }

            // Mostrar marcador
            mMap.addMarker(new MarkerOptions().position(ubicacion).title(titulo));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        }
    }


    private void HabilitarOpciones() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
    }

    public void MostrarMarcador(LatLng ubicacion, String titulo) {
        mMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title(titulo));
    }
/*
    public void MostrarMarcadorConIcono(LatLng ubicacion, String titulo) {
        BitmapDescriptor customIcon = BitmapDescriptorFactory.fromResource(R.drawable.centro);
        mMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title(titulo)
                .icon(customIcon));
    }

 */
}
