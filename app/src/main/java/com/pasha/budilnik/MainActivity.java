package com.pasha.budilnik;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tvText;
    SensorManager sensorManager;
    List<Sensor> sensors;
//    Sensor sensorLight;
    Sensor sensorStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = (TextView) findViewById(R.id.tvText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public void onClickSensList(View v) {

        sensorManager.unregisterListener(listenerLight, sensorStep);
        StringBuilder sb = new StringBuilder();

        for (Sensor sensor : sensors) {
            sb.append("name = ").append(sensor.getName())
                    .append(", type = ").append(sensor.getType())
                    .append("\nvendor = ").append(sensor.getVendor())
                    .append(" ,version = ").append(sensor.getVersion())
                    .append("\nmax = ").append(sensor.getMaximumRange())
                    .append(", resolution = ").append(sensor.getResolution())
                    .append("\n--------------------------------------\n");
        }
        tvText.setText(sb);
    }

    public void onClickSensStep(View v) {
        sensorManager.registerListener(listenerLight, sensorStep,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorStep);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            tvText.setText(String.valueOf(event.values[0]));
        }
    };

}