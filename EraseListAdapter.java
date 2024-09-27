package com.tra.loginscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EraseListAdapter extends RecyclerView.Adapter<EraseListAdapter.ViewHolder> {

    private List<String> mData;

    EraseListAdapter(List<String> data) {
        mData = data;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView txtItem;
        private Button btnRemove;

        AlertDialog.Builder builder;

        ViewHolder(View itemView) {
            super(itemView);

            txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);

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
                    Toast.makeText(view.getContext(),
                            "button click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    Removerfidtagdialog removerfidtagdialog = new Removerfidtagdialog(view.getContext());
                    Button  confirmbtn = removerfidtagdialog.findViewById(R.id.btn_confirm);
                    confirmbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                            removeItem(getAdapterPosition());   // remove the item which you clicked
                            removerfidtagdialog.cancel();
                        }
                    });
                    removerfidtagdialog.show();
                    // removeItem(getAdapterPosition());   // remove the item which you clicked

                /*
                    builder = new AlertDialog.Builder(view.getContext());

                    builder.setMessage("是否確定要移除此光纜資料 ? ")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeItem(getAdapterPosition()); // delete current item that you clicked !
                                    dialog.cancel();
                                    showCustomDialog(view); // show a popup dialog
                                    Toast.makeText(view.getContext(), "此RFID已移除成功",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(view.getContext(), "此RFID保留!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("移除RFID標籤");
                    alert.show();
                    */
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
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.erasecabledialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //setting the view of the builder to our custom view that we already inflated

        builder.setView(dialogView);
        okbtn = dialogView.findViewById(R.id.buttonOk);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


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
                .inflate(R.layout.eraselist, parent, false);

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