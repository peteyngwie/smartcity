package com.tra.loginscreen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Environment;
import com.tra.loginscreen.ExcelRead ; // using excel object
// import com.tra.loginscreen.TableMainLayout; // custom table
// import com.tra.loginscreen.ExcelUtils ;
import android.util.Log;
// import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import com.tra.loginscreen.TableMainLayout;
import com.tra.loginscreen.SampleObject ;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
// import com.tra.loginscreen.TableMainLayout ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.tra.loginscreen.ExcelRead ;
// import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import android.content.DialogInterface;

      //  import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


class FiberOpCableDat1 {
    private String f1  ;         // cable serial number (serial No.)
    private String f2  ;      // Tag ID
    private String f3  ;   // router no.
    private String f4  ;   // Room From
    private String f5  ;
    private String f6  ;       // Cabinet No.
    private String f7  ;   // splice case No.
    private String f8  ;       // Port No.
    private String f9  ;       // Cable name
    private String f10 ;              // Room to
    private String f11 ;
    private String f12 ;
    private String f13 ;
    private String f14 ;
    private String f15 ;
    private String f16 ;
    private String f17 ;
    private String f18 ;

    public FiberOpCableDat1(
            String f1 ,          // cable serial number (serial No.)
            String f2 ,       // Tag ID
            String f3 ,    // route no.
            String f4 ,    // Room From
            String f5 ,
            String f6 ,        // Cabinet No.
            String f7  ,    // splice case No.
            String f8    ,        // Port No.
            String f9 ,        // Cable name
            String f10 ,               // Room to
            String f11  ,
            String f12     ,
            String f13        ,
            String f14       ,
            String f15 ,
            String f16 ,
            String f17 ,
            String f18
    )
    {

        this.f1= f1 ;         // cable serial number (serial No.)
        this.f2 = f2 ;       // Tag ID
        this.f3 = f3 ;    // router no.
        this.f4 = f4 ;    // Room From
        this.f5 = f5;
        this.f6 = f6;       // Cabinet No.
        this.f7 = f7 ;    // splice case No.
        this.f8 = f8 ;        // Port No.
        this.f9 = f9 ;         // Cable name
        this.f10= f10;               // Room to
        this.f11 = f11  ;
        this.f12 = f1 ;       // Tag ID
        this.f13 = f3 ;    // router no.
        this.f14 = f14 ;    // Room From
        this.f15 = f15;
        this.f16 = f16;       // Cabinet No.
        this.f17 = f17 ;    // splice case No.
        this.f18 = f18 ;        // Port No.

    }

    public String getf1() {  return  f1 ;   }
    public String getf2() {  return  f2 ;   }
    public String getf3() {  return  f3 ;   }
    public String getf4() {  return  f4 ;   }
    public String getf5() {  return  f5 ;   }
    public String getf6() {  return  f6 ;   }
    public String getf7() {  return  f7 ;   }
    public String getf8() {  return  f8 ;   }
    public String getf9() {  return  f9 ;   }
    public String getf10(){  return f10 ;   }
    public String getf11(){  return f11 ;   }
    public String getf12(){  return f12 ;   }
    public String getf13(){  return f13 ;   }
    public String getf14(){  return f14 ;   }
    public String getf15(){  return f15 ;   }
    public String getf16(){  return f16 ;   }
    public String getf17(){  return f17 ;   }
    public String getf18(){  return f18 ;   }

} // end of class


public class DataManipulation2 extends AppCompatActivity {

    int rowno = 0;
    FloatingActionButton fab;  // floating action button
    boolean isOpened;   // notification is opened
    String channelId = "channel1";
    String channelName = "name";
    // FiberOpCableDat FiberObject ;
    //  Workbook history_book; // excel workbook
    private TextView textshow;
    private TextView CableNo;
    // MyAdapter adapter;


    RecyclerView recyclerView;
    // RecyclerViewAdapter recyclerViewAdapter;
    List<String> titleList = new ArrayList<String>();
    List<FiberOpCableDat1> arrayLST = new ArrayList<>();

    public static String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    int itemsNum;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new TableMainLayout(this));


        //  setContentView(new TableMainLayout(this));
        //TableMainLayout tablefor = new TableMainLayout(this);
        // setContentView(tablefor);
        //  ExcelRead excelRead ;
        //  excelRead = new ExcelRead("rfidtry.xls") ;
        //  int nums = readExcelFileFromAssets_xls(arrayLST);  //
        //  ExcelUtils excelobject;
        //  readExcelFileFromAssets_xls();

        // readExcelFile(getApplicationContext(),"rfidtry.xls");
        // Toast.makeText(this, "筆數 :" + Integer.toString(nums), Toast.LENGTH_SHORT).show();
        //  File xx =

        /*
        if (false) {
            textshow = findViewById(R.id.textshow2);
            textshow.setTextColor(Color.WHITE);
            textshow.setTypeface(Typeface.DEFAULT_BOLD);
            textshow.setTextSize(6);
        } */

        // readExcelFileFromAssets();
/*
        textshow = findViewById(R.id.textshow);

        arrayList.clear(); // initialize array list
        itemsNum = readExcelFileFromAssets_xls(arrayList);  // getting number of total items in excel file

        swipeRefreshLayout = findViewById(R.id.refreshLayoutDataMan);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.purple));


        ShowToastClickListener clickListener = new ShowToastClickListener();
        // Generating  data
        // you should add total data from excel file to this loop
        for (int i = 1; i < itemsNum ; i++) {

            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("No", "光纜資料 " + Integer.toString(i));
            hashMap.put("Sub1", String.valueOf(new Random().nextInt(80) + 20));
            hashMap.put("Sub2", String.valueOf(new Random().nextInt(80) + 20));

            hashMap.put("Sub3", String.valueOf(
                    (Integer.parseInt(hashMap.get("Sub1"))
                            + Integer.parseInt(hashMap.get("Sub2"))) / 2));

            arrayList.add(hashMap);
        }


        swipeRefreshLayout = findViewById(R.id.refreshLayoutDataMan);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.purple));

        // The action which is responsible for omitting the refresh cycle icon.
        //
        swipeRefreshLayout.setOnRefreshListener(()->{
            myListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });

        mRecyclerView = findViewById(R.id.recycleviewDataManre);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // omit 項目分隔線 , Currently , we must omit it.
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);

*/
/*
    }  // end of onCreate
*/
/*
    public void readExcelFileFromAssets() {

 */

        /* Notice ! this method just handles suffix is .xls  .
         * please convert .xlsx suffix to .xls via https://cloudconvert.com/
         */

        /*
        try {

            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = getAssets();
            //  open excel sheet
            myInput = assetManager.open("rfidxlsx.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            textshow.append("\n");

            while (rowIter.hasNext()) {

                Log.e(TAG, " Serial No."+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();

                if(rowno !=0) {

                    Iterator<Cell> cellIter = myRow.cellIterator();

                    int colno =0;


                    String  f1="",
                            f2="",
                            f3="",
                            f4="",
                            f5="",
                            f6="",
                            f7="",
                            f8="",
                            f9="",
                            f10="" ,
                            f11="" ,
                            f12="" ,
                            f13="" ,
                            f14="" ,
                            f15="" ,
                            f16="" ,
                            f17="" ,
                            f18="" ;

                    while (cellIter.hasNext()) {

                        HSSFCell myCell = (HSSFCell) cellIter.next();

                        if (colno==0){
                            f1 = myCell.toString();  // 標籤序號
                        }else if (colno==1){
                            f2 = myCell.toString();  // 路由編號
                        }else if (colno==2){
                            f3 = myCell.toString();
                        }else if (colno==3){
                            f4 = myCell.toString();
                        }else if (colno==4){
                            f5 = myCell.toString();
                        }else if (colno==5){
                            f6 = myCell.toString();
                        }else if (colno==6){
                            f7 = myCell.toString();
                        }else if (colno==7){
                            f8 = myCell.toString();
                        }else if (colno==8){
                            f9 = myCell.toString();
                        }else if (colno==9){
                            f10 = myCell.toString();
                        }else if (colno==10) {
                            f11 = myCell.toString();
                        }else if (colno==11) {
                                f12 = myCell.toString();
                        }else if (colno==12) {
                                f13 = myCell.toString();
                        }else if (colno==13) {
                                f14 = myCell.toString();
                        }else if (colno==14) {
                                f15 = myCell.toString();
                        }else if (colno==15) {
                                f16 = myCell.toString();
                        }else if (colno==16) {
                                f17 = myCell.toString();
                        }else if (colno==17) {
                                f18 = myCell.toString();
                        }

                        colno++;
                       //  Log.e(TAG, " Index :" + myCell.getColumnIndex() + " -- " + myCell.toString());
                    }

                    FiberOpCableDat1 obj= new FiberOpCableDat1(
                            f1,
                            f2,
                            f3,
                            f4,
                            f5,
                            f6,
                            f7,
                            f8,
                            f9,
                            f10,
                            f11,
                            f12,
                            f13,
                            f14,
                            f15,
                            f16,
                            f17,
                            f18);
                    Log.e(TAG, "標籤序號: " + obj.getf1().toString() + "\n" +
                                    "路由編號: " + obj.getf2().toString() + "\n" +
                                    "機房名稱: " + obj.getf3().toString() +"\n" +
                                    "機房樓層: " + obj.getf4().toString() +"\n" +
                                    "機櫃編號: " + obj.getf5().toString() +"\n" +
                                    obj.getf6().toString() +"\n" +
                                    obj.getf7().toString() +"\n" +
                                    obj.getf8().toString() +"\n" +
                                    obj.getf9().toString() +"\n" +
                                    obj.getf10().toString() +"\n" +
                                    obj.getf11().toString() +"\n" +
                                    obj.getf12().toString() +"\n" +
                                    obj.getf13().toString() +"\n" +
                                    obj.getf14().toString() +"\n" +
                                    obj.getf15().toString() +"\n" +
                                    obj.getf16().toString() +"\n" +
                                    obj.getf17().toString() +"\n" +
                                    obj.getf18().toString() +"\n" +
                                    "\n" );
                    textshow.append( f1 + "-"+
                                     f2 + "-"+
                                     f3 + "-"+
                            f4+ "-"+
                            f5 + "-"+
                            f3 + "-"+
                            f6 + "-"+
                            f7 + "-"+
                            f8 + "-"+
                            f9 + "-"+
                            f10 + "-"+
                            f11 + "-"+
                            f12 + "-"+
                            f13 + "-"+
                            f14 + "-"+
                            f15 + "-"+
                            f16 + "-"+
                            f17 + "-"+
                            f18 + "\n");
                }
                rowno++;
            }
        } catch (Exception e) {
            Log.e(TAG, "11ror "+ e.toString());
        }
    }

}

         */
    /*
        // The following is a custom toast (display period can be changed )

        private Toast mToastToShow; // the default custom toast object

        public void showToast(View view, int period ) {

            int toastDurationInMilliSeconds = period*1000;
            mToastToShow = Toast.makeText(this, "Hello world, I am a toast.", Toast.LENGTH_LONG);
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 ) {
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


            public void removeItem(int position){
                arrayList.remove(position);
               //  notifyItemRemoved(position);
            }

            public  void addItem(int position , String text) {
                // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("No", "光纜資料" + Integer.toString(position));
                hashMap.put("Sub1", String.valueOf(new Random().nextInt(80) + 20));
                hashMap.put("Sub2", String.valueOf(new Random().nextInt(80) + 20));

                hashMap.put("Sub3", String.valueOf(
                        (Integer.parseInt(hashMap.get("Sub1"))
                                + Integer.parseInt(hashMap.get("Sub2"))) / 2));

                arrayList.add(position,hashMap);
              //  notifyItemInserted(position);
            }


            class ViewHolder extends RecyclerView.ViewHolder{
                private TextView tvTitle,
                        tvSub1 ,
                        tvSub2 ,
                        tvSub3 ;

                TextView title ;
                private View mView;

                private Button btnremove ;
                private Button btnadd ;

                private ImageView imageOfRfid ;


                public ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    tvTitle = itemView.findViewById(R.id.textView_title); // Title of sequential number
                    Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(), "boldttf.otf");
                    //  tvTitle.setTypeface(type);

                    tvSub1 = itemView.findViewById(R.id.textView_sub1);
                    tvSub2 = itemView.findViewById(R.id.textView_sub2);
                    tvSub3 = itemView.findViewById(R.id.textView_sub3);

                    btnremove = itemView.findViewById(R.id.btnRemove) ; // to focus on the remove button
                    btnadd = itemView.findViewById(R.id.btnUpdate);

                    imageOfRfid = itemView.findViewById(R.id.imagerfid);


                    mView  = itemView;

                }
            }
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.itemrecyclerview2,parent,false);

                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                 int Realposition = holder.getBindingAdapterPosition();
                // int avgS = Integer.parseInt(arrayList.get(position).get("Sub3"));

                // holder.tvTitle.setBackgroundColor(getColor(R.color.purple));

                holder.tvTitle.setText(arrayList.get(Realposition).get("No")); // title no.
                // construct the color of title
                holder.tvTitle.setTextColor(Color.rgb(20,50,200));
                holder.tvSub1.setText(arrayList.get(Realposition).get("Sub1"));
                holder.tvSub2.setText(arrayList.get(Realposition).get("Sub2"));
                holder.tvSub3.setText(arrayList.get(Realposition).get("Sub3"));
                // holder.tvTitle.startAnimation(AnimationUtils.loadAnimation(holder.tvTitle.getContext(),));



                holder.btnremove.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           // 移除項目，getAdapterPosition為點擊的項目位置
                           // Toast.makeText(DataManipulation2.this, "刪除 "+Integer.toString(position), Toast.LENGTH_SHORT).show();
                            removeItem(position);
                            notifyItemRemoved(position);  // this line should be refresh screen

                       }
                   });

                   holder.btnadd.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Toast.makeText(DataManipulation2.this, "Add ", Toast.LENGTH_SHORT).show();
                           myListAdapter.addItem(position,"he");
                           if (new Random().nextInt(80) % 2 == 0 )
                           holder.imageOfRfid.setImageResource(R.drawable.rfid6);
                           else if (new Random().nextInt(100) %7 == 0 )
                               holder.imageOfRfid.setImageResource(R.drawable.rfid5);
                           else if (new Random().nextInt(45) %3 == 0 )
                               holder.imageOfRfid.setImageResource(R.drawable.rfid4);
                           else
                               holder.imageOfRfid.setImageResource(R.drawable.rfid10);

                       }

                   });

                   holder.mView.setOnLongClickListener(new View.OnLongClickListener() {

                       @Override

                       public boolean onLongClick(View view) {

                          //  removeItem(position);
                          //  notifyItemRemoved(position);
                           Toast.makeText(DataManipulation2.this, "Query .... ", Toast.LENGTH_SHORT).show();

                           return true;

                       }

                   });

                Toast.makeText(DataManipulation2.this, ""+holder.tvTitle.getText(), Toast.LENGTH_SHORT).show();

                // Toast.makeText(getBaseContext(), ""+No.getText(), Toast.LENGTH_SHORT).show();
                // The following codes show a toast when user click item in a view.


                holder.mView.setOnClickListener(
                        (v)->
                        {
                            // String No = (String) holder.tvTitle.getText();


                            // title.setText(No.toString());
                            ShowToastClickListener clickListener = new ShowToastClickListener();

                            final AlertDialog.Builder builder = new AlertDialog.Builder(DataManipulation2.this,R.style.CustomAlertDialog);


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

                            // alertDialog.setCanceledOnTouchOutside(false);

                             alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                 public void onCancel(DialogInterface dialog) {
                                     finish();
                                 }
                             });
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // custom toast
                                    // showToast(v,20);
                                    // alertDialog.dismiss();

                                    // alertDialog.cancel();
                                }
                            });

                            alertDialog.show(); // the dialog which shows information of cable

                        });

            } // end of onBindViewholder




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
                        adapter.notifyItemMoved(position_dragged,position_target);

                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getBindingAdapterPosition();

                        switch(direction){
                            case ItemTouchHelper.LEFT:                  //資料左滑
                            case ItemTouchHelper.RIGHT:
                                Toast.makeText(DataManipulation2.this, ""+"Swiped ...", Toast.LENGTH_SHORT).show();
                                choose.remove(position);               //移除資料
                                adapter.notifyItemRemoved(position);   //告知配適器資料有更換

                                if(position!=choose.size()){
                                    //開始改變的位置和總共被影響的位置數量，相當重要否則資料的position不會更改
                                    //刮號內是(改變的位置,受影響的數量)
                                    adapter.notifyItemRangeChanged(position,choose.size()-position);
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
    */
    /*
        public int readExcelFileFromAssets_xls (ArrayList < HashMap < String, String >> CableDat){

        FileOutputStream fos = null;
        int index = 1;
        int size = 0;

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
                size++;
            }
        } catch (Exception e) {
            Log.e(TAG, "error " + e.toString());
        }

        return size;  // return number of items
    }

    */
    /*


    public int readExcelFileFromAssets_xls() {

        FileOutputStream fos = null;
        int index = 1;
        int size = 0;

        TextView textshow;

        try {

            // FileInputStream fs = new FileInputStream("XLS50.xls");
            // Workbook book = new HSSFWorkbook(fs);


            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = getAssets();
            //  open excel sheet

            InputStream stream = (InputStream) getApplication().getResources().getAssets().open("rfidtry.xls");
            /* myInput = assetManager.open("rfidxlsx.xls");
            // Create a POI File System object
            // POIFSFileSystem myFileSystem = new POIFSFileSystem(/*myInput stream); */
            /* POIFSFileSystem
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

           // FileInputStream fs = new FileInputStream(pathFiles);
           // Workbook book = new HSSFWorkbook(fs);


            // HSSFWorkbook myWorkBook = WorkbookFactory.create(myFileSystem);
            // Get the first sheet from workbook


            HSSFSheet mySheet =myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.
            textshow = findViewById(R.id.textshow2);
            // textshow = new TextView(this);
            textshow.setTextSize(16);
            textshow.setTextColor(Color.WHITE);


            Iterator<Row> rowIter = mySheet.rowIterator();

            int rowno = 0; // setting the first row

            textshow.append("\n");

            //  Toast.makeText(this, ""+"show", Toast.LENGTH_SHORT).show();

            while (rowIter.hasNext()) {

                HSSFRow myRow = (HSSFRow) rowIter.next();


                if (rowno != 0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno = 0;

                    String fld1 = "",
                            fld2 = "",
                            fld3 = "",
                            fld4 = "",
                            fld5 = "",
                            fld6 = "",
                            fld7 = "",
                            fld8 = "",
                            fld9 = "",
                            fld10 = "",
                            fld11 = "",
                            fld12 = "",
                            fld13 = "",
                            fld14 = "",
                            fld15 = "",
                            fld16 = "",
                            fld17 = "",
                            fld18 = "";


                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();  // 由第1行依序找


                        if (colno == 0) {   //
                            fld1 = myCell.toString();
                            Log.i(TAG, "....: " + fld1.toString());
                        } else if (colno == 1) {
                            fld2 = myCell.toString();
                        } else if (colno == 2) {
                            fld3 = myCell.toString();
                        } else if (colno == 3) {
                            fld4 = myCell.toString();
                        } else if (colno == 4) {
                            fld5 = myCell.toString();
                        } else if (colno == 5) {
                            fld6 = myCell.toString();
                        } else if (colno == 6) {
                            fld7 = myCell.toString();
                        } else if (colno == 7) {
                            fld8 = myCell.toString();
                        } else if (colno == 8) {
                            fld9 = myCell.toString();
                        } else if (colno == 9) {
                            fld10 = myCell.toString();
                        } else if (colno == 10) {
                            fld11 = myCell.toString();
                        } else if (colno == 11) {
                            fld12 = myCell.toString();
                        } else if (colno == 12) {
                            fld13 = myCell.toString();
                        } else if (colno == 13) {
                            fld14 = myCell.toString();
                        } else if (colno == 14) {
                            fld15 = myCell.toString();
                        } else if (colno == 15) {
                            fld16 = myCell.toString();
                        } else if (colno == 16) {
                            fld17 = myCell.toString();
                        } else if (colno == 17) {
                            fld18 = myCell.toString();
                        }

                        FiberOpCableDat1 Cableobj = new FiberOpCableDat1(
                                fld1,
                                fld2,
                                fld3,
                                fld4,
                                fld5,
                                fld6,
                                fld7,
                                fld8,
                                fld9,
                                fld10,
                                fld11,
                                fld12,
                                fld13,
                                fld14,
                                fld15,
                                fld16,
                                fld17,
                                fld18);

                        arrayLST.add(Cableobj);
                        Log.i(TAG, "CC->" + fld1);
                         textshow.append(Integer.toString(rowno)+ "--" +
                                 arrayLST.get(rowno).getNo() + "--" +

                                   fld2 + "--" +
                                   fld3 + "--" +
                                   fld4 + "--" +
                                   fld5 + "--" +
                                   fld6 + "--" +
                                   fld7 + "--" +
                                   fld8 + "--" +
                                   fld9 + "--" +
                                   fld10 + "--" +
                                   fld11 + "--" +
                                   fld12 + "--" +
                                   fld13 + "--" +
                                   fld14 + "--" +
                                   fld15 + "--" +
                                   fld16 + "--" +
                                   fld17 + "--" +
                                   fld18 +  "\n"   );


                        colno++;
                    }
                }

                rowno++;
                size++;
            }
        } catch (Exception e) {
            Log.e(TAG, "error " + e.toString());
        }

        return --size;  // return number of items
    }

}
*/

    }
}

