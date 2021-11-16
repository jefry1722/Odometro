package co.edu.unipiloto.odometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private OdometerService odometer;
    private boolean bound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mostrarDistancia();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent= new Intent(this,OdometerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound){
            unbindService(connection);
        }
    }

    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            OdometerService.OdometerBinder odometerBinder=(OdometerService.OdometerBinder)iBinder;
            bound=true;
            odometer=odometerBinder.getOdometer();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound=false;
        }
    };

    public void mostrarDistancia(){
        final TextView distanceView=(TextView) findViewById(R.id.distance);
        final Handler handler= new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distancia=0.0;
                if(bound && odometer!=null){
                    distancia=odometer.getDistance();
                }
                String distanciaStr=String.format(Locale.getDefault(),"%1$,.2f kilometros",distancia);
                distanceView.setText(distanciaStr);
                handler.postDelayed(this,1000);
            }
        });

    }
}