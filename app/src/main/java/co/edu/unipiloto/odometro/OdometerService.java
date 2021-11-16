package co.edu.unipiloto.odometro;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;

import java.util.Random;

public class OdometerService extends Service {
    private final IBinder binder=new OdometerBinder();

    //Número aleatorio para probar el método obtener distancia
    private final Random random= new Random();

    private LocationListener listener;

    //Crear un gestor de localización
    LocationManager locManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

    //Especificar un proveedor de servicio
    String provider=locManager.getBestProvider(new Criteria(),true);

    public OdometerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
            @Override
            public void onProviderDisabled(String arg0){

            }
            @Override
            public void onProviderEnabled(String arg0){

            }
            @Override
            public void onStatusChanged(String arg0,int arg1, Bundle bundle){

            }
        };

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class OdometerBinder extends Binder{
        OdometerService getOdometer(){
            return OdometerService.this;
        }
    }

    public double getDistance(){
        return random.nextDouble();
    }


}