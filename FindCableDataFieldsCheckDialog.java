package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Rect;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class FindCableDataFieldsCheckDialog extends Dialog {

    private Button cfmbtn ;
    private TextView ErrorMessageTxt ;

    public FindCableDataFieldsCheckDialog (@NonNull Context context ,
                                           String fields ,
                                           FindCablefields fieldsobj )
    {

        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.findcabledatafieldsdialog);


        initView(fieldsobj);
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // this method which is responsible for handling the dialog's status
        // 判断触摸事件的类型和触摸位置

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect dialogRect = new Rect();

            getWindow().getDecorView().getHitRect(dialogRect);

            if (!dialogRect.contains((int) event.getX(), (int) event.getY())) {

                // 在 Dialog 外部区域事件
                // 执行某些操作

                dismiss();  // close the dialog
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView(FindCablefields obj) {

        // this function which initializes those components in dialog

        cfmbtn = findViewById(R.id.btn_confirminputip);
        ErrorMessageTxt = findViewById(R.id.errormsgtxt) ;  // Error message text

        String AllFileds = "全部欄位皆未輸入!" ;
        String f1Error   = "機房名稱未輸入\n" ;
        String f2Error   = "纜線名稱未輸入\n" ;
        String f3Error   = "裝置名稱未輸入\n" ;
        String f4Error   = "埠號未輸入\n";

        // lenOfidcRoom, lenOfcableNanme , lenOfdeviceName, lenOfportNo //
        int result = obj.Getf1()*1000 + obj.Getf2()*100 + obj.Getf3()*10 + obj.Getf4() ;
        Log.d(TAG,"Result  : " + Integer.toString(result));

        if ( Integer.toString(result).equals("1111") ) {

            Log.d(TAG,"資料全部齊全") ;
        }
        else {

            Log.d(TAG,"資料有誤");

        }


        /*
        switch (result) {

            case 0: ErrorMessageTxt.setText(AllFileds);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1: ErrorMessageTxt.setText(f1Error+f2Error+f3Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break ;
            case 10: ErrorMessageTxt.setText(f1Error+f2Error+f4Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break ;
            case 11: ErrorMessageTxt.setText(f1Error+f2Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break ;
            case 100: ErrorMessageTxt.setText(f1Error+f3Error+f4Error) ;
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 101: ErrorMessageTxt.setText(f1Error+f3Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 110: ErrorMessageTxt.setText(f1Error+f3Error+f4Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 111: ErrorMessageTxt.setText(f1Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1000: ErrorMessageTxt.setText(f2Error+f3Error+f4Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break ;
            case 1001: ErrorMessageTxt.setText(f2Error+f3Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break ;
            case 1010: ErrorMessageTxt.setText(f2Error+f4Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1011: ErrorMessageTxt.setText(f2Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1100: ErrorMessageTxt.setText(f3Error+f4Error);
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1110: ErrorMessageTxt.setText(f4Error);S
                ErrorMessageTxt.setTextColor(Color.parseColor("#D32A32"));   // red error message
                ErrorMessageTxt.setTextSize(25);  // 25
                break;
            case 1111: {

                // Intent intent = new Intent(getContext() , SearchCableDatarfid.class);
                // getContext().startActivity(intent);

            }
            default:
                break;

        }

         */

    }
    private void addListener(Context context)    {

        //  the confirmation button click event listener
        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ChangePassword1.mChangePassword1.finish();   // close before activity , avoid it still exists in stack

               //  Toast.makeText(context, "正確 !", Toast.LENGTH_SHORT).show();
                dismiss();   // close the dialog

                if (false) {
                    Intent intent = new Intent(v.getContext(), FindCableData.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

    }
}
