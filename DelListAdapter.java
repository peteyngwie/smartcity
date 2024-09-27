package com.tra.loginscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DelListAdapter extends RecyclerView.Adapter<DelListAdapter.ViewHolder> {

    private List<String> mData;

    DelListAdapter(List<String> data) {
        mData = data;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView txtItem;
        private ImageButton btnRemove;

        AlertDialog.Builder builder;

        ViewHolder(View itemView) {
            super(itemView);

            txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            txtItem.setGravity(Gravity.CENTER);
            btnRemove = (ImageButton) itemView.findViewById(R.id.btnRemove);

            // 點擊項目時
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "item click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                }
            });

            // 點擊項目中的Button時

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    // 按下Button要做的事

                    CableDeletionDialog cableDeletionDialog = new CableDeletionDialog(view.getContext());
                    Button yesBtn = cableDeletionDialog.findViewById(R.id.btn_confirm);  // yes ! delete this item
                    Button nobtn = cableDeletionDialog.findViewById(R.id.btn_cancel);    // no ! reserve it
                    cableDeletionDialog.show();

                    if (true) {   // dialog animation
                        WindowManager.LayoutParams layoutParams = cableDeletionDialog.getWindow().getAttributes();
                        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        cableDeletionDialog.getWindow().setAttributes(layoutParams);
                        //设置dialog进入的动画效果
                        Window window = cableDeletionDialog.getWindow();
                        window.setWindowAnimations(R.style.AnimLeft);
                    }


                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeItem(getAdapterPosition()); // delete current item that you clicked !
                            showCustomDialog(view); // show a popup dialog
                             cableDeletionDialog.dismiss();
                        }
                    });

                    nobtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cableDeletionDialog.dismiss();
                        }
                    });

                    // removeItem(getAdapterPosition()); // delete current item that you clicked !
                    // Toast.makeText(view.getContext(),
                    //      "button click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                     /*
                    builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("是否確定要刪除此光纜資料 ? ")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeItem(getAdapterPosition()); // delete current item that you clicked !
                                    dialog.cancel();

                                    showCustomDialog(view); // show a popup dialog
                                    Toast.makeText(view.getContext(), "此光纜資料已刪除",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(view.getContext(), "此光纜資料未刪除!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Creating dialog box
                    AlertDialog alert = builder.create();
                    // Setting the title manually
                    alert.setTitle("刪除光纜資料");
                    alert.show();   */

                    // showCustomDialog(view); // show a popup dialog


                }
            });

        }
    }
    private void showCustomDialog(View view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup

        Button okbtn ;
        Button btnsearch ;
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.delcabledialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated

        builder.setView(dialogView);
        okbtn = dialogView.findViewById(R.id.buttonOk);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (true) {   // dialog animation
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setAttributes(layoutParams);
            //设置dialog进入的动画效果
            Window window = alertDialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);
        }


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "ok button", Toast.LENGTH_SHORT).show();

                alertDialog.cancel(); // confirmation button
    /*
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rfidtagsearch, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                //setting the view of the builder to our custom view that we already inflated
                Button btnsearch;
                builder.setView(dialogView);
                btnsearch = dialogView.findViewById(R.id.buttonsearch);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
   */
              /*
                AlertDialog.Builder builder = new AlertDialog.Builder(querycable.this);
                builder.setTitle("RFID標籤搜尋");
                builder.setMessage("編號: 0049");
                // add a button
                // builder.setPositiveButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show(); */
                //Intent intent = new Intent(querycable.this, querycable.class);
                // startActivity(intent);
                // alertOneButton(v) ;
                // finish(); //  close current activity

            }
        });

        //finally creating the alert dialog and displaying it
        // AlertDialog alertDialog = builder.create();
        //  alertDialog.show();
        //Now we need an AlertDialog.Builder object

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dellist, parent, false); // deletion list
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        holder.txtItem.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position){
        mData.remove(position);
        //mData.set(position,"hello");
        notifyItemRemoved(position);
        // notifyItemChanged(position);
    }
}