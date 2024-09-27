package com.tra.loginscreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.tra.loginscreen.ContactViewHolder ;


public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Contacts> listContacts;
    private ArrayList<Contacts> mArrayList;
    private SqliteDatabase mDatabase;

    private Contacts contacts ;

    private String acc ;
    private String pwd ;


    public ContactAdapter(Context context, ArrayList<Contacts> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.mArrayList=listContacts;

        mDatabase = new SqliteDatabase(context);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        /* final Contacts */
        contacts = listContacts.get(position);

        holder.name.setText("帳號: " + contacts.getName());
        holder.ph_no.setText("密碼: "+contacts.getPhno());


        if (false) {
            holder.editContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // editTaskDialog(contacts);

                    ModifyAccountDialog modifyAccountDialog = new ModifyAccountDialog(view.getContext());
                    modifyAccountDialog.show();

                    // ((Activity)context).finish();
                    // context.startActivity(((Activity)context).getIntent());


                }
            });
        }

        if (false) {

            holder.deleteContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(view.getContext());

                    deleteAccountDialog.which(contacts);
                    deleteAccountDialog.show();

                    // Create the alert dialog and display it  -- they are dummy codes

                    if (false) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("確定刪除它嗎 ?")
                                .setMessage(contacts.getName())
                                .setCancelable(false)
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(view.getContext(), "選擇項目: 確定", Toast.LENGTH_SHORT).show();
                                        mDatabase.deleteContact(contacts.getId()); // delete the item that you clicked
                                        ((Activity) context).finish(); // close current activity

                                        context.startActivity(((Activity) context).getIntent());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(view.getContext(), "Selected Option: No", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                        //Creating dialog box
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    //  mDatabase.deleteContact(contacts.getId());
                    //  mDatabase.deleteContact(contacts.getId());
                    //  refresh the activity page.
                    //  ((Activity)context).finish();
                    //  context.startActivity(((Activity) context).getIntent());
                }
            });
        }

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listContacts = mArrayList;
                } else {

                    ArrayList<Contacts> filteredList = new ArrayList<>();

                    for (Contacts contacts : mArrayList) {

                        if (contacts.getName().toLowerCase().contains(charString)) {

                            filteredList.add(contacts);
                        }
                    }

                    listContacts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listContacts;

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listContacts = (ArrayList<Contacts>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listContacts.size();
    }


    private class DeleteAccountDialog extends Dialog {

        private Button cfmbtn, cnlbtn;

        private EditText editText ;

        public DeleteAccountDialog(@NonNull Context context) {
            super(context, R.style.dialog_style);

            this.setContentView(R.layout.deleteaccdialog1);

            initView();
            addListener(context);   // define all listeners of component

        }  // the definition of constructor

        private void which(Contacts contacts ) {

            mDatabase.deleteContact(contacts.getId()); // delete the item that you clicked
            ((Activity) context).finish(); // close current activity
            context.startActivity(((Activity) context).getIntent());
        }

        private void initView() {

            // this function which initializes those components in dialog

            cfmbtn = findViewById(R.id.confirmbtn);
            cnlbtn = findViewById(R.id.cancelbtn);
            // the following is edit fields
            // acc = findViewById(R.id.editacc);
            //  editname = findViewById(R.id.editname);
            // editport = findViewById(R.id.editportnum);

        }
        private void addListener(Context context)
        {
            //  the confirmation button click event listener

            cfmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //      Toast.makeText(context, "編號: "+editno.getText().toString(), Toast.LENGTH_SHORT).show();
                    //      Toast.makeText(context, "名稱: "+editname.getText().toString(), Toast.LENGTH_SHORT).show();
                    //      Toast.makeText(context, "Port:"+editport.getText().toString(), Toast.LENGTH_SHORT).show();
                    //      showQueryDialog(v);

                    //      RFIDTagSearchDialog rfidTagSearchDialog = new RFIDTagSearchDialog(v.getContext());
                    //      rfidTagSearchDialog.show();

                    // WindowManager.LayoutParams layoutParams = rfidTagSearchDialog.getWindow().getAttributes();
                    // layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                    // layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                    // rfidTagSearchDialog.getWindow().setAttributes(layoutParams);

                    //设置dialog进入的动画效果
                    // Window window = rfidTagSearchDialog.getWindow();
                    // window.setWindowAnimations(R.style.AnimLeft);
                     which(contacts);


                }
            });
            // cancel button click event listener
            cnlbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // close the dialog
                    dismiss();
                }
            });
        }
      private void showQueryDialog(View view) {

            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            Button confirmbtn ;
            Button cancelbtn ;
            ViewGroup viewGroup = findViewById(android.R.id.content);
            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.iperrordialog, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            //setting the view of the builder to our custom view that we already inflated

            builder.setView(dialogView);
            confirmbtn = dialogView.findViewById(R.id.confirmbtn);
            cancelbtn = dialogView.findViewById(R.id.cancelbtn) ;

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if (true) {   // dialog animation
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                alertDialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = alertDialog.getWindow();
                window.setWindowAnimations(R.style.AnimLeft);
            }

            confirmbtn.setOnClickListener(new View.OnClickListener() {
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

                    //  alertOneButton(v) ;


                    // finish(); //  close current activity

                }
            });

            //finally creating the alert dialog and displaying it
            // AlertDialog alertDialog = builder.create();
            //  alertDialog.show();
            //Now we need an AlertDialog.Builder object
        }
    }


    private void editTaskDialog(final Contacts contacts){

        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText contactField = (EditText)subView.findViewById(R.id.enter_phno);

        if(contacts != null){
            nameField.setText(contacts.getName());
            contactField.setText(String.valueOf(contacts.getPhno()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改帳號");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String name = nameField.getText().toString();
                final String ph_no = contactField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateContacts(new Contacts(contacts.getId(), name, ph_no));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    private class ModifyAccountDialog extends  Dialog {

        private Button cfmbtn, cnlbtn;

        private EditText editacc ,
                editpwd ;

        public ModifyAccountDialog (@NonNull Context context) {
            super(context, R.style.dialog_style);

            this.setContentView(R.layout.modifyaccountdialog);

            initView();
            addListener(context);   // define all listeners of component

        }  // the definition of constructor

        private void initView() {

            // this function which initializes those components in dialog

            cfmbtn = findViewById(R.id.confirmbtn);
            cnlbtn = findViewById(R.id.cancelbtn);

            // the following is edit fields - account and password
            editacc = findViewById(R.id.editacc);
            editpwd = findViewById(R.id.editpwd);

        }
        private void addListener(Context context)
        {

            //  the confirmation button click event listener

            cfmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "編號: "+ editacc.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "名稱: "+ editpwd.getText().toString(), Toast.LENGTH_SHORT).show();
                    //      Toast.makeText(context, "Port:"+editport.getText().toString(), Toast.LENGTH_SHORT).show();
                    // showQueryDialog(v);

                    //   RFIDTagSearchDialog rfidTagSearchDialog = new RFIDTagSearchDialog(v.getContext());
                    //   rfidTagSearchDialog.show();

                    // WindowManager.LayoutParams layoutParams = rfidTagSearchDialog.getWindow().getAttributes();
                    // layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                    // layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                    // rfidTagSearchDialog.getWindow().setAttributes(layoutParams);

                    //设置dialog进入的动画效果
                    // Window window = rfidTagSearchDialog.getWindow();
                    // window.setWindowAnimations(R.style.AnimLeft);

                    mDatabase.updateContacts(new Contacts(contacts.getId(),
                                                          editacc.getText().toString(),
                                                          editpwd.getText().toString()));
                    modificationConfirmation();

                }
            });
            // cancel button click event listener
            cnlbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // close the dialog
                    dismiss();
                }
            });
        }
        private void modificationConfirmation(){

            ((Activity)context).finish();
            context.startActivity(((Activity)context).getIntent());

        }
        private void showQueryDialog(View view) {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            Button confirmbtn ;
            Button cancelbtn ;
            ViewGroup viewGroup = findViewById(android.R.id.content);
            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.iperrordialog, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            //setting the view of the builder to our custom view that we already inflated

            builder.setView(dialogView);
            confirmbtn = dialogView.findViewById(R.id.confirmbtn);
            cancelbtn = dialogView.findViewById(R.id.cancelbtn) ;

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if (true) {   // dialog animation
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                alertDialog.getWindow().setAttributes(layoutParams);
                //设置dialog进入的动画效果
                Window window = alertDialog.getWindow();
                window.setWindowAnimations(R.style.AnimLeft);
            }

            confirmbtn.setOnClickListener(new View.OnClickListener() {
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

                    //  alertOneButton(v) ;


                    // finish(); //  close current activity

                }
            });

            //finally creating the alert dialog and displaying it
            // AlertDialog alertDialog = builder.create();
            //  alertDialog.show();
            //Now we need an AlertDialog.Builder object
        }
    }


}