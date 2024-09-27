package com.tra.loginscreen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import android.app.NotificationManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
/*
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
*/


import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


class FiberOpCableDat {
    private String No ;         // cable serial number (serial No.)
    private String TagNo ;      // Tag ID
    private String routerNo ;   // router no.
    private String RoomFrom ;   // Room From
    private String RoomFloorFrom ;
    private String CabinetNoFrom ;       // Cabinet No.
    private String SpliceCaseNoFrom  ;   // splice case No.
    private String PortNoFrom    ;       // Port No.
    private String CableNameFrom ;       // Cable name
    private String RoomTo ;              // Room to
    private String SpliceCaseNoTo  ;
    private String CableNameTo     ;
    private String PortNoTo        ;
    private String Comments        ;
    private String BuildingCompany ;
    private String BuildingEmployee ;
    private String UpdateDate ;
    private String Maintainer ;

            public FiberOpCableDat(
            String No ,          // cable serial number (serial No.)
            String TagNo ,       // Tag ID
            String routerNo ,    // router no.
            String RoomFrom ,    // Room From
            String RoomFloorFrom ,
            String CabinetNoFrom ,        // Cabinet No.
            String SpliceCaseNoFrom  ,    // splice case No.
            String PortNoFrom    ,        // Port No.
            String CableNameFrom ,        // Cable name
            String RoomTo ,               // Room to
            String SpliceCaseNoTo  ,
            String CableNameTo     ,
            String PortNoTo        ,
            String Comments        ,
            String BuildingCompany ,
            String BuildingEmployee ,
            String UpdateDate ,
            String Maintainer
    )
    {

        assert( No    !=null  &&
                TagNo != null &&
                routerNo !=null  &&
                RoomFrom != null &&
                RoomFloorFrom != null &&
                CabinetNoFrom !=null &&
                SpliceCaseNoFrom !=null &&
                PortNoFrom !=null &&
                CableNameFrom !=null &&
                RoomTo !=null &&
                SpliceCaseNoFrom !=null &&
                CableNameTo !=null &&
                PortNoTo !=null &&
                Comments !=null &&
                BuildingCompany  !=null &&
                BuildingEmployee !=null &&
                UpdateDate !=null &&
                Maintainer !=null ) ;


        this.No= No ;         // cable serial number (serial No.)
        this.TagNo = TagNo ;       // Tag ID
        this.routerNo = routerNo ;    // router no.
        this.RoomFrom = RoomFrom ;    // Room From
        this.RoomFloorFrom = RoomFloorFrom;
        this.CabinetNoFrom = CabinetNoFrom;       // Cabinet No.
        this.SpliceCaseNoFrom = SpliceCaseNoFrom ;    // splice case No.
        this.PortNoFrom = PortNoFrom ;        // Port No.
        this.CableNameFrom = CableNameFrom ;         // Cable name
        this.RoomTo = RoomTo ;               // Room to
        this.SpliceCaseNoTo = SpliceCaseNoTo  ;
        this.CableNameTo     = CableNameTo ;
        this.PortNoTo = PortNoTo  ;
        this.Comments  = Comments ;
        this.BuildingCompany = BuildingCompany ;
        this.BuildingEmployee = BuildingEmployee ;
        this.UpdateDate = UpdateDate ;
        this.Maintainer = Maintainer ;

    }
    public String getNo() {
        assert(No!=null);
        return No;
    }
    public void setNo(String No){
        assert(No!=null);
        this.No= No ;
    }
    public String getTagNo() {
        assert(TagNo!=null);
        return TagNo;
    }
    public void setTagNo(String TagNo){
        assert(TagNo!=null);
        this.TagNo=TagNo ;
    }
    public String getRouterNo() {
        assert(routerNo!=null);
        return routerNo;
    }
    public void setRouterNo(String routerNo){
        assert(routerNo!=null);
        this.routerNo = routerNo ;
    }
    public String getRoomFrom() {
        assert(RoomFrom!=null);
        return RoomFrom ;
    }
    public void setRoomFrom(String RoomFrom){
        assert(RoomFrom!=null);
        this.RoomFrom= RoomFrom ;
    }

    public String getRoomFloorFrom() {
        assert(RoomFloorFrom!=null);
        return RoomFloorFrom;
    }
    public void setRoomFloorFrom(String RoomFloorFrom){
        assert(RoomFloorFrom!=null);
        this.RoomFloorFrom= RoomFloorFrom ;
    }

    public String getCabinetNoFrom() {
        assert(CabinetNoFrom!=null);
        return  CabinetNoFrom ;
    }
    public void setCabinetNoFrom(String CabinetNoFrom) {
        assert(CabinetNoFrom!=null);
        this.CabinetNoFrom= CabinetNoFrom ;
    }

    public String getSpliceCaseNoFrom() {
        assert(SpliceCaseNoFrom!=null);
        return SpliceCaseNoFrom ;
    }
    public void setSpliceCaseNoTo(String SpliceCaseNoTo) {
        assert(SpliceCaseNoTo!=null);
        this.SpliceCaseNoTo = SpliceCaseNoTo ;
    }
    public String getPortNoFrom() {
        assert(PortNoFrom!=null);
        return PortNoFrom ;
    }
    public void setPortNoFrom(String PortNoFrom){
        assert(PortNoFrom!=null);
        this.PortNoFrom= PortNoFrom ;
    }

    public String getCableNameFrom(){
        assert(CableNameFrom!=null);
        return CableNameFrom ;
    }
    public void setCableNameFrom(String CableNameFrom){
        assert(CableNameFrom!=null);
        this.CabinetNoFrom=CableNameFrom ;
    }
    public String getRoomTo(){
        assert(RoomTo!=null);
        return RoomTo ;
    }
    public void setRoomTo(String RoomTo){
        assert(RoomTo!=null);
        this.RoomTo=RoomTo ;
    }
    public String getSpliceCaseNoTo(){
        assert(SpliceCaseNoTo!=null);
        return SpliceCaseNoTo ;
    }
    public void setSpliceCaseNoFrom(String SpliceCaseNoFrom){
        assert(SpliceCaseNoFrom!=null);
        this.SpliceCaseNoFrom= SpliceCaseNoFrom;
    }
    public String getCableNameTo(){
        assert(CableNameTo!=null);
        return CableNameTo ;
    }
    public void setCableNameTo(String CableNameTo){
        assert(CableNameTo!=null);
        this.CableNameTo=CableNameTo ;
    }
   public String getPortNoTo() {
        assert(PortNoTo!=null);
        return PortNoTo ;
   }
   public void setPortNoTo(String PortNoTo){
        assert(PortNoTo!=null);
        this.PortNoTo= PortNoTo;
   }
   public String getComments(){
        assert(Comments!=null);
        return Comments ;
   }
   public void setComments(String Comments){
        assert(Comments!=null);
        this.Comments=Comments;
   }
   public String getBuildingCompany(){
        assert(BuildingCompany!=null);
        return BuildingCompany ;
   }
   public void setBuildingCompany(String BuildingCompany){
        assert(BuildingCompany!=null);
        this.BuildingCompany=BuildingCompany ;
   }
    public String getBuildingEmployee() {
        assert(BuildingEmployee!=null);
        return BuildingEmployee;
    }

    public void setBuildingEmployee(String BuildingEmployee) {
        assert(BuildingEmployee!=null);
        this.BuildingEmployee= BuildingEmployee ;
    }

    public String getUpdateDate(){
        assert(UpdateDate!=null);
        return UpdateDate ;

    }
    public void setUpdateDate(String UpdateDate){
        assert(UpdateDate!=null);
        this.UpdateDate=UpdateDate ;
    }
    public String getMaintainer() {
        assert(Maintainer!=null);
        return Maintainer;
    }
    public void setMaintainer(String Maintainer) {
        assert(Maintainer!=null);
        this.Maintainer=Maintainer ;
    }

} // end of class

 class ShowToastClickListener implements View.OnClickListener{

     @Override
     public void onClick(View v) {

         v.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (false) {
                     Toast.makeText(v.getContext(),
                             "開啟toast",
                             Toast.LENGTH_SHORT).show();
                 }
             }  // end of onclick

                 // it is useless
                 public boolean onTouchEvent(MotionEvent event) {
                     // Tap anywhere to close dialog.
                     final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.CustomAlertDialog);
                     builder.create().dismiss();
                     return true;
                 }

                 // v.setVisibility(View.INVISIBLE);

         });

     }
 }

    public class DataImport extends AppCompatActivity {
        static int rowno = 0;
        FloatingActionButton fab;  // floating action button
        boolean isOpened;   // notification is opened
        String channelId = "channel1";
        String channelName = "name";
        // FiberOpCableDat FiberObject ;
        // Workbook history_book; // excel workbook
        private TextView textshow;
        private TextView CableNo ;
        // MyAdapter adapter;



        RecyclerView recyclerView;
        // RecyclerViewAdapter recyclerViewAdapter;
        List<String> titleList = new ArrayList<String>();

        String TAG = "mExample";
        RecyclerView mRecyclerView;
        MyListAdapter myListAdapter;
        int itemsNum ;
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        SwipeRefreshLayout swipeRefreshLayout ;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_data_import);

            textshow = findViewById(R.id.textshow);

            arrayList.clear(); // initialize array list
            // itemsNum = readExcelFileFromAssets_xls(arrayList);  // getting number of total items in excel file

            swipeRefreshLayout = findViewById(R.id.refreshLayout);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.purple));


            ShowToastClickListener clickListener = new ShowToastClickListener();
            // Generating  data
            // you should add total data from excel file to this loop
                        for (int i = 0; i < itemsNum + 1; i++) {

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("No", "編號：" + String.format("%02d", i + 1));
                            hashMap.put("Sub1", String.valueOf(new Random().nextInt(80) + 20));
                            hashMap.put("Sub2", String.valueOf(new Random().nextInt(80) + 20));

                            hashMap.put("Sub3", String.valueOf(
                                    (Integer.parseInt(hashMap.get("Sub1"))
                                            + Integer.parseInt(hashMap.get("Sub2"))) / 2));

                            arrayList.add(hashMap);
                        }


            swipeRefreshLayout = findViewById(R.id.refreshLayout);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.purple));

            // The action which is responsible for omitting the refresh cycle icon.
            //
            swipeRefreshLayout.setOnRefreshListener(()->{
                myListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            });

            mRecyclerView = findViewById(R.id.recycleview);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            // omit 項目分隔線 , Currently , we must omit it.
            // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            myListAdapter = new MyListAdapter();
            mRecyclerView.setAdapter(myListAdapter);

        }  // end of onCreate


        private Toast mToastToShow; // the default  toast object
        // the following is a custom toast
        public void showToast(View view, int period ) {

            int toastDurationInMilliSeconds = period*1000;
            mToastToShow = Toast.makeText(this, "Hello world, I am a toast.", Toast.LENGTH_LONG);
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
                public void onTick(long millisUntilFinished) {
                    mToastToShow.show();
                }

                public void onFinish() {
                    mToastToShow.cancel();
                }
            };


            mToastToShow.show();
            toastCountDown.start();
        }   // the end of showToast


        private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


            class ViewHolder extends RecyclerView.ViewHolder{
                private TextView tvTitle,
                                 tvSub1 ,
                                 tvSub2 ,
                                 tvSub3 ;

                TextView title ;
                private View mView;


                public ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    tvTitle = itemView.findViewById(R.id.textView_title); // Title of sequential number
                    Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(), "boldttf.otf");
                   //  tvTitle.setTypeface(type);

                    tvSub1 = itemView.findViewById(R.id.textView_sub1);
                    tvSub2 = itemView.findViewById(R.id.textView_sub2);
                    tvSub3 = itemView.findViewById(R.id.textView_sub3);
                    mView  = itemView;

                }
            }
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item,parent,false);

                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


                // int avgS = Integer.parseInt(arrayList.get(position).get("Sub3"));

                // holder.tvTitle.setBackgroundColor(getColor(R.color.purple));

                holder.tvTitle.setText(arrayList.get(position).get("No")); // title no.
                // construct the color of title
                holder.tvTitle.setTextColor(Color.rgb(20,50,200));
                holder.tvSub1.setText(arrayList.get(position).get("Sub1"));
                holder.tvSub2.setText(arrayList.get(position).get("Sub2"));
                holder.tvSub3.setText(arrayList.get(position).get("Sub3"));

                Toast.makeText(DataImport.this, ""+holder.tvTitle.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DataImport.this, ""+holder.tvSub1.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DataImport.this, ""+holder.tvSub2.getText(), Toast.LENGTH_SHORT).show();

                // Toast.makeText(getBaseContext(), ""+No.getText(), Toast.LENGTH_SHORT).show();
                // The following codes show a toast when user click item in a view.

                holder.mView.setOnClickListener(
                        (v)->
                    {
                        // String No = (String) holder.tvTitle.getText();


                         // title.setText(No.toString());
                        ShowToastClickListener clickListener = new ShowToastClickListener();

                        final AlertDialog.Builder builder = new AlertDialog.Builder(DataImport.this,R.style.CustomAlertDialog);


                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.customdailog, viewGroup, false);
                        // dialogView.setOnClickListener(clickListener);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        TextView CableNo = dialogView.findViewById(R.id.cableno) ;
                       //  CableNo.setText(holder.tvSub1.getText());
                        MediaPlayer mediaplayer = new MediaPlayer();// 构建MediaPlayer对象

                        builder.setView(dialogView);  // Create the view of dialog

                        clickListener = new ShowToastClickListener();  // onclick listener

                        final AlertDialog alertDialog = builder.create();
                        dialogView.setOnClickListener(clickListener);

                          alertDialog.setCanceledOnTouchOutside(false);


                        buttonOk.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {

                                  alertDialog.dismiss();
                                  }
                           });

                             alertDialog.show(); // the dialog which shows information of cable

                    });

            } // end of MyListAdapter

              // the following codes define action of item view.
              // this method is responsible for handling swipe action.
              // But now it is not available !

            private void recyclerViewAction(RecyclerView recyclerView,
                                            final ArrayList<HashMap<String,String>> choose,
                                            final MyListAdapter myAdapter) {

                // the helper is a utility class object which adds swipe to dismiss and drag &drop support to RecyclerView.
                ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                                , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
                    }

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView
                            , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        // Notice ! you should use getBindingAdapterPosition()

                        int position_dragged = viewHolder.getBindingAdapterPosition() ;       //選取的位置
                        int position_target = target.getBindingAdapterPosition();             //放置的位置

                        //交換陣列中兩資料位置，choose為陣列
                        Collections.swap(choose,position_dragged,position_target);


                        //告知配適器資料有更換
                        // adapter.notifyItemMoved(position_dragged,position_target);

                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getBindingAdapterPosition();

                        switch(direction){
                            case ItemTouchHelper.LEFT:                  //資料左滑
                            case ItemTouchHelper.RIGHT:
                                Toast.makeText(DataImport.this, ""+"Swiped ...", Toast.LENGTH_SHORT).show();
                                choose.remove(position);               //移除資料
                                // adapter.notifyItemRemoved(position);   //告知配適器資料有更換

                                if(position!=choose.size()){
                                    //開始改變的位置和總共被影響的位置數量，相當重要否則資料的position不會更改
                                    //刮號內是(改變的位置,受影響的數量)
                                   //  adapter.notifyItemRangeChanged(position,choose.size()-position);
                                }
                                break;
                        }
                    }


                });

                helper.attachToRecyclerView(recyclerView);
            }

            @Override
            public int getItemCount() {
                return arrayList.size();
            }
        }


        /*
        public int readExcelFileFromAssets_xls( ArrayList<HashMap<String,String>> CableDat) {

            FileOutputStream fos = null;
            int index = 1;
            int size = 0 ;

            try {

                InputStream myInput;
                // initialize asset manager
                AssetManager assetManager = getAssets();
                //  open excel sheet
                myInput = assetManager.open("rfidtry.xls");
                // Create a POI File System object
                POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                // Create a workbook using the File System
                HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                // Get the first sheet from workbook
                HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                // We now need something to iterate through the cells.


                Iterator<Row> rowIter = mySheet.rowIterator();

                int rowno = 0; // setting the first row

                textshow.append("\n");

                while (rowIter.hasNext()) {

                    HSSFRow myRow = (HSSFRow) rowIter.next();

                    if (rowno != 0) {
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        int colno = 0;
                        String sno = "", date = "", det = "";

                        while (cellIter.hasNext()) {
                            HSSFCell myCell = (HSSFCell) cellIter.next();
                            if (colno == 0) {
                                sno = myCell.toString();
                            } else if (colno == 1) {
                                date = myCell.toString();
                            } else if (colno == 2) {
                                det = myCell.toString();
                            }
                            colno++;
                        }
                       //  textshow.append(sno + " -- " + date + "  -- " + det + "\n");
                    }
                    rowno++;
                    size++ ;
                }
            } catch (Exception e) {
                Log.e(TAG, "error " + e.toString());
            }

         return size ;  // return number of items
        }
*/
    }




