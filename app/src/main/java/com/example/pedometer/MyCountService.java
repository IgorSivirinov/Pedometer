package com.example.pedometer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;


public class MyCountService extends IntentService {
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean isPresent;
    int steps1 = 1;
    private boolean was = true;
    private final String TAG = "MyCountService";
    public static final String ACTION_1="MyCountService";
    public MyCountService() {
        super("MyCountService");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager .getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size()>0){ isPresent = true; sensor = sensors.get(0);
    }
    }
    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            if (x>0&&was){steps1++; was = false;}
            if (x<0) was = true;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent=new Intent();
        broadcastIntent.setAction(MyCountService.ACTION_1);
        for(int i=0;i<=100;i++)
        {
            broadcastIntent.putExtra("percel",i);
            sendBroadcast(broadcastIntent);
            SystemClock.sleep(100);
        }

    }


}
