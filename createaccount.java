package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class createaccount extends AppCompatActivity {

    private EditText accedit , pwdedit , confirmpwdedit ;

    private TextView ErrorTXT ;
    private Button Accaddbtn ;


    private SqliteDatabase mDatabase;
    private ArrayList<Contacts> allContacts=new ArrayList<>();   // allocate all available contacts for use
    private ContactAdapter mAdapter;

    private String account , password ;

    private TextView accErrorTxt , pwdErrorTxt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);  // initialize toolbar

        pwdedit = (EditText)findViewById(R.id.pwdedit);
        accedit = (EditText)findViewById(R.id.accedit);
        confirmpwdedit = (EditText)findViewById(R.id.confirmpwdedit);

        Accaddbtn = (Button)findViewById(R.id.addaccButton);
        accErrorTxt =  (TextView)findViewById(R.id.accperrortxt);
        pwdErrorTxt = (TextView)findViewById(R.id.pwderrorTxt);

        ErrorTXT = (TextView)findViewById(R.id.errortxt);
        ErrorTXT.setText("");

        Accaddbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(createaccount.this, "新增帳號按鈕!", Toast.LENGTH_SHORT).show();

                   account  = accedit.getText().toString();
                   password = pwdedit.getText().toString();

                   if ( account.isEmpty() == true && password.isEmpty() == true ) {

                   }

                if ( pwdedit.getText().toString().compareTo(confirmpwdedit.getText().toString()) == 0 )
                {

                    Toast.makeText(createaccount.this, "輸入正確!", Toast.LENGTH_SHORT).show();
                    mDatabase = new SqliteDatabase(v.getContext());
                    Contacts newContact = new Contacts(account, password);
                    mDatabase.addContacts(newContact);
                    Toast.makeText(createaccount.this, "account :" + account + "\n" + "password :" + password, Toast.LENGTH_SHORT).show();
                    pwdedit.setText("");  // clear password field
                    confirmpwdedit.setText("");  // clear confirmation password field
                    ErrorTXT.setText(""); // password match is correct !

                    // mDatabase = new SqliteDatabase(v.getContext()); // this line must be ignored !

                    allContacts = mDatabase.listContacts();  // getting all accounts from data base

                    if(allContacts.size() > 0){
                        // contactView.setVisibility(View.VISIBLE);
                        mAdapter = new ContactAdapter(v.getContext(), allContacts);
                        Toast.makeText(v.getContext(), "目前已存在"+Integer.toString(allContacts.size())+"筆帳號!", Toast.LENGTH_SHORT).show();
                        // contactView.setAdapter(mAdapter);

                    }else {

                        // contactView.setVisibility(View.GONE); //
                        Toast.makeText(v.getContext(), "目前無任何帳號!", Toast.LENGTH_LONG).show();
                    }

                }
                else {

                    Toast.makeText(createaccount.this, "輸入錯誤!", Toast.LENGTH_SHORT).show();
                    ErrorTXT.setText("輸入密碼不一致!請重新確認輸入");
                    ErrorTXT.setTextColor(Color.parseColor("#D32A32"));
                    pwdedit.setText("");  // clear password field
                    confirmpwdedit.setText("");  // clear confirmation password field

                }

            }
        });

        // hint format : password
        pwdedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmpwdedit.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // when you click the back arrow < , current activity go back home activity
            }
        });  // back action -- < sign

    }
}