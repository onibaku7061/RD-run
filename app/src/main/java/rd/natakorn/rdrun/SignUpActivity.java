package rd.natakorn.rdrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit ประกาศตัวแปร
    private EditText nameEditText ,surnameEditText ,userEditText , passEditText;
    private RadioGroup radioGroup;
    private RadioButton avta1RadioButton,avata2RadioButton,avata3RadioButton,avata4RadioButton, avata5RadioButton;
    private String nameString,surnameString,userString, passwordSting, avataString;


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
        avta1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);


        // Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

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
        }

    }  // click SignUp

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
