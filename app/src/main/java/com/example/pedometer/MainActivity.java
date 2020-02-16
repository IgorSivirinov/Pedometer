package com.example.pedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean binded=false;
    private TextView stepsOut;
    private CountService countService;
    private ImageButton ibu,ibu2;

    ServiceConnection countserviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CountService.LocalCountBinder binder=(CountService.LocalCountBinder)service;
            countService=binder.getService();
            binded=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepsOut = findViewById(R.id.steps);
        ibu=findViewById(R.id.ibu);
        ibu2=findViewById(R.id.ibu2);
        ibu.setImageResource(R.drawable.home);
        ibu2.setImageResource(R.drawable.gear);
    }


    @Override
    protected void onResume() {
        super.onResume();
            try {
                String steps0 = String.valueOf(countService.getSteps());
                stepsOut.setText(steps0);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,CountService.class);
        startService(intent);
        this.bindService(intent,countserviceConnection,Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(binded){
            this.unbindService(countserviceConnection);
            binded=false;
        }
    }
}
