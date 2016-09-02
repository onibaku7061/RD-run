package rd.natakorn.rdrun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    //Explicit
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString , passwordString;
    private CheckBox checkBox;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // Load Image from Server

        Picasso.with(this).load("http://swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150,150).into(imageView);


    }//  Maim Method


    // Create Inner Class
    private class SynUser extends AsyncTask<Void, Void, String> {

        //  Explicit
        private Context context;
        private String myUserString, mypasswordString , truePasswordString,
                        nameString ,surnameString ,idString , avataString;
        private static final String urlJSON="http://swiftcodingthai.com/rd/get_user_master.php";
        private boolean statusABoolean = true;


        public SynUser(Context context, String myUserString, String mypasswordString) {
            this.context = context;
            this.myUserString = myUserString;
            this.mypasswordString = mypasswordString;
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
                Log.d("31AugV2", "e doInBack ==> " + e.toString());
                return null;

            }

        }  //  Do In Back

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("31AugV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0;i<jsonArray.length();i+=1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (myUserString.equals(jsonObject.getString("User"))) {

                        statusABoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                        nameString = jsonObject.getString("Name");
                        surnameString = jsonObject.getString("Surname");
                        idString = jsonObject.getString("id");
                        avataString = jsonObject.getString("Avata");
                    }  // Ifse

                }  // For

                if (statusABoolean) {
                    // User Fail
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, R.drawable.nobita48, "User False",
                            "ไม่มี " + myUserString + " อยู่ในฐานข้อมูล");
                } else if (mypasswordString.equals(truePasswordString)) {
                    // Password True
                    if (checkBox.isChecked()) {
                        Log.d("2SepV5", "CheckBox is Checked");

                        MyManage myManage = new MyManage(context);

                    } else {
                        Log.d("2SepV5", "CheckBox is UnChecked");
                    }

                    Intent intent = new Intent(MainActivity.this,ServiceActivity.class);

                    intent.putExtra("id", idString);
                    intent.putExtra("Avata", avataString);
                    intent.putExtra("Name", nameString);
                    intent.putExtra("Surname", surnameString);

                    startActivity(intent);


                    Toast.makeText(context,"Wellcome "+ nameString+" "+surnameString,Toast.LENGTH_SHORT).show();

                } else {
                    // Password false

                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context,R.drawable.doremon48,"Password Flase", "Please Try Again");

                }


            } catch (Exception e) {
                Log.d("31AugV2", "e onPost ==>" + e.toString());

            }

        }  // On Post

    }  // SynUser Class



    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("")|| passwordString.equals("")) {
            // cHave Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.doremon48, "You Have Space",
                    "Please FillAll Every Bank");

        } else {

            //Non Space
            SynUser synUser = new SynUser(this, userString, passwordString);
            synUser.execute();


        }

    }

    // Get Event from Click Button
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void clickSignIn(View view) {

    }



}//  Main Class
