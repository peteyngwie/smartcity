package com.tra.loginscreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import static com.tra.loginscreen.DataManipulation2.TAG;
        import android.app.ActivityOptions;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.transition.Explode;
        import android.transition.Slide;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.annotation.NonNull;
        import com.tra.loginscreen.R;
        import com.tra.loginscreen.opmonitor;
        import com.tra.loginscreen.transmissionunit;

public class DataBaseErrorDialog extends Dialog {

    private EditText editcablename ,   // cable name
            editroomname  ,   // room name
            editcore      ,   //  core
            editequname    ;  // equipment name


    private TextView Msgtxt ;
    // private RadioGroup classification_rg;
    // private RadioButton rb1, rb2;
    private Button cfmbtn ;
    private Button cnlbtn ;

    public DataBaseErrorDialog (@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.dberrordialog);

        initView();
        // addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog


        if (false) {
            cfmbtn = findViewById(R.id.confirmbtn);
            cnlbtn = findViewById(R.id.cancelbtn);
            editcablename = findViewById(R.id.editcablename);
            editroomname = findViewById(R.id.editroomname);
            editcore = findViewById(R.id.editcore);
            editequname = findViewById(R.id.editequname);
        }

        Msgtxt = (TextView)findViewById(R.id.msgtxt);    // Get database error message text
        Msgtxt.setText("資料表建立失敗 !");

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener


        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the following codes are responsible for query those data via fields combination
                if ( editcablename.getText().toString() == ""  ||
                        editroomname.getText().toString()  == ""  ||
                        editequname.getText().toString()   == ""  ) {
                    Toast.makeText(context, "輸入錯誤 !", Toast.LENGTH_SHORT).show();
                }
                else {


                    // here , it will handle those query data
                    /*
                    cablename   =  (String)editcablename.getText().toString();
                    roomname    =  (String)editroomname.getText().toString() ;
                    core        =  (String)editcore.getText().toString();
                    equipment   =  (String)editequname.getText().toString() ;
                     */
                    // The following are Query conditions via cable name , room name , core and equipment name
                    // First , you must open the database
                    // Second , put query statements for querying

                }

                Intent intent =new Intent(v.getContext() , SearchCableData.class) ;
                v.getContext().startActivity(intent);
                // Toast.makeText(context, "VVVVVVVVVVVVVVVVVVVVVV", Toast.LENGTH_SHORT).show();
            }
        });

        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // close current dialog

            }
        });
        // cancel button click event listener

    }
}
