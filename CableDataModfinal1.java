package com.tra.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;





public class CableDataModfinal1 extends AppCompatActivity {

    String f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16 ;

    Button confirmbtn ;

    private TextView txtdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_data_modfinal1);

        f1 = (String)getIntent().getExtras().getString("EPC") ;
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

        txtdata = (TextView) findViewById(R.id.txtrfidtagcont) ;
        txtdata.setTextColor(Color.BLACK);
        txtdata.setTextSize(17);

        txtdata.setText(ContactAll(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,"WDM01-1-S+M","\n", "•"," "));

        confirmbtn = (Button)findViewById(R.id.rfidconfbtn);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(v.getContext() , RFIDTagWritingWithOLDF.class);
                startActivity(intent);
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
                        dot+space+"From"  +  space + ":" + space +f3+ret+
                        dot+space+"To"     + space + ":" + space +f4+ret+
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