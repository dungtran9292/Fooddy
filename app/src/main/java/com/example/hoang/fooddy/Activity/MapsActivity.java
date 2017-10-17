package com.example.hoang.fooddy.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hoang.fooddy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mGoogleMap;
    Spinner spinner;
    ArrayList<String> list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner = (Spinner) findViewById(R.id.spinner);
        list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        adapter = new ArrayAdapter(MapsActivity.this,android.R.layout.simple_spinner_item,list);
        spinner.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }else if (position == 1){
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else if (position == 2) {
                    mGoogleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    MapsActivity.this, R.raw.style_json));
                    //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }else if (position == 3){
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(MapsActivity.this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
        double[] position = getLocation();
        LatLng sydney = new LatLng(position[0], position[1]);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
    }

    protected double[] getLocation() {
        double[] latlong = new double[2];
        Log.d("dungtran isMyLocationEnabled ", mGoogleMap.isMyLocationEnabled() + "");
        if (mGoogleMap.isMyLocationEnabled()) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
            Location location = null;
            //You can still do this if you like, you might get lucky:
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(bestProvider);
            }
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latlong[0] = location.getLatitude();
                latlong[1] = location.getLongitude();
                Toast.makeText(MapsActivity.this, "latitude:" + latlong[0] + " longitude:" + latlong[1], Toast.LENGTH_SHORT).show();
            } else {
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
//        } else {
//            //prompt user to enable location....
//            //.................
//        }
        return latlong;
    }


    public void searchNearestPlace(String v2txt, double lat, double longpo) {
        v2txt = v2txt.toLowerCase();
        String[] placesS = {"accounting", "airport", "aquarium", "atm", "attraction", "bakery", "bakeries", "bank", "bar", "cafe", "campground", "casino", "cemetery", "cemeteries", "church", "courthouse", "dentist", "doctor", "electrician", "embassy", "embassies", "establishment", "finance", "florist", "food", "grocery", "groceries", "supermarket", "gym", "health", "hospital", "laundry", "laundries", "lawyer", "library", "libraries", "locksmith", "lodging", "mosque", "museum", "painter", "park", "parking", "pharmacy", "pharmacies", "physiotherapist", "plumber", "police", "restaurant", "school", "spa", "stadium", "storage", "store", "synagog", "synagogue", "university", "universities", "zoo"};
        String[] placesM = {"amusement park", "animal care", "animal care", "animal hospital", "art gallery", "art galleries", "beauty salon", "bicycle store", "book store", "bowling alley", "bus station", "car dealer", "car rental", "car repair", "car wash", "city hall", "clothing store", "convenience store", "department store", "electronics store", "electronic store", "fire station", "funeral home", "furniture store", "gas station", "general contractor", "hair care", "hardware store", "hindu temple", "home good store", "homes good store", "home goods store", "homes goods store", "insurance agency", "insurance agencies", "jewelry store", "liquor store", "local government office", "meal delivery", "meal deliveries", "meal takeaway", "movie rental", "movie theater", "moving company", "moving companies", "night club", "pet store", "place of worship", "places of worship", "post office", "real estate agency", "real estate agencies", "roofing contractor", "rv park", "shoe store", "shopping mall", "subway station", "taxi stand", "train station", "travel agency", "travel agencies", "veterinary care"};
        int index;
        for (int i = 0; i <= placesM.length - 1; i++) {
            Log.e("TAG", "forM");
            if (v2txt.contains(placesM[i])) {
                Log.e("TAG", "sensedM?!");
                index = i;
                Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + longpo + "?q=" + placesM[index]);
                Log.d("dungtran place", "geo:" + lat + "," + longpo + "?q=" + placesM[index]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                finish();
            }
        }
        for (int i = 0; i <= placesS.length - 1; i++) {
            Log.e("TAG", "forS");
            if (v2txt.contains(placesS[i])) {
                index = i;
                Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + longpo + "?q=" + placesS[index]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                finish();
            }
        }
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
