package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CableDetailDatatagwriting extends AppCompatActivity {

    String f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16 ;

    private TextView txtdata;

    private TextView txtrfidwriting ;

    // private ImageView MainMenuHome;

    private ImageView BackPreviousPage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_detail_datatagwriting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏


       //  MainMenuHome = (ImageView)findViewById(R.id.imghome);
        /*
        MainMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // home icon was clicked !
                finish();  // Notice ! it must be executed
                Intent intent = new Intent(v.getContext(), MainSqaurefun.class);
                startActivity(intent);
            }
        });

         */

        BackPreviousPage = (ImageView)findViewById(R.id.imgebackarrow) ;
        BackPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtrfidwriting = (TextView)findViewById(R.id.txtrfidtagwr) ;
        /*
        txtrfidwriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();   // bundle for data
                Toast.makeText(CableDetailDatatagwriting.this, "rfid writing ! ", Toast.LENGTH_SHORT).show();
                // the following cdoes is responsible for getting rfid tag's epc and passing it to next activity
                //
                //
                //

                bundle.putString("EPC","A1B20000000000000000000");
                finish();

                Intent intentforRfidtagCabledetails = new Intent() ;
                intentforRfidtagCabledetails.setClass(v.getContext() , rfidtagwritingcontent.class) ;
                intentforRfidtagCabledetails.putExtras(bundle);  // put those data and send it all out
                startActivity(intentforRfidtagCabledetails);     // put those data to next activity


            }
        });

        */

        txtdata = (TextView) findViewById(R.id.txtcabledetails) ;
        txtdata.setTextColor(Color.BLACK);
        txtdata.setTextSize(18);

        // Getting the bundle from previous activity

        f1 = (String)getIntent().getExtras().getString("f1") ;
        f2 = (String)getIntent().getExtras().getString("f2") ;
        f3 = (String)getIntent().getExtras().getString("f3") ;
        f4 = (String)getIntent().getExtras().getString("f4") ;
        f5 = (String)getIntent().getExtras().getString("f5") ;
        f6 = (String)getIntent().getExtras().getString("f6") ;
        f7 = (String)getIntent().getExtras().getString("f7") ;
        f8 = (String)getIntent().getExtras().getString("f8") ;
        f9 = (String)getIntent().getExtras().getString("f9") ;

        f10 = (String)getIntent().getExtras().getString("f10") ;
        f11 = (String)getIntent().getExtras().getString("f11") ;
        f12 = (String)getIntent().getExtras().getString("f12") ;
        f13 = (String)getIntent().getExtras().getString("f13") ;
        f14 = (String)getIntent().getExtras().getString("f14") ;
        f15 = (String)getIntent().getExtras().getString("f15") ;
        f16 = (String)getIntent().getExtras().getString("f16") ;

        txtdata.setText(ContactAll(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,"WDM01-1-S+M","\n", "•"," "));

        Toast.makeText(CableDetailDatatagwriting.this,
                f1+"\n"+
                        f2+"\n"+
                        f3+"\n"+
                        f4+"\n"+
                        f5+"\n"+
                        f6+"\n"+
                        f7+"\n"+
                        f8+"\n"+
                        f9+"\n"+
                        f10+"\n"+
                        f11+"\n"+
                        f12+"\n"+
                        f13+"\n"+
                        f14+"\n"+
                        f15+"\n"+
                        f16+"\n"
                , Toast.LENGTH_SHORT).show();

            txtrfidwriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();   // bundle for data
                Toast.makeText(CableDetailDatatagwriting.this, "rfid writing ! ", Toast.LENGTH_SHORT).show();
                // the following cdoes is responsible for getting rfid tag's epc and passing it to next activity
                //
                //
                //
                bundle.putString("EPC","A1B20000000000000000000");
                bundle.putString("f1",f1.toString());
                bundle.putString("f2",f2.toString());
                bundle.putString("f3",f3.toString());
                bundle.putString("f4",f4.toString());
                bundle.putString("f5",f5.toString());
                bundle.putString("f6",f6.toString());
                bundle.putString("f7",f7.toString());
                bundle.putString("f8",f8.toString());
                bundle.putString("f9",f9.toString());
                bundle.putString("f10",f10.toString());
                bundle.putString("f11",f11.toString());
                bundle.putString("f12",f12.toString());
                bundle.putString("f13",f13.toString());
                bundle.putString("f14",f14.toString());
                bundle.putString("f15",f15.toString());
                bundle.putString("f16",f16.toString());
                bundle.putString("f17","WDM01-1-S+M");


                finish();

                Intent intentforRfidtagCabledetails = new Intent() ;
                intentforRfidtagCabledetails.setClass(v.getContext() , rfidtagwritingcontent.class) ;
                intentforRfidtagCabledetails.putExtras(bundle);  // put those data and send it all out
                startActivity(intentforRfidtagCabledetails);     // put those data to next activity


            }
        });




    }

    String ContactAll(String f1,String f2, String f3 , String f4 , String f5 ,
                      String f6, String f7, String f8 , String f9 , String f10 ,
                      String f11 , String f12, String f13, String f14, String f15 , String f16 , String f17,
                      String ret , String dot , String space )
    {

        return (
                dot+space+"TAG ID" + space + ":" + space +f1+ret+
                dot+space+"光纜名稱"+ space + ":"  + space +f2+ret+
                dot+space+"FROM"  +  space + ":" + space +f3+ret+
                dot+space+"TO"     + space + ":" + space +f4+ret+
                dot+space+"樓層" +   space + ":" +  space +f5+ret+
                dot+space+"機房名稱" +space + ":" +  space +f6+ret+
                dot+space+"排"+ space + ":" +  space +f7+ret+
                dot+space+"櫃"+ space + ":" + space  +f8+ret+
                dot+space+"箱"+ space + ":" + space+f9+ret+
                dot+space+"芯"+ space + ":" + space+f10+ret+
                dot+space+"備註"+ space + ":" + space+f11+ret+
                dot+space+"纜線廠商"+space + ":" +  space+f12+ret+
                dot+space+"設備名稱"+space + ":" +  space+f13+ret+
                dot+space+"建置廠商"+ space + ":" + space+f14+ret+
                dot+space+"光測架構"+ space + ":" + space+f15+ret+
                dot+space+"設備模組"+ space + ":" + space+f16+ret+
                dot+space+"模組編號"+ space + ":" + space+f17);
    }
}