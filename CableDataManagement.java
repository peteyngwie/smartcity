package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import static com.tra.loginscreen.DataManipulation2.TAG;

public class CableDataManagement extends Dialog {

    private TextView tvTempValue, tvHumiValue, tvLightValue;

    private RadioGroup classification_rg;
    private RadioButton rb1, rb2 ,rb3 , rb4 , rb5, rb6, rb7, rb8 ;
    private Button cfmbtn, cnlbtn;

    public CableDataManagement(@NonNull Context context) {
        super(context, R.style.dialog_style);
        //关联布局文件
        this.setContentView(R.layout.cabledatamanagementdialog1);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initView();
        addListener(context);   // define all listeners of component

    }  // the definition of constructor

    private void initView() {

        // this function which initializes those components in dialog
        cfmbtn = findViewById(R.id.confirm);
        cnlbtn = findViewById(R.id.cancel);

        // options creation
        classification_rg= (RadioGroup) findViewById(R.id.cablerg);

        rb1 = (RadioButton) findViewById(R.id.cablemngrb1);
        rb2 = (RadioButton) findViewById(R.id.cablemngrb2);
        rb3 = (RadioButton) findViewById(R.id.cablemngrb3);
       // rb4 = (RadioButton) findViewById(R.id.cablemngrb4);
        rb5 = (RadioButton) findViewById(R.id.cablemngrb5);
        rb6 = (RadioButton) findViewById(R.id.cablemngrb6);
        rb7 = (RadioButton) findViewById(R.id.cablemngrb7);
        rb8 = (RadioButton) findViewById(R.id.cablemngrb8);

        return ;

    }
    private void addListener(Context context)
    {

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, ""+"rb1", Toast.LENGTH_SHORT).show();
                // cancel();  // 避免返回時再次出現

            }
        });   // rb1 - click event listener
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //   Toast.makeText(context, ""+"rb2", Toast.LENGTH_SHORT).show();
            }
        });   // rb2 - click event listener

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+"rb3", Toast.LENGTH_SHORT).show();
            }
        });   // rb3 - click event listener
        /*
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+"rb4", Toast.LENGTH_SHORT).show();
            }
        });   // rb4 - click event listener

         */

        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+"rb5", Toast.LENGTH_SHORT).show();
            }
        });   // rb5 - click event listener

        rb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+"rb6", Toast.LENGTH_SHORT).show();
            }
        });   // rb6 - click event listener

        rb7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+"rb7", Toast.LENGTH_SHORT).show();
            }
        });   // rb7 - click event listener

        rb8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, ""+"rb8", Toast.LENGTH_SHORT).show();
            }
        });   // rb8 - click event listener

        //  the confirmation button click event listener

        cfmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked() == true ) {
                    // find the radiobutton by returned id
                    Toast.makeText(v.getContext(), ">>>" + "纜線資料建立", Toast.LENGTH_SHORT).show();
                      // Intent intent = new Intent(v.getContext(),querycable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), CreateCableDatat.class);
                    v.getContext().startActivity(intent);

                    // cable data creation ( new cable data )
                    // CablequeryDialog cablequeryDialog = new CablequeryDialog(v.getContext());
                    // cablequeryDialog.show();  // query cable dialog

                    dismiss();   // close current dialog
                }
                else if ( rb2.isChecked() == true ) {

                    // Toast.makeText(v.getContext(), ">>>" + "纜線資料讀取", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), opmonitor.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    dismiss();   // close current dialog
                }
                else if (rb3.isChecked() == true ) {

                    // Toast.makeText(v.getContext(), ">>>" + "纜線資料查詢", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), findcable.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    dismiss();   // close current dialog
                }

                /******************** mark rb4 , because it was wrong !
                else if (rb4.isChecked() == true ) {
                    Toast.makeText(v.getContext(), ">>>" + "纜線資料修改", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), updaterecyclerlist.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    dismiss();   // close current dialog
                } */

                else if (rb5.isChecked() == true ) {

                    // Toast.makeText(v.getContext(), ">>>" + "纜線資料刪除", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), CableDeletion.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    dismiss();   // close current dialog

                }
                else if (rb6.isChecked() == true ) {

                    // Toast.makeText(v.getContext(), ">>>" + "csv檔匯入", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    Intent intent = new Intent(v.getContext(), importcsv.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    dismiss();   // close current dialog
                }
                else if (rb7.isChecked() == true ) {
                    // Toast.makeText(v.getContext(), ">>>" + "檔案下載", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    // Intent intent = new Intent(v.getContext(), dnData.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    // v.getContext().startActivity(intent);
                    // DownloadDatadialog(v);

                    WebipDialogDownLoad webipDialogDownLoad = new WebipDialogDownLoad(v.getContext());
                    webipDialogDownLoad.show();

                    WindowManager.LayoutParams layoutParams = webipDialogDownLoad.getWindow().getAttributes();
                    layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                    layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                    webipDialogDownLoad.getWindow().setAttributes(layoutParams);
                    //设置dialog进入的动画效果
                    Window window = webipDialogDownLoad.getWindow();
                    window.setWindowAnimations(R.style.AnimLeft);
                    dismiss();   // close current dialog

                }
                else if (rb8.isChecked() == true ) {

                    Toast.makeText(v.getContext(), ">>>" + "檔案上傳", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(),Erasecable.class);  // to get current dialog context and jump to another activity
                    // Intent intent = new Intent(v.getContext(), dnData.class);
                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                    // v.getContext().startActivity(intent);
                    WebipDialogUpload webipDialogUpload = new WebipDialogUpload(v.getContext());
                    webipDialogUpload.show();

                    WindowManager.LayoutParams layoutParams = webipDialogUpload.getWindow().getAttributes();
                    layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                    layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                    webipDialogUpload.getWindow().setAttributes(layoutParams);
                    //设置dialog进入的动画效果
                    Window window = webipDialogUpload.getWindow();
                    window.setWindowAnimations(R.style.AnimLeft);

                    dismiss();   // close current dialog
                }

            }
        });
        // cancel button click event listener
        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the dialog
                dismiss();
            }
        });
    }

    private void DownloadDatadialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final EditText editText = new EditText(view.getContext()); //final一個editText

        builder.setView(editText);
        builder.setTitle("請輸入欲下載纜線資料的IP位址");

        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(view.getContext(), "取消", Toast.LENGTH_SHORT).show();
                // dialogInterface.dismiss();

            }
        });

        AlertDialog dialog = builder.create(); // 創建 AlertDialog
        dialog.setCanceledOnTouchOutside(false); // 禁止點擊 AlertDialog 以外的區域取消
        dialog.setCancelable(false); // 禁止按 [手機返回鍵] 取消
        dialog.show(); // 顯示 AlertDialog

        if (true) {   // dialog animation

            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setAttributes(layoutParams);
            //设置dialog进入的动画效果
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);

        }

        //  builder.create().show();
        // the following codes redefine the positive button which

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (editText.getText().toString().compareTo("207.148.126.21") == 0) {

                    Toast.makeText(view.getContext(), editText.getText().toString() + "下載資料開始 ~~", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();   // close the dialog
                                        // DownloadProgressDialog(view); // download progressing dialog

                } else if (editText.getText().toString().compareTo("") == 0) {

                    Toast.makeText(view.getContext(), editText.getText().toString() + "IP位址不能為空,請重新輸入 !", Toast.LENGTH_SHORT).show();
                    // showEmptyIPDialog();   // show empty ip dialog
                    // dialog.dismiss();

                } else {
                    Toast.makeText(view.getContext(), "錯誤的IP位址,請重新輸入!", Toast.LENGTH_SHORT).show();
                    //   showErrorIPDialog();   // show error ip dialog
                }
                /*  此區域預設點擊按鈕後「不會」關閉 AlertDialog
                 *   除非下達 dialog.dismiss(); 的指令，AlertDialog 才會消失　*/

                // 處理 PositiveButton 要進行的動作
                // 開啟瀏覽器，把用戶導到目標網址
                // startActivity( new Intent( Intent.ACTION_VIEW , Uri.parse("http://207.148.126.21/")));
                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://medium.com/@quarter_life")));
            }
        });
    }
}

