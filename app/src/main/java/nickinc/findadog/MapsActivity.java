package nickinc.findadog;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected static ArrayList<LatLng> locations = new ArrayList<>();
    protected static ArrayList<String> breeds = new ArrayList<>();
    static GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    String s;
   // LatLng newLoc;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Go to other screen with My Dogs listed

        return true;
    }//end onOptionsItemSelected

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0]  == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        s = intent.getStringExtra("Dog breed");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (locations.size() > 0) {
            for (int i = 0; i<locations.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(locations.get(i)).title(breeds.get(i)));
            }
        }

        if(s != null) {
            System.out.println("In if....");
            makeNewMarker(s);
        }



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                ListActivity.newLoc = new LatLng(latLng.latitude, latLng.longitude);
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               // mMap.clear();
                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.US);
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0 ) {

                        String stf = addresses.get(0).getSubThoroughfare();
                        String tf = addresses.get(0).getThoroughfare();
                        String locality = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String postalcode = addresses.get(0).getPostalCode();
                        String cn = addresses.get(0).getCountryName();
                        String address = stf + " " + tf + "\n" + locality +", " +
                                state + ", " + postalcode + "\n" + cn;
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();

                    }//if
                }//try
                catch (Exception e ) {e.printStackTrace();}



            }//onLocationChanged

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23 ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng myLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
               // mMap.clear();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));
            }
        }
    }//onMapReady

    public void makeNewMarker(String dogName) {
        System.out.println("In makeNewMarker...");
        System.out.println(ListActivity.newLoc.toString());
        LatLng loc = ListActivity.newLoc;
        System.out.println(dogName);
        mMap.addMarker(new MarkerOptions().position(loc).title(dogName));
        locations.add(loc);
        breeds.add(dogName);
    }
}
