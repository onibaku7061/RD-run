package rd.natakorn.rdrun;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    //Explicit
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString , passwordString;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

            // Load Image from Server

        Picasso.with(this).load("http://swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150,150).into(imageView);


    }//  Maim Method


    // Create Inner Class
    private class SynUser extends AsyncTask



    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("")|| passwordString.equals("")) {
        } else {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.doremon48, "You Have Space",
               "Please FillAll Every Bank");

        }

    }

    // Get Event from Click Button
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void clickSignIn(View view) {

    }



}//  Main Class
