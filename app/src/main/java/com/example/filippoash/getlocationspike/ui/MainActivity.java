package com.example.filippoash.getlocationspike.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filippoash.getlocationspike.R;
import com.example.filippoash.getlocationspike.di.component.ApiComponent;
import com.example.filippoash.getlocationspike.di.component.DaggerApiComponent;
import com.example.filippoash.getlocationspike.di.component.DaggerLocationProviderComponent;
import com.example.filippoash.getlocationspike.di.component.DaggerNetworkComponent;
import com.example.filippoash.getlocationspike.di.module.ApiModule;
import com.example.filippoash.getlocationspike.di.module.LocationProviderModule;
import com.example.filippoash.getlocationspike.di.module.NetworkModule;
import com.example.filippoash.getlocationspike.model.LocationResponse;
import com.example.filippoash.getlocationspike.model.Results;
import com.example.filippoash.getlocationspike.service.LocationApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MIN_TIME = 1;
    private static final int MIN_DISTANCE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOCATION_REQUEST = 100;

    @Inject LocationManager mLocationManager;
    @Inject LocationApiService mService;

    private EditText mLocationDisplay;
    private Button mGetLocation;
    private TextView mAddressLine;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resolveDependency();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationDisplay = (EditText) findViewById(R.id.locationDisplay);
        mAddressLine = (TextView) findViewById(R.id.addressLine);
        mGetLocation = (Button) findViewById(R.id.getLocation);
        mGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectLocation();
            }
        });
    }

    private void fetchLocation(Call<LocationResponse> service) {
        service.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful()) {

                    LocationResponse locationResponse = response.body();
                    Results[] results = locationResponse.getResults();
                    mAddressLine.setText(results[0].getFormatted_address());
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                //mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setTitle("Please wait");
        mDialog.setMessage("Detecting Location...");
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(true);
        mDialog.show();
    }

    private void detectLocation() {
        showDialog();
        configLocationManager();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void configLocationManager() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        } else {
            enableLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if((requestCode == LOCATION_REQUEST) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            enableLocationUpdates();
        }
    }

    private void enableLocationUpdates() {
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, mListener);
        if (mLocationManager != null) {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
//                mLocationDisplay.setText(String.format(Locale.UK, "%d-%d", location.getLatitude(), location.getLongitude()));
                //mLocationDisplay.setText("["+location.getLatitude() +"] [" +location.getLongitude()+"]");
            }
        }
    }

    private void resolveDependency() {
        DaggerLocationProviderComponent
                .builder()
                .apiComponent(getApiComponent())
                .locationProviderModule(new LocationProviderModule(this))
                .build()
                .inject(this);
    }

    private ApiComponent getApiComponent() {
        return DaggerApiComponent.builder()
                .apiModule(new ApiModule())//This line can be removed because it's a default constructor
                .networkModule(new NetworkModule("http://maps.googleapis.com"))
                .build();
    }

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocationDisplay.setText("["+location.getLatitude() +"] [" +location.getLongitude()+"]");

            Call<LocationResponse> service = mService.getAddressesResponse(location.getLatitude() +","+location.getLongitude(), false);
            fetchLocation(service);

//            mLocationDisplay.setText(String.format("%d-%d", location.getLatitude(), location.getLongitude()));
            mDialog.dismiss();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged -> " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled -> " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            mDialog.dismiss();
            Toast.makeText(MainActivity.this, "Your Location Providers are disabled!", Toast.LENGTH_SHORT).show();
        }
    };
}
