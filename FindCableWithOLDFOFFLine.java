package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import static com.tra.loginscreen.DataManipulation2.TAG;
        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;

public class FindCableWithOLDFOFFLine extends AppCompatActivity {

    ImageView backimg ;   // backarrow on toolbar
    ImageView CableDataimg ;  // Cable Data image object
    ImageView OLDFDataimg  ;  // oldf Data image object

    PressImageView pressbacking ;
    PressImageView pressCableDataimg ;
    PressImageView pressOLDFDataimg ;


    private int offonline ;   // tracking offonline route

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cable_with_oldfoffline);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 豎屏

        backimg      = (ImageView) findViewById(R.id.imgback) ;
        // without effectiveness
        // CableDataimg = (ImageView) findViewById(R.id.cabledatimg) ;
        // OLDFDataimg  = (ImageView) findViewById(R.id.oldfdatimg);

        // with effectiveness - imageview click 纜線資料 , oldf 資料 ///////////
        // 20240414 - peter

        pressCableDataimg  =(PressImageView) findViewById(R.id.pressimgcabledata);
        pressOLDFDataimg  = (PressImageView) findViewById(R.id.pressoldfdatimg) ;

        // 纜線資料找尋 imageview

        pressCableDataimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"pressCableDataimg.setOnClickListener()") ;
                // finish();
                Intent intent = new Intent(v.getContext() , FindCableDataOFFLine.class);  // 纜線資料
                                                                                          // (這裡必須有一個 offline 的  FindCableData )
                v.getContext().startActivity(intent);

            }
        });  // end of 纜線資料找尋 click

        pressOLDFDataimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"pressOLDFDataimg.setOnClickListener()") ;

                finish();
                overridePendingTransition(R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);

                Intent intent = new Intent(v.getContext() , OLDFDataFind.class);  // oldf 資料
                v.getContext().startActivity(intent);
            }
        });


        Bundle bundle = this.getIntent().getExtras();   // new a bundle

        if (bundle != null ) {

            int offonline = bundle.getInt("offonline");   // off / on line check
            SetOffOneLine(offonline);
        }


        backimg.setOnClickListener(new View.OnClickListener() {
            // 回到主畫面
            @Override
            public void onClick(View v) {

                // finish(); -- 這個地方不要用, 因為finish 中有 overridePendingTransition 會有殘影
                overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);  // 進場動畫(從下到上)
                Intent intent = new Intent(v.getContext(), OffLineMainSquare.class);  // back to offline main menu

                v.getContext().startActivity(intent);

                    /* 離線查詢
                    finish();
                    Intent intent = new Intent(v.getContext(), OffLineMainSquare.class);  // back to offline main menu
                    startActivity(intent);
                    */

            }
        });

        /*
        CableDataimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Log.d(TAG,"CableDataimg.setOnClickListener()") ;

                finish();
                Intent intent = new Intent(v.getContext() , FindCableData.class);  // 纜線資料
                startActivity(intent);

            }
        });   //  cable data searching activity
         */
        /*
        OLDFDataimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent intent = new Intent(v.getContext() , OLDFDataFind.class);
                startActivity(intent);

            }

        });   // oldf data find activity

         */

    }



    @Override
    public void finish() {

        super.finish();
        overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);  // 進場動畫(從下到上)

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed(); //此行根据情况可以被 comments
        finish();
        Log.d(TAG,"onBackPressed was actived ");
        Intent i = new Intent(FindCableWithOLDFOFFLine.this , OffLineMainSquare.class);  // 回到離線畫面
        // startActivity(i);

        // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);
    }


    public void SetOffOneLine(int onoffline){
        this.offonline = onoffline  ;

    }

    public int GetOffOnLine(){
        return this.offonline  ;

    }
}