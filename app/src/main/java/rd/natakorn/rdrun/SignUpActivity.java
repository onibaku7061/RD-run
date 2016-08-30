package rd.natakorn.rdrun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicit ประกาศตัวแปร
    private EditText nameEditText ,surnameEditText ,userEditText , passEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton,avata2RadioButton,avata3RadioButton,avata4RadioButton, avata5RadioButton;
    private String nameString,surnameString,userString, passwordSting, avataString;
    private static final String urlPHP = "http://swiftcodingthai.com/rd/add_user_oni.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind or Initail Widget การผูกความสัมพันธ์ระว่างตัวแปร กับ Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText4);
        passEditText = (EditText) findViewById(R.id.editText2);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvatar);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);


        // Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radioButton:
                        avataString = "0";
                        break;
                    case R.id.radioButton2:
                        avataString = "1";
                        break;
                    case R.id.radioButton3:
                        avataString = "2";
                        break;
                    case R.id.radioButton4:
                        avataString = "3";
                        break;
                    case R.id.radioButton5:
                        avataString = "4";
                        break;

                }

            }
        });


    }  //main method

    public void clickSignUpSign(View view) {
    // get value from edit text
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordSting = passEditText.getText().toString().trim();

        // check space

        if (checkSpace()) {

            // true
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.nobita48,"กรอกข้อมูลไม่ครบ","กรุณากรอกข้อมูลให้ครบถ้วน");
        } else if (checkChoose()) {
            // true ==> have Choose
            confirmValue();
        } else {
            // true ==> Non Choose
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.bird48,"ยังไม่เลือก Avata","กรุณาเลือก Avata ด้วยครัย");
        }

    }  // click SignUp

    private void confirmValue() {

        MyConstant myConstant = new MyConstant();
        int[] avataInts = myConstant.getAvataInts();



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(avataInts[Integer.parseInt(avataString)]);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        builder.setMessage("Name = " + nameString + "\n" +
                "Surname = " + surnameString + "\n" +
                "User = " + userString + "\n" +
                "Password = " + passwordSting + "\n");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Confrim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadValueToServer();
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }// confrim Value

    private void uploadValueToServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", passwordSting)
                .add("Avata", avataString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    }// Upload to Server


    private boolean checkChoose() {

        boolean result = false;

        if (avata1RadioButton.isChecked() ||
                avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() ||
                avata4RadioButton.isChecked() ||
                avata5RadioButton.isChecked()) {
            result = true;
        }


        return result;
    }

    private boolean checkSpace() {

        boolean result = false;

        if (    nameString.equals("")||
                surnameString.equals("")||
                userString.equals("")||
                passwordSting.equals("")) {
            result = true;
        }

        return result;

    }

} //main class
