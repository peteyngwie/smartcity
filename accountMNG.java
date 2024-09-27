package com.tra.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar ;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class accountMNG extends AppCompatActivity {
    private static final String TAG = accountMNG.class.getSimpleName();

    private SqliteDatabase mDatabase;
    private ArrayList<Contacts> allContacts=new ArrayList<>();
    private ContactAdapter mAdapter;

    Toolbar toolbar ;

    private String accountnew ;
    private String pwdnew ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_mng);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView contactView = (RecyclerView)findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(this);
        allContacts = mDatabase.listContacts();


        if(allContacts.size() > 0){
            contactView.setVisibility(View.VISIBLE);
            mAdapter = new ContactAdapter(this, allContacts);
            contactView.setAdapter(mAdapter);

        }else {
            contactView.setVisibility(View.GONE); //
            Toast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        if (false)  // this is a useless function
        {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AddaccountDialog addaccountDialog = new AddaccountDialog(view.getContext());
                    addaccountDialog.show();

                    if (true) {   // dialog animation
                        WindowManager.LayoutParams layoutParams = addaccountDialog.getWindow().getAttributes();
                        layoutParams.width = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT);
                        addaccountDialog.getWindow().setAttributes(layoutParams);
                        //设置dialog进入的动画效果
                        Window window = addaccountDialog.getWindow();
                        window.setWindowAnimations(R.style.AnimLeft);
                    }

                    // CreateACCDialog();

                }
            }); // floating action button
        }


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);  // initialize toolbar

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();   // when you click the back arrow < , current activity go back home activity
            }
        });

    }

    private class AddaccountDialog extends Dialog {

        private Button cfmbtn, cnlbtn;

        private EditText editacc, editpwd  ;
        //  the followicng codes which are repso
        private SqliteDatabase mDatabase;
        private ArrayList<Contacts> allContacts=new ArrayList<>();
        private ContactAdapter mAdapter;

        public AddaccountDialog(@NonNull Context context) {
            super(context, R.style.dialog_style);

            this.setContentView(R.layout.addaccdialog);

            initView();
            addListener(context);   // define all listeners of component

        }  // the definition of constructor

        private void initView() {

            // this function which initializes those components in dialog

            cfmbtn = findViewById(R.id.confirmbtn);
            cnlbtn = findViewById(R.id.cancelbtn);
            // the following is edit fields
            editacc = findViewById(R.id.editacc);
            editpwd = findViewById(R.id.editpwd);

        }
        private void addListener(Context context)
        {

            //  the confirmation button click event listener

            cfmbtn.setOnClickListener(new View.OnClickListener() {

                // final String account = editacc.getText().toString();
                // final String pwd = editpwd.getText().toString();

                @Override
                public void onClick(View v) {

                    final String account = editacc.getText().toString();
                    final String pwd = editpwd.getText().toString();

                    Toast.makeText(context, "編號: "+account.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "名稱: "+pwd.toString(), Toast.LENGTH_SHORT).show();

                    // showQueryDialog(v);

                    // RFIDTagSearchDialog rfidTagSearchDialog = new RFIDTagSearchDialog(v.getContext());
                    // rfidTagSearchDialog.show();

                    // WindowManager.LayoutParams layoutParams = rfidTagSearchDialog.getWindow().getAttributes();
                    // layoutParams.width =  (ViewGroup.LayoutParams.WRAP_CONTENT) ;
                    // layoutParams.height = (ViewGroup.LayoutParams.WRAP_CONTENT) ;

                    // rfidTagSearchDialog.getWindow().setAttributes(layoutParams);

                    //设置dialog进入的动画效果
                    // Window window = rfidTagSearchDialog.getWindow();
                    // window.setWindowAnimations(R.style.AnimLeft);

                    // Contacts newContact = new Contacts(account, pwd);
                    // mDatabase.addContacts(newContact);

                    // finish();

                   // Bundle bundle = new Bundle();  // New a bundle for delivery
                   //  Intent intent = new Intent();

                   //  bundle.putString("acc",editacc.getText().toString());
                   //  bundle.putString("pwd",editpwd.getText().toString());


                    //把bundle綁入Intent
                    // intent.putExtras(bundle);

                    // Context context = v.getContext();
                    // Intent intent = new Intent(context, accountMNG.class);

                    // intent.setClass(context,accountMNG.class);
                    //啟動intent，執行畫面跳轉
                    // v.getContext().startActivity(intent);
                    CreateACCDialog(account,pwd);

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
            // confirmbtn = dialogView.findViewById(R.id.confirmbtn);
            // cancelbtn = dialogView.findViewById(R.id.cancelbtn) ;

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
         /*
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "ok button", Toast.LENGTH_SHORT).show();

                alertDialog.cancel(); // confirmation button

                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rfidtagsearch, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                //setting the view of the builder to our custom view that we already inflated
                Button btnsearch;
                builder.setView(dialogView);
                btnsearch = dialogView.findViewById(R.id.buttonsearch);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                AlertDialog.Builder builder = new AlertDialog.Builder(querycable.this);
                builder.setTitle("RFID標籤搜尋");
                builder.setMessage("編號: 0049");
                // add a button
                // builder.setPositiveButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                //Intent intent = new Intent(querycable.this, querycable.class);
                // startActivity(intent);

                //  alertOneButton(v) ;


                // finish(); //  close current activity

            }
        });
         */
            //finally creating the alert dialog and displaying it
            // AlertDialog alertDialog = builder.create();
            //  alertDialog.show();
            //Now we need an AlertDialog.Builder object
        }
    }


    private void CreateACCDialog(String acc , String pwd )
    {


        /*
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText noField = (EditText)subView.findViewById(R.id.enter_phno);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增帳號");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("新增", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String account = nameField.getText().toString();
                final String pwd = noField.getText().toString();

                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)){
                    Toast.makeText(accountMNG.this, "未輸入帳號及密碼 !", Toast.LENGTH_LONG).show();
                }

                else{
                    Contacts newContact = new Contacts(account, pwd);
                    mDatabase.addContacts(newContact);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(accountMNG.this, "取消新增", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
        */

       // final String account ;
       //  final String pwd ;


        // Bundle bundle = getIntent().getExtras();
        //拆出bundle的內容，key為content
        // account = bundle.getString("acc");
        // pwd = bundle.getString("pwd");

        //Toast.makeText(this, ">>>>>>" + account.toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "++++++" + pwd.toString(), Toast.LENGTH_SHORT).show();

        Contacts newContact = new Contacts(acc, pwd);
        mDatabase.addContacts(newContact);

        finish();
        startActivity(getIntent());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // super.onCreateOptionsMenu(menu);


        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search); // this is a search bar

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);


        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter!=null)
                    mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}