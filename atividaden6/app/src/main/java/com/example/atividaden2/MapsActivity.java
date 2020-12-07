package com.example.atividaden2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener {

    private GoogleMap mMap;
    private SensorManager sensorManager;
    Marker marker_1;
    String paisretornado ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle retornpais = getIntent().getExtras();

        String v="";
        String v1="";
        String value ="";

          //  double v=0;
          //  double v1=0;
        if(retornpais != null){
            value = (String) retornpais.get("latlng");
            paisretornado = (String) retornpais.get("pais");
        }
        System.out.println("#################### value:"+value);

        String value2 = value.replace("[", "");
        String value3 = value2.replace("]", "");
        String[] separated  = value3.split(",");

        v = separated[0];
        v1 = separated[1];

       double v2 =Double.parseDouble(v);
       double v3 =Double.parseDouble(v1);
        // Add a marker in Sydney and move the camera
        LatLng pais = new LatLng(v2, v3);

        mMap.addMarker(new MarkerOptions().position(pais).title("Marker in "+ paisretornado));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pais));
    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }
    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        double velocidade = Math.sqrt(x*x+y*y+z*z);
        if(velocidade >= 30){
            velocidade=0;
            Intent paisclicado =new Intent(MapsActivity.this, FotoClass.class);

            paisclicado.putExtra("paisclicado",paisretornado);

            startActivity(paisclicado);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}