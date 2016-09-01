package rd.natakorn.rdrun;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {

    // Explicit

    private GoogleMap mMap;
    private String avataString,nameString,idString, surnameString;
    private ImageView imageView;
    private TextView nameTextView, surnameTextView;
    private int[] avataInts;
    private double userLatADouble = 13.806919, userLngADouble = 100.574833; //The Connection




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sevie);
        // Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextView = (TextView) findViewById(R.id.textView8);
        surnameTextView = (TextView) findViewById(R.id.textView9);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    // Setup Center Map
        LatLng latLng = new LatLng(userLatADouble, userLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));


    }   // OnMap

}  // Main Class

