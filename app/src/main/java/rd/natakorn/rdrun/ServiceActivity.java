package rd.natakorn.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    // Explicit

    private GoogleMap mMap;
    private String avataString,nameString,idString, surnameString;
    private ImageView imageView;
    private TextView nameTextView, surnameTextView;
    private int[] avataInts;
    private double userLatADouble = 13.806919, userLngADouble = 100.574833; //The Connection
    private LocationManager locationManager;
    private Criteria criteria;
    private static final String urlPHP = "http://swiftcodingthai.com/rd/edit_location_oni.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sevie);
        // Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextView = (TextView) findViewById(R.id.textView8);
        surnameTextView = (TextView) findViewById(R.id.textView9);

        // Set Location Services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        // Get Value From Intent
        idString = getIntent().getStringExtra("id");
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");
        avataString = getIntent().getStringExtra("Avata");

        // Show Text
        nameTextView.setText(nameString);
        surnameTextView.setText(surnameString);

        // show Avata
        MyConstant myConstant = new MyConstant();
        avataInts = myConstant.getAvataInts();
        imageView.setImageResource(avataInts[Integer.parseInt(avataString)]);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }  // Main Method

    private class SynAllUser extends AsyncTask<Void, Void, String>{
        //  Explicit
        private Context context;
        private GoogleMap googleMap;
        private static final String urlJSON = ("http://swiftcodingthai.com/rd/get_user_master.php");


        public SynAllUser(Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("2SepV3", " e doIN ==> " + e.toString());
                return null;
            }

        } // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("2SepV2", " JSON ==> " + s);


        }//OnPost

    }   // SynAllUser Class



    @Override
    protected void onResume() {
        super.onResume();
        locationManager.removeUpdates(locationListener);
        Location networLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networLocation!= null) {
            userLngADouble = networLocation.getLatitude();
            userLngADouble = networLocation.getLongitude();
        }
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }

    }// Onresume


    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);


    }

    public Location myFindLocation(String strProvider) {

        Location location = null;
        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider,1000,10,locationListener);///
            location = locationManager.getLastKnownLocation(strProvider);


        } else {
            Log.d("1SepV1", "Cannot find Location");
        }


        return location;
    }


    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }   // onlocation Change


        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    // Setup Center Map
        LatLng latLng = new LatLng(userLatADouble, userLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

        // Loop
        myLoop();


    }   // OnMap

    private void myLoop() {
        // To Do
        Log.d("1SepV2", " Lat == > " + userLatADouble);
        Log.d("1SepV2", "Lng ==> " + userLngADouble);

        editLatLngOnServer();

        creatMarker();

         // Post
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },1000);



    }// Myloop

    private void creatMarker() {

        SynAllUser synAllUser = new SynAllUser(this,mMap);
        synAllUser.execute();

    }  //  Create Marker


    private void editLatLngOnServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idString)
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("2SepV1", "e ==> " + e.toString());

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("2SepV1", " Result ==> " + response.body().string());
            }
        });



    }  // edit Lat Lng


}  // Main Class

