package com.tra.loginscreen;

import static android.app.PendingIntent.getActivity;
import static com.tra.loginscreen.DataManipulation2.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import android.widget.SeekBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toolbar;

import com.tra.loginscreen.RfidtagReadingDialog.DataBackListener;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.apache.xmlbeans.impl.store.Query;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;


public class DashBoardLinearActivity extends AppCompatActivity {

    // Each card of cardview

    private CardView cardFSer0;

    private CardView cardCableMng1;
    private CardView cardWrite2;
    private CardView cardDatman3;
    private CardView cardRfid4;
    private CardView cardSetting5;
    private CardView cardWeb6;

    private CardView cardEqu7;

    DataBackListener listener ;

    // The following codes which are responsible for downloading
    // these codes were provided
    private long enqueue;
    private DownloadManager downloadManager;
    // private final static String DOWNLOAD_URL = "http://i.imgur.com/wsibrEw.gif";
    ////////////////////////////////////////////////////////////////////////////

    Dialog customDialog;

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;

    private String[] classification;
    private BottomSheetDialog bottomSheetDialog;

    private View view;
    private Button cancel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_linear);

        //TextView textView = findViewById(R.id.textView1);
        // textView.setMarqueeRepeatLimit(-1);
        // textView.setHorizontallyScrolling(true);
        // textView.setSingleLine(true);
        // textView.setSelected(true);

        final Dialog levelDialog = new Dialog(DashBoardLinearActivity.this); // power level dialog
        levelDialog.setContentView(R.layout.level_layout);
        final TextView levelTxt = (TextView) levelDialog.findViewById(R.id.level_txt);
        final SeekBar levelSeek = (SeekBar) levelDialog.findViewById(R.id.level_seek);  // here is to control power level

        levelSeek.setMax(100);  // set the max value
        levelSeek.setProgress(100);

            androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar )findViewById(R.id.mainfuntoolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            ImageView Toolbarexitimg = findViewById(R.id.imgexittoolbar);   // image button for exit main functions

            Toolbarexitimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    finish();
                }
            });

            // The following codes which are responsible for action of left side icon click
            if (false) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();   // when you click the back arrow < , current activity go back home activity
                    }
                });
            }



        levelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //change to progress
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                levelTxt.setText(Integer.toString(progress) + "%");
            }

            //methods to implement but not necessary to amend
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        Button okBtn = (Button) levelDialog.findViewById(R.id.level_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //  respond to level
                int chosenLevel = levelSeek.getProgress();
                levelDialog.dismiss();
            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/TaipeiSans.ttf");

        // TextView custom = (TextView) findViewById(R.id.textView1);
        // custom.setTypeface(font);

        // Toast.makeText(DashBoardLinearActivity.this, "登入成功 線性儀錶板!", Toast.LENGTH_SHORT).show(); //  This is a toast
        // for showing log in successfully !
        // The following codes are responsible for creating the instance of each card and its click
        // listener.


        cardFSer0 = findViewById(R.id.card0);          // search optical fiber cable with rfid tag
        cardCableMng1 = findViewById(R.id.card1);      // Load excel card
        // cardWrite2 = findViewById(R.id.card2);      // write excel card
        // cardDatman3 = findViewById(R.id.card3);     // data manipulation card
        cardRfid4 = findViewById(R.id.card4);          // RFID card
        cardSetting5 = findViewById(R.id.card5);       // Setting card
       // cardWeb6 = findViewById(R.id.card6);         // Web upload card
        cardEqu7 = findViewById(R.id.card7);           // equipments search
        classification = getResources().getStringArray(R.array.classificationsearch);


        // rg = (RadioGroup) findViewById(R.id.unit);  // radio group
        // r1= (RadioButton) findViewById(R.id.opequ);
        // r2 =(RadioButton) findViewById(R.id.transequ);
        // rg.check(R.id.opequ);
        // rg.clearCheck();

        // rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        //  @Override
        // public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        //  if (checkedId == rarray[0]) {

        //   Toast.makeText(DashBoardLinearActivity.this, "你選擇:" + r1.getText(), Toast.LENGTH_SHORT).show();
        //  Intent Searchopequintent= new Intent(DashBoardLinearActivity.this ,opequTable.class );
        //  startActivity(Searchopequintent);
        //  }
        // else if ( checkedId == rarray[1] ) {

        //   Toast.makeText(DashBoardLinearActivity.this, "你選擇:" + r2.getText(), Toast.LENGTH_SHORT).show();
        //    Intent Searchtransequintent= new Intent(DashBoardLinearActivity.this , transequTable.class );
        //    startActivity(Searchtransequintent);
        //  }
        //   }
        // });


        cardFSer0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] listItems = {"纜線查詢", "纜線刪除"};

                CableManagementDialog cableManagementDialog = new CableManagementDialog(v.getContext());
                cableManagementDialog.show();

                WindowManager.LayoutParams layoutParams = cableManagementDialog.getWindow().getAttributes();
                layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                cableManagementDialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = cableManagementDialog.getWindow();
                window.setWindowAnimations(R.style.mystyle);

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardLinearActivity.this);
                builder.setTitle("請選擇一項");
                builder.setIcon(R.drawable.rfid8) ;

                int checkedItem = 0; //this will checked the item when user open the dialog

                // AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                // View titleView = getLayoutInflater().inflate(R.layout.dialog_title, null);
                // builder.setCustomTitle(titleView);
                // key point 2

                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DashBoardLinearActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                        if ( which== -1 )
                            Toast.makeText(DashBoardLinearActivity.this, "未選擇" , Toast.LENGTH_SHORT).show();
                        else if (which == 0 ) {
                            Toast.makeText(DashBoardLinearActivity.this, "第1項", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DashBoardLinearActivity.this, transmissionunit.class);
                            startActivity(intent);
                            dialog.cancel();  // 避免返回時再次出現
                        }
                        else if (which == 1) {
                            Toast.makeText(DashBoardLinearActivity.this, "第2項", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DashBoardLinearActivity.this, opmonitor.class);
                            startActivity(intent);
                            dialog.cancel();  // 避免返回時再次出現
                        }

                    }
                });

                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
              */
              /*
                LayoutInflater inflater = (LayoutInflater) getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.form_elements,
                        null, false);
                // You have to list down your form elements

                final RadioGroup genderRadioGroup = (RadioGroup) formElementsView
                        .findViewById(R.id.genderRadioGroup);
                // View titleView = getLayoutInflater().inflate(R.layout.dialog_title, null);
                // View titleView = getLayoutInflater().inflate(R.layout.dialog_title, null);

                 */
                /*
                AlertDialog builder =
                        new AlertDialog.Builder(v.getContext()).setView(formElementsView)
                                .setTitle("請選擇")
                                // .setCustomTitle(formElementsView)
                                .setIcon(R.drawable.rfid)
                                .setPositiveButton("確定",
                                        new DialogInterface.OnClickListener() {
                                            @TargetApi(11)
                                            public void onClick(DialogInterface dialog, int id) {
                                                int selectedId = genderRadioGroup
                                                        .getCheckedRadioButtonId();

                                                // find the radiobutton by returned id
                                                RadioButton selectedRadioButton = (RadioButton) formElementsView
                                                        .findViewById(selectedId);

                                                Toast.makeText(DashBoardLinearActivity.this, "" + "selected id :" + Integer.toString(selectedId), Toast.LENGTH_SHORT).show();
                                                if (selectedRadioButton.getText().toString().compareTo(listItems[0]) == 0) {  // 纜線查詢
                                                    // Intent intent = new Intent(DashBoardLinearActivity.this, transmissionunit.class);
                                                    //  startActivity(intent);
                                                    Intent intent1 = new Intent(DashBoardLinearActivity.this, querycable.class);
                                                    startActivity(intent1);


                                                    dialog.cancel();  // 避免返回時再次出現
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems[1]) == 0) {
                                                    // 纜線刪除 (基本上, 這個是RFID的光纜刪除)
                                                    Bundle bundle = new Bundle(); // using a bundle to put some data which could be refered in next activity
                                                    bundle.putString("content", "EraseCable");
                                                    Intent intent = new Intent();
                                                    intent.putExtras(bundle);   // binding the bundle with intent
                                                    intent.setClass(DashBoardLinearActivity.this, Erasecable.class);

                                                    startActivity(intent);
                                                    dialog.cancel();  // 避免返回時再次出現
                                                } else
                                                    Log.e(TAG, "Error ! ");

                                                // dialog.cancel();
                                            }
                                        })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(DashBoardLinearActivity.this, "" + "hello", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }


                                }).show();

                  */
                /*
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);

                try {

                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(builder);

                    //获取mTitleView并设置大小颜色
                    Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                    mTitle.setAccessible(true);
                    TextView mTitleView = (TextView) mTitle.get(mAlertController);
                    mTitleView.setTextSize(40);
                    mTitleView.setTextColor(Color.YELLOW);

                    Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                    mMessage.setAccessible(true);
                    TextView mMessageView = (TextView) mMessage.get(mAlertController);
                    mMessageView.setTextColor(Color.RED);
                    mMessageView.setTextSize(30);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                Window window = builder.getWindow();
                WindowManager m = getWindowManager(); // it's not a  fragment , so don't use getActivity()

                Display d = m.getDefaultDisplay();                      // 获取屏幕宽、高用
                WindowManager.LayoutParams p = window.getAttributes();  // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.28);              // 改变的是dialog框在屏幕中的位置而不是大小
                p.width = (int) (d.getWidth() * 0.85);                  // 宽度设置为屏幕的0.65
                window.setAttributes(p);
                 */
                /*
                Intent intentDataImport = new Intent(DashBoardLinearActivity.this , DataImport.class) ;
                startActivity(intentDataImport);
                */
                //  Toast.makeText(DashBoardLinearActivity.this, "" + "kldslldhkld", Toast.LENGTH_SHORT).show();
                //  final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardLinearActivity.this,R.style.CustomAlertDialog);
                //  Dialog d = builder.create();
                //  d.show();
                // showRadioDialog();
            }

        });

       /*
                    builder.setSingleChoiceItems(listItems, checkedItem1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DashBoardLinearActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();

                            if( which == 0 ) {
                                Intent intent = new Intent(DashBoardLinearActivity.this, transmissionunit.class);
                                startActivity(intent) ;

                            }
                            else if ( which == 1 ) {

                                Intent intent= new Intent(DashBoardLinearActivity.this , opmonitor.class);
                                // Toast.makeText(DashBoardLinearActivity.this, "", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }
                        }
                    });

                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();  // close the dialog
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Window window = dialog.getWindow();//对话框窗口
                    window.setGravity(Gravity.CENTER);//设置对话框显示在屏幕中间
                    window.setWindowAnimations(R.style.dialog_style);//添加动画
            }
        });

        */

        cardCableMng1.setOnClickListener(new View.OnClickListener() {

            String[] listItems1 =
                    {
                    "纜線資料建立",
                    "纜線資料讀取",
                    "纜線資料查詢",
                    "纜線資料修改",
                    "纜線資料刪除",
                    "纜線資料匯入",
                    "纜線資料下載",
                    "纜線資料上傳" } ;

            @Override
            public void onClick(View v) {

                TextView msg = new TextView(DashBoardLinearActivity.this);
                msg.setTextSize(35);
                msg.setText("纜線管理");
                msg.setGravity(Gravity.CENTER);
                msg.setTextSize(20);

                CableDataManagement cableDataManagement = new CableDataManagement(v.getContext());
                cableDataManagement.show();

                WindowManager.LayoutParams layoutParams = cableDataManagement.getWindow().getAttributes();
                layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                cableDataManagement.getWindow().setAttributes(layoutParams) ;

                //设置dialog进入的动画效果
                Window window = cableDataManagement.getWindow();
                window.setWindowAnimations(R.style.mystyle);

                Toast.makeText(DashBoardLinearActivity.this, "纜線資料操作", Toast.LENGTH_SHORT).show();

                // Intent intentDataImport = new Intent(DashBoardLinearActivity.this, DataImport.class);
                // startActivity(intentDataImport);
                /*
                LayoutInflater inflater = (LayoutInflater) getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.form_elements1,
                        null, false);
                // You have to list down your form elements
                final RadioGroup cableMngRadioGroup = (RadioGroup) formElementsView
                        .findViewById(R.id.cableMngRadioGroup);
                // View titleView = getLayoutInflater().inflate(R.layout.dialog_title, null);
                 */
                /*
                AlertDialog builder =
                        new AlertDialog.Builder(v.getContext()).setView(formElementsView)
                                .setCustomTitle(msg)
                                // .setCustomTitle(formElementsView)
                                // .setIcon(R.drawable.rfid8)
                                .setPositiveButton("確定",
                                        new DialogInterface.OnClickListener() {
                                            @TargetApi(11)
                                            // this is a API level filter , the following codes will be executed normally over API 11

                 */
                /*
                                            public void onClick(DialogInterface dialog, int id) {

                                                int selectedId = cableMngRadioGroup
                                                        .getCheckedRadioButtonId();
                                                // find the radiobutton by returned id

                                                RadioButton selectedRadioButton = (RadioButton) formElementsView
                                                        .findViewById(selectedId);

                                                // Toast.makeText(DashBoardLinearActivity.this, "" + "selected id :"+Integer.toString(selectedId), Toast.LENGTH_SHORT).show();
                                                if (selectedRadioButton.getText().toString().compareTo(listItems1[0]) == 0) {   // 纜線資料建立
                                                    Toast.makeText(DashBoardLinearActivity.this, "纜線資料建立", Toast.LENGTH_SHORT).show();
                                                    // Intent intent = new Intent(DashBoardLinearActivity.this, transmissionunit.class);
                                                    //  startActivity(intent);
                                                    Intent intent = new Intent(DashBoardLinearActivity.this, CreateCableDatat.class);
                                                    // cable data creation ( new cable data )

                                                    startActivity(intent);
                                                    dialog.cancel();  // 避免返回時再次出現
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[1]) == 0) {
                                                    // 纜線刪除
                                                    // Intent intent = new Intent(DashBoardLinearActivity.this, opmonitor.class);
                                                    //  startActivity(intent);
                                                    //  startActivity(intent);
                                                    // Intent  intent1 = new Intent(DashBoardLinearActivity.this , querycable.class) ;
                                                    //  startActivity(intent1);

                                                    Intent intent = new Intent(DashBoardLinearActivity.this, opmonitor.class);
                                                    // Toast.makeText(DashBoardLinearActivity.this, ""+"try this", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);

                                                    dialog.cancel();  // 避免返回時再次出現
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[2]) == 0) {   // This is cable search page

                                                    Intent intent = new Intent(DashBoardLinearActivity.this, findcable.class);
                                                    Toast.makeText(DashBoardLinearActivity.this, "", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);

                                                    dialog.cancel();
                                                    Log.e(TAG, "Error ! ");
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[3]) == 0) {   // This is modification dialog that is in charge of edit the contents of items in list view

                                                    Toast.makeText(DashBoardLinearActivity.this, "" + "修改光纖資料", Toast.LENGTH_SHORT).show();
                                                    // Intent intent = new Intent(DashBoardLinearActivity.this,UpdatecableItem.class );
                                                    // startActivity(intent);
                                                    Intent intent = new Intent(DashBoardLinearActivity.this, updaterecyclerlist.class);
                                                    // Intent intent = new Intent (DashBoardLinearActivity.this , CRUDrecyclerview.class);

                                                    startActivity(intent);

                                                    // Intent intent = new Intent(DashBoardLinearActivity.this , importcsv.class);
                                                    // startActivity(intent);
                                                    // this is modification of items in listview

                                                    dialog.cancel();
                                                    Log.e(TAG, "Error ! ");
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[4]) == 0) {

                                                    Toast.makeText(DashBoardLinearActivity.this, "進入刪除頁面", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(DashBoardLinearActivity.this, CableDeletion.class);
                                                    startActivity(intent);

                                                    dialog.cancel();
                                                    Log.e(TAG, "Error ! ");
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[5]) == 0) {
                                                    // this is responsible for importing csv file into rfid reader
                                                    Toast.makeText(DashBoardLinearActivity.this, "" + "csv檔載入...", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(DashBoardLinearActivity.this, importcsv.class);
                                                    startActivity(intent);
                                                    dialog.cancel();
                                                    Log.e(TAG, "Error ! ");
                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[6]) == 0) {
                                                    Toast.makeText(DashBoardLinearActivity.this, "" + "檔案下載...", Toast.LENGTH_SHORT).show();

                                                    DownloadDatadialog();
                                                    // Intent intent = new Intent(DashBoardLinearActivity.this , dnData.class);
                                                    //  startActivity(intent);
                                                    dialog.cancel();

                                                } else if (selectedRadioButton.getText().toString().compareTo(listItems1[7]) == 0) {
                                                    Toast.makeText(DashBoardLinearActivity.this, "" + "檔案上傳...", Toast.LENGTH_SHORT).show();
                                                    UploadDatadialog();
                                                    dialog.cancel();

                                                }

                                                // dialog.cancel();
                                            }
                                        })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Toast.makeText(DashBoardLinearActivity.this, ""+"hello", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }

                                }).show();

                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);

 */
 /*
                try {

                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(builder);

                    //获取mTitleView并设置大小颜色
                    Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                    mTitle.setAccessible(true);
                    TextView mTitleView = (TextView) mTitle.get(mAlertController);
                    mTitleView.setTextSize(40);
                    mTitleView.setTextColor(Color.YELLOW);

                    Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                    mMessage.setAccessible(true);
                    TextView mMessageView = (TextView) mMessage.get(mAlertController);
                    mMessageView.setTextColor(Color.RED);
                    mMessageView.setTextSize(30);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                Window window = builder.getWindow();
                WindowManager m = getWindowManager(); // it's not a  fragment , so don't use getActivity()

                Display d = m.getDefaultDisplay();                      // 获取屏幕宽、高用
                WindowManager.LayoutParams p = window.getAttributes();  // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.7);              // 改变的是dialog框在屏幕中的位置而不是大小
                p.width = (int) (d.getWidth() * 0.85);                  // 宽度设置为屏幕的0.65
                window.setAttributes(p);

              */

            }

        });


        /*
        cardWrite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardLinearActivity.this, "資料匯出", Toast.LENGTH_SHORT).show();
                // translate current page to data export page
                Intent intentDataExport = new Intent(DashBoardLinearActivity.this, DataExport.class);
                startActivity(intentDataExport);
            }
        });

         */
         /*
        cardDatman3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DashBoardLinearActivity.this, "纜線標籤資料操作", Toast.LENGTH_SHORT).show();
                // Intent intentforGridviewDash = new Intent(DashBoardLinearActivity.this, DashBoardActivity.class) ;
                // startActivity(intentforGridviewDash); // jump to data manipulation page
                Intent intentDataNavig = new Intent(DashBoardLinearActivity.this, DataManipulation2.class);
                startActivity(intentDataNavig);

            }
        });  */


        cardSetting5.setOnClickListener(new View.OnClickListener() {
            @Override

            // RFID　功率調整　

            public void onClick(View v) {
                Toast.makeText(DashBoardLinearActivity.this, "RFID輸出功率設定", Toast.LENGTH_SHORT).show();

                // int chosenLevel = levelSeek.getProgress();
                // levelDialog.dismiss();
                // levelDialog.show();

                SettingThresholdDialog dialog = new SettingThresholdDialog(v.getContext());
                dialog.show();

                if (true) {

                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                    layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setAttributes(layoutParams);
                    //设置dialog进入的动画效果
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);
                }

                // Intent intent = new Intent(DashBoardLinearActivity.this, RFIDPWAdjust.class);
                // startActivity(intent);
            }
        });

        /*
        cardWeb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardLinearActivity.this, "資料同步", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashBoardLinearActivity.this, UploadActivity.class);
                startActivity(intent);

            }
        });

         */


        cardRfid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DashBoardLinearActivity.this, "RFID標籤建立", Toast.LENGTH_SHORT).show();

                String[] listItems = { "RFID標籤寫入" ,
                                       "RFID標籤讀取" ,
                                       "RFID標籤修改"  } ;

                RfidtagManagementDialog rfidtagManagementDialog = new RfidtagManagementDialog(DashBoardLinearActivity.this);
                rfidtagManagementDialog.show();

                WindowManager.LayoutParams layoutParams = rfidtagManagementDialog.getWindow().getAttributes();
                layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                rfidtagManagementDialog.getWindow().setAttributes(layoutParams);

                //设置dialog进入的动画效果
                Window window = rfidtagManagementDialog.getWindow();
                window.setWindowAnimations(R.style.mystyle);

                Button confirmbtn , cancelbtn ;

                RadioGroup rfidmngrg;
                RadioButton rb1,rb2,rb3 ;

                rfidmngrg = rfidtagManagementDialog.findViewById(R.id.rfidtagmngrg);

                rb1 = rfidtagManagementDialog.findViewById(R.id.rfidmngrb1);
                rb2 = rfidtagManagementDialog.findViewById(R.id.rfidmngrb2);
                rb3 = rfidtagManagementDialog.findViewById(R.id.rfidmngrb3);

                confirmbtn = rfidtagManagementDialog.findViewById(R.id.confirm) ;
                cancelbtn  = rfidtagManagementDialog.findViewById(R.id.cancel)  ;

                confirmbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(DashBoardLinearActivity.this, "confirmation ! ", Toast.LENGTH_SHORT).show();

                        if (rb1.isChecked() == true ) {
                            WriteRFIDdialog(v);  // write RFID
                        }
                        if (rb2.isChecked() == true ) {
                            ReadRFIDdialog(v);   // read RFID
                        }
                        if (rb3.isChecked() == true ) {
                            UpdateRFIDdialog(v);  // update RFID
                        }
                        // rfidtagManagementDialog.cancel();  // close the dialog
                    }
                });

                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rfidtagManagementDialog.cancel();  // close the dialog
                        Toast.makeText(DashBoardLinearActivity.this, "cancellation ! ", Toast.LENGTH_SHORT).show();

                    }
                });


                if (false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardLinearActivity.this);
                    builder.setTitle("RFID標籤管理");

                    int checkedItem = 0; //this will checked the item when user open the dialog
                    builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            //  Toast.makeText(DashBoardLinearActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                            if (which == 0) {
                                WriteRFIDdialog(v);  // write RFID
                            } else if (which == 1) {
                                ReadRFIDdialog(v);   // read RFID
                            } else if (which == 2) {
                                UpdateRFIDdialog(v);  // update RFID
                            }
                        }
                    });

                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(DashBoardLinearActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(DashBoardLinearActivity.this, RFIDTagActivity.class);
                            // startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                //Intent intent = new Intent(DashBoardLinearActivity.this, RFIDTagActivity.class);
                // startActivity(intent);
            }
        });


        cardEqu7.setOnClickListener(new View.OnClickListener() {

            final int[] checkedItem = {-1};

            @Override
            public void onClick(View v) {

                String[] listItems = {"傳輸單位", "光纖監測"};

                if (false) {  // those codes are responsible for a  dialog which display some information

                    customDialog = new Dialog(DashBoardLinearActivity.this);
                    customDialog.setContentView(R.layout.custom_dialog_equ);
                    customDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    WindowManager m = getWindowManager();
                    Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                    WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
                    p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.5
                    p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
                    customDialog.show();

                    Button btnSubmit = customDialog.findViewById(R.id.btnSubmit);
                    LayoutInflater inflater = getLayoutInflater();                             //先获取当前布局的填充器
                    View whichYouWantToUse_findViewById = inflater.inflate(R.layout.custom_dialog_equ, null);   //通过填充器获取另外一个布局的对象
                    radioGroup = (RadioGroup) whichYouWantToUse_findViewById.findViewById(R.id.radiogp);             //通过另外一个布局对象的findViewById获取其中的控件
                    radioButton1 = (RadioButton) whichYouWantToUse_findViewById.findViewById(R.id.radioButton1);     //通过另外一个布局对象的findViewById获取其中的控件
                    radioButton2 = (RadioButton) whichYouWantToUse_findViewById.findViewById(R.id.radioButton2);     //通过另外一个布局对象的findViewById获取其中的控件
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                            if (checkedId == R.id.radioButton1)
                                Toast.makeText(DashBoardLinearActivity.this, "你選擇:" + radioButton1.getText(), Toast.LENGTH_SHORT).show();
                            else if (checkedId == R.id.radioButton2)
                                Toast.makeText(DashBoardLinearActivity.this, "你選擇:" + radioButton2.getText(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    btnSubmit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Toast.makeText(DashBoardLinearActivity.this, "關閉對話框", Toast.LENGTH_SHORT).show();
                            customDialog.dismiss(); // close dialog

                        }
                    });
                }  // do nothing !

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardLinearActivity.this);
                builder.setTitle("請選擇一項");
                // radioGroup.clearCheck();

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DashBoardLinearActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();

                        if (which == 0) {
                            Toast.makeText(DashBoardLinearActivity.this, "開啟傳輸單位", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DashBoardLinearActivity.this, transmissionunit.class);
                            startActivity(intent);
                        } else if (which == 1) {
                            Toast.makeText(DashBoardLinearActivity.this, "開啟光纖監測", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DashBoardLinearActivity.this, opmonitor.class);
                            // Toast.makeText(DashBoardLinearActivity.this, "", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                });

                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();  // close the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Window window = dialog.getWindow();//对话框窗口
                window.setGravity(Gravity.CENTER);//设置对话框显示在屏幕中间
                window.setWindowAnimations(R.style.dialog_style);//添加动画

                 */
                ClassificationDialog dialog = new ClassificationDialog(v.getContext());

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                 if (true) {
                     WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                     layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                     layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                     dialog.getWindow().setAttributes(layoutParams);
                     //设置dialog进入的动画效果
                     Window window = dialog.getWindow();
                     window.setWindowAnimations(R.style.AnimCenter);
                 }
                //获取对话框确定和取消按钮
                // initEvent();//初始化事件
                /*
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashBoardLinearActivity.this);
                alertDialog.setIcon(R.drawable.rfid3);
                alertDialog.setTitle("篩選清單");
                final String[] listItems = new String[]{"傳輸設備", "光纖監測"};
                alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                    checkedItem[0] = which;
                    Intent intent = new Intent(DashBoardLinearActivity.this , opequTable.class);
                    startActivity(intent);
                    Toast.makeText(DashBoardLinearActivity.this, ""+Integer.toString(which), Toast.LENGTH_SHORT).show();
                });
                alertDialog.setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();  // close the dialog
                });
                AlertDialog customAlertDialog = alertDialog.create();
                customAlertDialog.show();

                */
            }

        });

    }

        private void DownloadDatadialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText editText = new EditText(DashBoardLinearActivity.this); //final一個editText

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
                    Toast.makeText(DashBoardLinearActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    // dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();   // 創建 AlertDialog
            dialog.setCanceledOnTouchOutside(false); // 禁止點擊 AlertDialog 以外的區域取消
            dialog.setCancelable(false);             // 禁止按 [手機返回鍵] 取消
            dialog.show();                           // 顯示 AlertDialog

            if (true) {
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = dialog.getWindow();
                window.setWindowAnimations(R.style.AnimCenter);
            }

            //  builder.create().show();
            // the following codes redefine the positive button which

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (editText.getText().toString().compareTo("207.148.126.21") == 0) {
                        Toast.makeText(DashBoardLinearActivity.this, editText.getText().toString() + "下載資料開始 ~~", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();   // close the dialog
                        DownloadProgressDialog(view); // download progressing dialog


                    } else if (editText.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DashBoardLinearActivity.this, editText.getText().toString() + "IP位址不能為空,請重新輸入 !", Toast.LENGTH_SHORT).show();
                        showEmptyIPDialog();   // show empty ip dialog
                        // dialog.dismiss();
                    } else {
                        Toast.makeText(DashBoardLinearActivity.this, "錯誤的IP位址,請重新輸入!", Toast.LENGTH_SHORT).show();
                        showErrorIPDialog();   // show error ip dialog
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

        private void UploadDatadialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText editText = new EditText(DashBoardLinearActivity.this); //final一個editText

            builder.setView(editText);
            builder.setTitle("請輸入Web Server的位址");

            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(DashBoardLinearActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    // dialogInterface.dismiss();

                }
            });

            AlertDialog dialog = builder.create(); // 創建 AlertDialog
            dialog.setCanceledOnTouchOutside(false); // 禁止點擊 AlertDialog 以外的區域取消
            dialog.setCancelable(false); // 禁止按 [手機返回鍵] 取消
            dialog.show();


            if (true) {   // this is  animation for dialog entery

                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = dialog.getWindow();
                window.setWindowAnimations(R.style.AnimCenter);
            }
            //  顯示 AlertDialog
            //  builder.create().show();
            //  the following codes redefine the positive button which
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (editText.getText().toString().compareTo("207.148.126.21") == 0) {
                        Toast.makeText(DashBoardLinearActivity.this, editText.getText().toString() + "下載資料開始 ~~", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();   // close the dialog
                        UploadProgressDialog(view); // download progressing dialog


                    } else if (editText.getText().toString().compareTo("") == 0) {
                        // Toast.makeText(DashBoardLinearActivity.this, editText.getText().toString() + "IP位址不能為空,請重新輸入 !", Toast.LENGTH_SHORT).show();

                        showEmptyIPDialog(); // show empty ip address

                        // dialog.dismiss();
                    } else {
                        // Toast.makeText(DashBoardLinearActivity.this, "錯誤的IP位址,請重新輸入!", Toast.LENGTH_SHORT).show();
                        showErrorIPDialog();  // show error ip address
                    }

                    // 此區域預設點擊按鈕後「不會」關閉 AlertDialog
                    //  除非下達 dialog.dismiss(); 的指令，AlertDialog 才會消失　
                    // 處理 PositiveButton 要進行的動作
                    // 開啟瀏覽器，把用戶導到目標網址
                    // startActivity( new Intent( Intent.ACTION_VIEW , Uri.parse("http://207.148.126.21/")));
                    // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://medium.com/@quarter_life")));
                }
            });

        }
        public void DownloadProgressDialog(View view) {
             Handler handle = null; 

            progressDialog = new ProgressDialog(DashBoardLinearActivity.this);
            progressDialog.setMax(100); // Progress Dialog Max Value
            progressDialog.setMessage("          " +
                    "纜線資料下載中...."); // Setting Message
            progressDialog.setCanceledOnTouchOutside(true);  // you could touch outside of dialog to cancel this task
            progressDialog.setTitle("纜線資料下載"); // Setting Title
            progressDialog.setIcon(R.drawable.rfid4);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        try {

                            while (progressDialog.getProgress() <= progressDialog.getMax()) {
                                Thread.sleep(100);
                                handle.sendMessage(handle.obtainMessage());

                                if (progressDialog.getProgress() == progressDialog.getMax()) {
                                    progressDialog.dismiss(); // download data successfully !
                                }

                            } // end of while loop

                            //  Toast.makeText(DashBoardLinearActivity.this, "progress is over ! ", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //  this.notify();
                }
            }).start();

            // showRFIDReadDialog();
            // showRFIDUpdateDialog();   // Show the dialog that download successfully




            //Get download file name
            // String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(DOWNLOAD_URL);
            // String name = URLUtil.guessFileName(DOWNLOAD_URL, null, fileExtenstion);

            //Save file to destination folder
         
        }

        public void UploadProgressDialog(View view) {

            Handler handle = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.incrementProgressBy(1); // Incremented By Value 2
                }
            };

            progressDialog = new ProgressDialog(DashBoardLinearActivity.this);
            progressDialog.setMax(100); // Progress Dialog Max Value
            progressDialog.setMessage("          " +
                    "纜線資料上傳中...."); // Setting Message
            progressDialog.setCanceledOnTouchOutside(true);  // you could touch outside of dialog to cancel this task
            progressDialog.setTitle("纜線資料上傳"); // Setting Title
            progressDialog.setIcon(R.drawable.rfid4);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (progressDialog.getProgress() <= progressDialog.getMax()) {
                            Thread.sleep(100);
                            handle.sendMessage(handle.obtainMessage());
                            if (progressDialog.getProgress() == progressDialog.getMax()) {

                                progressDialog.dismiss(); // download data successfully !
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        private void showEmptyIPDialog() {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            int icon_error = R.drawable.errorupload;   // this is error indicator of uploading

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.iperrordialog, viewGroup, false);
            TextView Errortxt;
            TextView title;
            ImageView ImageOfdialog;

            ImageOfdialog = dialogView.findViewById(R.id.imagedetials);  // focus on the icon

            ImageOfdialog.setImageResource(icon_error);  // change image

            Errortxt = dialogView.findViewById(R.id.cabletxt);
            title = dialogView.findViewById(R.id.titledetials);  //  this is title of dialog

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            title.setVisibility(View.GONE);
            title.setGravity(Gravity.CENTER);  // center of gravity

            Errortxt.setText("IP 位址不能為空! ");
            Errortxt.setTextColor(Color.RED);
            Errortxt.setTextSize(25);
            Errortxt.setTypeface(Typeface.DEFAULT_BOLD);  // Bold text
            Errortxt.setGravity(Gravity.CENTER);   // text gravity center


            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
      /*
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("纜線詳細資料");
        progress.setMessage("標籤序號: " +dat.Getf1() + "\n" +
                            "路由編號: " +dat.Getf2() + "\n" +
                            "機房名稱(From): " + dat.Getf3() + "\n" +
                            "機房樓層(From): " + dat.Getf4() + "\n" +
                            "機櫃編號(From): " + dat.Getf5() + "\n" +
                            "接續盒編號(From): " + dat.Getf6() + "\n" +
                            "連接埠編號(From): " + dat.Getf7() + "\n" +
                            "纜線名稱(From): " + dat.Getf8() + "\n" );
        progress.show();


        // The following codes show the action which inform users writing progress is going to progress
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();  //
            }
        };
         */
            // Handler pdCanceller = new Handler();
            // pdCanceller.postDelayed(progressRunnable, 2000);

            Button btn = dialogView.findViewById(R.id.buttondetials);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        private void showErrorIPDialog() {

            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            int icon_error = R.drawable.iconerror;   // this is error indicator of uploading

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.iperrordialog_1, viewGroup, false);
            TextView Errortxt;
            TextView title;
            ImageView ImageOfdialog;

            ImageOfdialog = dialogView.findViewById(R.id.imagedetials);  // focus on the icon

            ImageOfdialog.setImageResource(icon_error);  // change image

            Errortxt = dialogView.findViewById(R.id.cabletxt);
            title = dialogView.findViewById(R.id.titledetials);  //  this is title of dialog

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            title.setVisibility(View.GONE);
            title.setGravity(Gravity.CENTER);  // center of gravity

            Errortxt.setText("IP位址錯誤 !");
            Errortxt.setTextColor(Color.RED);
            Errortxt.setTextSize(25);
            Errortxt.setTypeface(Typeface.DEFAULT_BOLD);  // Bold text
            Errortxt.setGravity(Gravity.CENTER);   // text gravity center

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
      /*
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("纜線詳細資料");
        progress.setMessage("標籤序號: " +dat.Getf1() + "\n" +
                            "路由編號: " +dat.Getf2() + "\n" +
                            "機房名稱(From): " + dat.Getf3() + "\n" +
                            "機房樓層(From): " + dat.Getf4() + "\n" +
                            "機櫃編號(From): " + dat.Getf5() + "\n" +
                            "接續盒編號(From): " + dat.Getf6() + "\n" +
                            "連接埠編號(From): " + dat.Getf7() + "\n" +
                            "纜線名稱(From): " + dat.Getf8() + "\n" );
        progress.show();


        // The following codes show the action which inform users writing progress is going to progress
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();  //
            }
        };
         */
            // Handler pdCanceller = new Handler();
            // pdCanceller.postDelayed(progressRunnable, 2000);

            Button btn = dialogView.findViewById(R.id.buttondetials);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        private void showWebIPDialog() {

            // before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            int icon_error = R.drawable.errorupload;   // this is error indicator of uploading

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.webipdialog, viewGroup, false);
            TextView Errortxt;
            TextView title;
            ImageView ImageOfdialog;

            ImageOfdialog = dialogView.findViewById(R.id.imagedetials);  // focus on the icon
            ImageOfdialog.setImageResource(icon_error);  // change image

            // Errortxt = dialogView.findViewById(R.id.cabletxt);
            title = dialogView.findViewById(R.id.titledetials);  //  this is title of dialog

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            title.setVisibility(View.GONE);
            title.setGravity(Gravity.CENTER);  // center of gravity

         /*
        Errortxt.setText("IP 位址不能為空! " ) ;
        Errortxt.setTextColor(Color.RED);
        Errortxt.setTextSize(25);
        Errortxt.setTypeface(Typeface.DEFAULT_BOLD);  // Bold text
        Errortxt.setGravity(Gravity.CENTER);   // text gravity center
         */

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
      /*
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("纜線詳細資料");
        progress.setMessage("標籤序號: " +dat.Getf1() + "\n" +
                            "路由編號: " +dat.Getf2() + "\n" +
                            "機房名稱(From): " + dat.Getf3() + "\n" +
                            "機房樓層(From): " + dat.Getf4() + "\n" +
                            "機櫃編號(From): " + dat.Getf5() + "\n" +
                            "接續盒編號(From): " + dat.Getf6() + "\n" +
                            "連接埠編號(From): " + dat.Getf7() + "\n" +
                            "纜線名稱(From): " + dat.Getf8() + "\n" );
        progress.show();


        // The following codes show the action which inform users writing progress is going to progress
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();  //
            }
        };
         */
            // Handler pdCanceller = new Handler();
            // pdCanceller.postDelayed(progressRunnable, 2000);

            Button btn = dialogView.findViewById(R.id.btnconfirm);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        private void WriteRFIDdialog(View view) {

            RfidtagWritingDialog rfidtagWritingDialog = new RfidtagWritingDialog(view.getContext());
            rfidtagWritingDialog.show();

            WindowManager.LayoutParams layoutParams = rfidtagWritingDialog.getWindow().getAttributes();
            layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

            rfidtagWritingDialog.getWindow().setAttributes(layoutParams);

            //设置dialog进入的动画效果
            Window window = rfidtagWritingDialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);

           /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText editText = new EditText(view.getContext()); //final一個editText
            builder.setView(editText);
            builder.setTitle("請輸入RFID編碼");
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), editText.getText().toString() + "寫入RFID編碼", Toast.LENGTH_SHORT).show();
                /*
                final ProgressDialog dialog = ProgressDialog.show(view.getContext(), "",
                        "寫入RFID編碼，請稍待...", true, true);  */
                /*
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                t.start();

                    showRFIDWriteDialog();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), "取消", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
            */
        }


        private void UpdateRFIDdialog(View view) {

            RfidtagUpdateDialog rfidtagUpdateDialog = new RfidtagUpdateDialog(view.getContext());
            rfidtagUpdateDialog.show();

            if (true) {
                WindowManager.LayoutParams layoutParams = rfidtagUpdateDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                rfidtagUpdateDialog.getWindow().setAttributes(layoutParams);

                //设置dialog进入的动画效果
                Window window = rfidtagUpdateDialog.getWindow();
                window.setWindowAnimations(R.style.mystyle);
            }

            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText editText = new EditText(view.getContext()); //final一個editText
            builder.setView(editText);
            builder.setTitle("請輸入RFID編碼");
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), editText.getText().toString() + "寫入RFID編碼", Toast.LENGTH_SHORT).show();

                final ProgressDialog dialog = ProgressDialog.show(view.getContext(), "",
                        "寫入RFID編碼，請稍待...", true, true);  */
                /*
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                t.start();


                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), "取消", Toast.LENGTH_SHORT).show();
                }
            });


            // builder.create().show();

            AlertDialog dialog;

            dialog = builder.create();
            dialog.show();
            // Given a timer to close the dialog
          */
            // showRFIDUpdateDialog();

        }

        private void ReadRFIDdialog(View view) {

          /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // final EditText editText = new EditText(view.getContext()); //final一個editText
            // builder.setView(editText);
            builder.setTitle("讀取RFID編碼");
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), "讀取RFID編碼", Toast.LENGTH_SHORT).show();
                /*
                final ProgressDialog dialog = ProgressDialog.show(view.getContext(), "",
                        "寫入RFID編碼，請稍待...", true, true);  */
                /*
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                t.start();

                    showRFIDReadDialog();

                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), "取消", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();

                 */
            RfidtagReadingDialog rfidtagReadingDialog = new RfidtagReadingDialog(view.getContext(),listener );
            rfidtagReadingDialog.show();

            if  (true) {
                WindowManager.LayoutParams layoutParams = rfidtagReadingDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);


                rfidtagReadingDialog.getWindow().setAttributes(layoutParams);

                //设置dialog进入的动画效果
                Window window = rfidtagReadingDialog.getWindow();
                window.setWindowAnimations(R.style.AnimTop);
            }

        }

        private void showRFIDReadDialog() {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.rfidread_dialog, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if(true) {
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                alertDialog.getWindow().setAttributes(layoutParams);

                //设置dialog进入的动画效果
                Window window = alertDialog.getWindow();
                window.setWindowAnimations(R.style.AnimTop);
            }

            Button btn = dialogView.findViewById(R.id.buttonrfidread);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        private void showRFIDWriteDialog() {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.rfidwrite_dialog, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("RFID編碼寫入");
            progress.setMessage("編碼寫入中....,請稍待 !");
            progress.show();

            // The following codes show the action which inform users writing progress is going to progress
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progress.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);


            Button btn = dialogView.findViewById(R.id.buttonrfidwrite);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        private void showRFIDUpdateDialog() {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.rfidupdate_dialog, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if(true) {

                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

                alertDialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = alertDialog.getWindow();
                window.setWindowAnimations(R.style.AnimTop);

            }

            if  (false) {
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alertDialog.dismiss();
                        //结束本界面并跳转到收派员列表的界面
                        // finish();
                        t.cancel();
                    }
                }, 5000);   // close this dialog after 5 secs or you may click the confirmation button
            }

            Button btn = dialogView.findViewById(R.id.buttonrfidupdate);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

    }
