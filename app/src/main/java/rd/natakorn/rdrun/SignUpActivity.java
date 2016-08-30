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
    private RadioButton avata1RadioButton,avata2RadioButton,avata3RadioButton,avata4RadioButton, avata5RadioButton;
    private String nameString,surnameString,userString, passwordSting, avataString;

    public SignUpActivity() {
    }


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

    }



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
