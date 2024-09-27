package com.tra.loginscreen;

import static com.tra.loginscreen.DataManipulation2.TAG;
import static com.tra.loginscreen.SearchCableDatafortagwriting.mActivitySearchCableDataforwriting;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class CustomerAdapterforRfidTagWriting extends RecyclerView.Adapter<CustomerAdapterforRfidTagWriting.MyViewHolder> {
    //create a list to pass our Model class
    ArrayList<SearchCableDatarfidforModObj> modelList;
    Context context;
    public CustomerAdapterforRfidTagWriting(ArrayList<SearchCableDatarfidforModObj> modelList, Context context) {

        this.modelList = modelList;
        this.context = context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate our custom view

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout,parent,false);

        MyViewHolder MV ;
        MV = new MyViewHolder(view);

        assert MV != null ;


        return MV ;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //bind all custom views by its position
        //to get the positions we call our Model class
        final SearchCableDatarfidforModObj model = modelList.get(position);

        holder.cablename.setText ("光纜名稱: " +model.getf3());      //  纜線名稱
        holder.idcname.setText   ("機房名稱: " +model.getf7());      //  機房名稱
        holder.portno.setText    ("埠號: "    +model.getf11());     //  埠號
        holder.devicename.setText("裝置名稱: " +model.getf14());     //  裝置名稱

        // holder.imageView.setImageDrawable(context.getResources().getDrawable(model.getImage()));
        // click listener
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,details.class);
                intent.putExtra("image",model.getImage());

                Toast.makeText(context, "第幾個" + position, Toast.LENGTH_SHORT).show();

                Log.d(TAG,"CCCCCCCCCCCCCCCCCCCCCCCCCCC");

                Log.d(TAG, "Tag ID:" + model.getf1());
                Log.d(TAG, "狀態 :" + model.getf2());
                Log.d(TAG, "纜線名稱 :" + model.getf3());
                Log.d(TAG, "From :" + model.getf4());
                Log.d(TAG, "To :" + model.getf5());
                Log.d(TAG, "Floor :" + model.getf6());
                Log.d(TAG, "機房名稱 :" +model.getf7());
                Log.d(TAG, "排 :" +model.getf8());
                Log.d(TAG, "櫃 :" + model.getf9());
                Log.d(TAG, "箱 :" + model.getf10());
                Log.d(TAG, "埠 :" + model.getf11());
                Log.d(TAG, "備註 :" + model.getf12());
                Log.d(TAG, "纜線廠商 :" + model.getf13());
                Log.d(TAG, "設備名稱 :" + model.getf14());
                Log.d(TAG, "建置廠商 :" + model.getf15());
                Log.d(TAG, "傳輸送收端 :" + model.getf16());
                Log.d(TAG, "傳輸設備Port位 :" +model.getf17());
                Log.d(TAG, "修改人 :" +model.getf18());
                Log.d(TAG, "修改時間 :" + model.getf19());

                // 0322 完成
                // 必須將資料傳送出去 - 這裡必須改一下
                // Intent i = new Intent();
                // i.setClass(v.getContext(), CableDataModification.class);
                // i.setClass(v.getContext(), )
                // 標籤寫入 -

                Bundle bundle = new Bundle();

                // assert bundle != null ;
                Log.d(TAG,"XXXXXXXX");

                // 要顯示的欄位 //////////////////////////////////////////////////////

                bundle.putString("tagid",model.getf1());
                bundle.putString("status",model.getf2());
                Log.d(TAG,"GGGGGGGGGGGGGGGGGGG " + model.getf2());
                bundle.putString("cablename",model.getf3());


                // from , to 是必須由 rfidlist / jsonData 中取得

                bundle.putString("from",model.getf4());
                bundle.putString("to",model.getf5());

                bundle.putString("floor",model.getf6());
                bundle.putString("idcroom",model.getf7());
                bundle.putString("row",model.getf8());
                bundle.putString("cabinet",model.getf9());
                bundle.putString("box",model.getf10());
                bundle.putString("port",model.getf11());
                bundle.putString("comment",model.getf12());
                bundle.putString("cablefirm",model.getf13());
                bundle.putString("equname",model.getf14());
                bundle.putString("buildingfirm", model.getf15());
                bundle.putString("transreciever", model.getf16());
                bundle.putString("transport",model.getf17());
                bundle.putString("updater",model.getf18());
                bundle.putString("updatetime", model.getf19());

                // i.putExtras(bundle);

                notifyItemChanged(position);  // ripple effect ( but it is unless ! )

                showmeidialog(v);  // 顯示資料加載提示動畫

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過兩秒後要做的事情
                        Intent i = new Intent();
                        i.setClass(v.getContext(), CableDataforRfidWritng.class);  // 顯示 rfid 纜線資料
                        i.putExtras(bundle);
                        v.getContext().startActivity(i);

                    }}, 845);


                // overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_push_bottom_out);
                // intent.putExtra("name",model.getName());
                // intent.putExtra("tag",model.getTag());
                // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // context.startActivity(intent);

            }
        });  // 短按 !

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "這是纜線寫入功能 , 長按第" + position + "個", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void showmeidialog(View v) {

        CustomProgressDialog dialog = new CustomProgressDialog(v.getContext(), "纜線資料載入中.....", R.drawable.load);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(false);//设置是否可以点击外部消失
        dialog.setCancelable(false);//设置是否可以按退回键取消
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                //過兩秒後要做的事情

                dialog.dismiss();

            }}, 3000);

    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }
    //all the custom view will be hold here or initialize here inside MyViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView cablename, idcname , portno , devicename ;
        ImageButton imgbtn  ;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);         // 主圖

            // 顯示的欄位 /////////////////////////////////

            cablename  = itemView.findViewById(R.id.cablename);       // 光纜名稱
            idcname    = itemView.findViewById(R.id.idcname);         // 機房名稱
            portno     = itemView.findViewById(R.id.portno) ;         // 埠號
            devicename = itemView.findViewById(R.id.devicename) ;     // 裝置名稱

            relativeLayout = itemView.findViewById(R.id.item);     // view 的實體化

        }   // end of MyViewHolder
    }
}