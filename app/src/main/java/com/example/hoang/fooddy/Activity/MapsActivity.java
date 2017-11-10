package com.example.hoang.fooddy.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.fooddy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import static com.example.hoang.fooddy.R.id.map;
import static com.example.hoang.fooddy.Util.Common.formatNumber;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mGoogleMap;
    LatLng currentPstGPS, positionChange , positionChangeGPS;
    GPSTracker gps;
    Marker markerGPS, markerGoogle , markerMatram;

    //

    TextView ma_tram, kd_tram ,vd_tram,kd_gps,vd_gps,kd_gg,vd_gg,distance_gg,distance_gps,txt_date;
    LinearLayout rl_dialog_info_type;
    ImageView btn_close;


    private static final String TAG = MapsActivity.class.getSimpleName();
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    LatLng matram;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        initView();
        gps = new GPSTracker(this);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MapsActivity.this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        ma_tram = (TextView) findViewById(R.id.ma_tram);
        vd_tram = (TextView) findViewById(R.id.vd_tram);
        kd_tram = (TextView) findViewById(R.id.kd_tram);
        vd_gps = (TextView) findViewById(R.id.vd_gps);
        kd_gps = (TextView) findViewById(R.id.kd_gps);
        distance_gps = (TextView) findViewById(R.id.distance_gps);
        distance_gg = (TextView) findViewById(R.id.distance_gg);
        vd_gg = (TextView) findViewById(R.id.vd_gg);
        kd_gg = (TextView) findViewById(R.id.kd_gg);
        rl_dialog_info_type = (LinearLayout) findViewById(R.id.rl_dialog_info_type);
        btn_close = (ImageView) findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (mGoogleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mGoogleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        final Handler handler = new Handler();
        matram = new LatLng(21.004245513724932, 105.81379705667496);
        mGoogleMap.setOnMyLocationChangeListener(myLocationChangeListener);


        if (gps == null) {
            gps = new GPSTracker(MapsActivity.this);
        }
        LocationManager mlocManager = (LocationManager) MapsActivity.this
                .getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = mlocManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (enabled) {
            if (gps.getLatitude() == 0 && gps.getLongitude() == 0) {
                return;
            } else {
                currentPstGPS = new LatLng(gps.getLatitude(), gps.getLongitude());
            }
        }
        mGoogleMap.setOnMarkerClickListener(this);
        markerMatram =  mGoogleMap.addMarker(new MarkerOptions().position(matram).title("Ma Tram"));
        markerGPS = mGoogleMap.addMarker(new MarkerOptions().position(currentPstGPS).title("GPS "));

        Runnable run = new Runnable() {
            @Override
            public void run() {
                if (gps != null) {
                    positionChangeGPS = new LatLng(gps.getLatitude(), gps.getLongitude());
                    Log.d("dungtran positionChangeGPS" , positionChange.latitude + " "+positionChangeGPS.longitude );
                    handler.postDelayed(this,GPSTracker.MIN_TIME_BW_UPDATES);
                }
            }
        };
        handler.postDelayed(run,5000);



        Log.d("dungtran currentPstGPS",gps.getLatitude()+" "+gps.getLongitude() );
        Polyline gpsTracker = mGoogleMap.addPolyline(new PolylineOptions()
                .add(currentPstGPS, matram)
                .width(5)
                .color(Color.BLUE));


        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();



    }
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
             positionChange = new LatLng(location.getLatitude(),
                    location.getLongitude());
//            if(mGoogleMap != null){
//                mGoogleMap.animateCamera(
//                        CameraUpdateFactory.newLatLngZoom(positionChange, 16.0f));
//            }

            Log.d("dungtran positionChange",positionChange.latitude + " "+positionChange.longitude);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    /**
     * Handles a click on the menu option to get a place.
     *
     * @param item The menu item to handle.
     * @return Boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            showCurrentPlace();
        }
        return true;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Log.d(TAG, "Current location is null. Using defaults. 1");
                            mLastKnownLocation = task.getResult();
                            markerGoogle = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(),
                                   mLastKnownLocation.getLongitude())).title("Google"));
                            Polyline googleAPI = mGoogleMap.addPolyline(new PolylineOptions()
                                    .add(new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), matram)
                                    .width(5)
                                    .color(Color.RED));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            markerGoogle = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude())).title("Google"));
                            mGoogleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void showCurrentPlace() {
        if (mGoogleMap == null) {
            Log.d(TAG, "mGoogleMap == null");
            return;
        }
        Log.d(TAG, "mGoogleMap != null");
        if (mLocationPermissionGranted) {
            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final
            Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                            Log.d(TAG, "onComplete 1: %" + task.isSuccessful());
                            if (task.isSuccessful() && task.getResult() != null) {
                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                                Log.d(TAG, "onComplete 2: %");
                                // Set the count, handling cases where less than 5 entries are returned.
                                int count;
                                if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                    count = likelyPlaces.getCount();
                                } else {
                                    count = M_MAX_ENTRIES;
                                }

                                int i = 0;
                                mLikelyPlaceNames = new String[count];
                                mLikelyPlaceAddresses = new String[count];
                                mLikelyPlaceAttributions = new String[count];
                                mLikelyPlaceLatLngs = new LatLng[count];

                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                    // Build a list of likely places to show the user.
                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
                                            .getAddress();
                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
                                            .getAttributions();
                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                                    i++;
                                    if (i > (count - 1)) {
                                        break;
                                    }
                                }

                                // Release the place likelihood buffer, to avoid memory leaks.
                                likelyPlaces.release();

                                // Show a dialog offering the user the list of likely places, and add a
                                // marker at the selected place.
                                openPlacesDialog();

                            } else {
                                Log.d(TAG, "Exception: %s", task.getException());
                            }
                        }
                    });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mGoogleMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The "which" argument contains the position of the selected item.
                LatLng markerLatLng = mLikelyPlaceLatLngs[which];
                String markerSnippet = mLikelyPlaceAddresses[which];
                if (mLikelyPlaceAttributions[which] != null) {
                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
                }

                // Add a marker for the selected place, with an info window
                // showing information about that place.
                mGoogleMap.addMarker(new MarkerOptions()
                        .title(mLikelyPlaceNames[which])
                        .position(markerLatLng)
                        .snippet(markerSnippet));

                // Position the map's camera at the location of the marker.
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                        DEFAULT_ZOOM));
            }
        };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.pick_place)
                .setItems(mLikelyPlaceNames, listener)
                .show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(MapsActivity.this, "onLocationChanged :", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(markerGoogle)) {
            double distanceGPS = SphericalUtil.computeDistanceBetween(markerGoogle.getPosition(), markerMatram.getPosition());
            Toast.makeText(MapsActivity.this, "Distances Places: " + formatNumber(distanceGPS), Toast.LENGTH_SHORT).show();
            Log.d("dungtran Distance GG ",formatNumber(distanceGPS)+"");
        }else if (marker.equals(markerGPS)) {
            double distancePlace = SphericalUtil.computeDistanceBetween(markerGPS.getPosition(), markerMatram.getPosition());
            Toast.makeText(MapsActivity.this, "Distances GPS: " + formatNumber(distancePlace), Toast.LENGTH_SHORT).show();
            Log.d("dungtran Distance  GPS", formatNumber(distancePlace)+"");
        }else if (marker.equals(markerMatram)) {
            showPoPup();
        }
        return false;
    }

    private void showPoPup() {
       // vido : lat , kinhdo : long


        matram = new LatLng(21.004245513724932, 105.81379705667496);
        vd_tram.setText("21.0042455137");
        kd_tram.setText("105.813797056");
        // gps
        if (positionChangeGPS != null){
            double distanceGPS = SphericalUtil.computeDistanceBetween(positionChangeGPS, markerMatram.getPosition());
            vd_gps.setText(positionChangeGPS.latitude+"");
            kd_gps.setText(positionChangeGPS.longitude+"");
            distance_gps.setText(formatNumber(distanceGPS)+"");
        }else {
            double distanceGPS = SphericalUtil.computeDistanceBetween(markerGPS.getPosition(), markerMatram.getPosition());
            vd_gps.setText(markerGPS.getPosition().latitude+"");
            kd_gps.setText(markerGPS.getPosition().longitude+"");
            distance_gps.setText(formatNumber(distanceGPS)+"");
        }

        // plcase api
        if (positionChange != null) {
            vd_gg.setText(positionChange.latitude+"");
            kd_gg.setText(positionChange.longitude+"");
            double distanceGG = SphericalUtil.computeDistanceBetween(positionChange, markerMatram.getPosition());
            distance_gg.setText(formatNumber(distanceGG)+"");
        }else {
            vd_gg.setText(markerGoogle.getPosition().latitude+"");
            kd_gg.setText(markerGoogle.getPosition().longitude+"");
            double distanceGG = SphericalUtil.computeDistanceBetween(markerGoogle.getPosition(), markerMatram.getPosition());
            distance_gg.setText(formatNumber(distanceGG)+"");
        }

        TranslateAnimation anim = new TranslateAnimation( 0, 0 , 1000, 0 );
        anim.setDuration(500);
        anim.setFillAfter( true );
        rl_dialog_info_type.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rl_dialog_info_type.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void dismissDialog(){
        TranslateAnimation anim = new TranslateAnimation( 0, 0 , 0, 2000 );
        anim.setDuration(200);
        anim.setFillAfter( true );
        rl_dialog_info_type.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_dialog_info_type.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
