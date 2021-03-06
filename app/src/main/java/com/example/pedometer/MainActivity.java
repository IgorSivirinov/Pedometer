package com.example.pedometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.Cache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean binded=false;
    private String steps;
    private Thread thread;
    private TextView stepsOut;
    private ProgressBar progressBar;
    private CountService countService;
    private ImageButton ibu,ibu2;
    private FragmentManager fragmentManager;
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
        progressBar=findViewById(R.id.pb);

            ibu = findViewById(R.id.ibu);
            ibu2 = findViewById(R.id.ibu2);
            ibu.setImageResource(R.drawable.home);
            ibu2.setImageResource(R.drawable.gear);
            ibu.setOnClickListener(this);
            ibu2.setOnClickListener(this);
            thread=new Thread (new AnotherRunnable());
            thread.start();
//
//            fragmentManager=getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            FragmentHome fragment = new FragmentHome();
//            fragmentTransaction.add(R.id.container, fragment);
//            fragmentTransaction.commit();
    }
    class AnotherRunnable implements Runnable{

        @Override
        public void run() {
            while(!thread.isInterrupted())
                try{ Ti();} catch (Exception e) { e.printStackTrace(); }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void Ti(){
            progressBar.setProgress(countService.getSteps());
           steps = Integer.toString(countService.getSteps());
            if (!(stepsOut.getText().equals(steps))){
            stepsOut.setText(steps);}

    }
    @Override
    public void onClick(View v) {
//        Fragment fragment=null;
//        switch (v.getId()){
//            case R.id.ibu:
//                fragment = new FragmentHome();break;
//            case R.id.ibu2:
//                fragment = new FragmentGear();break;
//        }
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.commit();
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
