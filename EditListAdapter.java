package com.tra.loginscreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// import com.tra.loginscreen.Cabledata;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



/*
public class EditListAdapter extends RecyclerView.Adapter<EditListAdapter.ViewHolder> {

    // private List<Cabledata> mData = new ArrayList<>();
    private Context context ;

     private String name,email;
    AlertDialog.Builder builder;
    AlertDialog dialog;




    EditListAdapter (List<Cabledata> data) {
        mData = data;
    }



    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView txtItemEdit;
        private Button btnEdit;

        AlertDialog.Builder builder;

        ViewHolder(View itemView) {
            super(itemView);

            txtItemEdit = (TextView) itemView.findViewById(R.id.txtItemedit);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);

            // 點擊項目時
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "item click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                }
            });

            // 點擊項目中的Button時
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 按下Button要做的事
                    Toast.makeText(view.getContext(),
                            "button click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();

                    builder = new AlertDialog.Builder(view.getContext());

                    builder.setMessage("是否確定要修改此光纜資料 ?")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   //  removeItem(getAdapterPosition()); // delete current item that you clicked !
                                  //   EditItem(getAbsoluteAdapterPosition(),view);
                                   //  dialog.cancel();
                                    // Cabledata c = new Cabledata();
                                    // c.setCableno("35537353753");
                                    int p = getAbsoluteAdapterPosition();
                                    InitUpdateDialog(p,view);


                                    Toast.makeText(view.getContext(), "position :" +Integer.toString(getAbsoluteAdapterPosition()),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(view.getContext(), "此光纜資料未修改!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                     alert.setTitle("修改光纜資料");
                    alert.show();
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item

        context = parent.getContext() ;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editlist
                        , parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        // holder.txtItemEdit.setText(mData.get(position).cableno);
    }

    @Override
    public int getItemCount() {
        // return mData.size();
    }



    public void EditItem(int position, View view){
        // dialog();
       //  Toast.makeText(context, "xx --> " + xx.toString(), Toast.LENGTH_SHORT).show();




        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("修改資料");
        builder.setCancelable(false);
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_update,null,false);
        InitUpdateDialog(position,v);
        builder.setView(v);
        dialog = builder.create();
        dialog.show();


        //mData.set(position, "jsjsjjs");
        notifyItemRangeChanged(position,getItemCount()); // the second parameter is total number of items
        // notifyItemRemoved(position);
    }

    private   void InitUpdateDialog(final int position, View view) {

        EditText et_update_name , et_update_email ;
        Button btn_update, btn_cancel ;

        et_update_name = view.findViewById(R.id.et_update_f1);
        et_update_email = view.findViewById(R.id.et_update_f2);
        btn_update = view.findViewById(R.id.btnEdit);
        // btn_cancel = view.findViewById(R.id.btn_update_cancel);



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "helloooooooo", Toast.LENGTH_SHORT).show();
                // name = et_update_name.getText().toString();
                // email = et_update_email.getText().toString();

                // Cabledata cabledata = new Cabledata();


                // cabledata.setCableno("11222");
                // cabledata.setRouteno("12334");
                // UpdateData(position,cabledata);
                Toast.makeText(view.getContext(), "name:" + "1234", Toast.LENGTH_SHORT).show();
                // mData.get(position).setCableno("uusuduuc");
                notifyItemChanged(position);
                notifyDataSetChanged();


                dialog.dismiss();


                //mData.remove(position);
                // mData.add(cabledata);
               // notifyItemChanged(position);
               //  notifyDataSetChanged();

                // UpdateData(position,cabledata);
                // mData.UpdateData(position,cabledata);
                Toast.makeText(view.getContext(), "User Updated..",Toast.LENGTH_SHORT).show();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void UpdateData(int position,Cabledata cabledata){
        Toast.makeText(context, "position >>" + position, Toast.LENGTH_SHORT).show();
        mData.remove(position);
        mData.add(cabledata);
        notifyItemChanged(position,cabledata);
        notifyDataSetChanged();
    }



    private void  dialog() {

        final Dialog dialog = new Dialog(context.getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.updatecable);

        final EditText et = dialog.findViewById(R.id.et);
        final EditText et2 = dialog.findViewById(R.id.et2);
        final EditText et3 = dialog.findViewById(R.id.et3);

        Button btnok = (Button) dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.dismiss();
            }
        });

        Button btncn = (Button) dialog.findViewById(R.id.btncn);
        btncn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


      }   // end of dialog


    }
*/






