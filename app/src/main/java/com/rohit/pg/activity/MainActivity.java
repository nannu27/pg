package com.rohit.pg.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.rohit.pg.R;
import com.rohit.pg.sql.DataBaseHelper;

public class MainActivity extends AppCompatActivity{

    Button login;
    TextView registratiin;
    EditText text_user, text_pass;
    DataBaseHelper db;
    CheckBox keeplogin;
    SharedPreferences sharedpreferences;
    String user1;
    String pass1;
    String spname1="user";
    String sppass1="pass";
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        registratiin = findViewById(R.id.registration);
        db = new DataBaseHelper(this);
        text_user = findViewById(R.id.username);
        text_pass = findViewById(R.id.password);
        keeplogin = findViewById(R.id.keeplogin);
     //   user1=text_user.getText().toString();
       // pass1=text_pass.getText().toString();
        keeplogin.setChecked(true);

        sharedpreferences = getSharedPreferences( mypreference, Context.MODE_PRIVATE);
      //  final SharedPreferences.Editor editor = sharedpreferences.edit();
        if (sharedpreferences.contains(spname1)) {
            Toast.makeText(getApplicationContext(),"if con is working",Toast.LENGTH_LONG).show();

            text_user.setText(sharedpreferences.getString(spname1, "no user data is taken"));
             text_pass.setText(sharedpreferences.getString(sppass1, "no pswd is taken"));
        }

        registratiin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registration.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keeplogin.isChecked())

                {
                    Toast.makeText(getApplicationContext(),"done sp   k"+sharedpreferences.getString(spname1, "khali")+user1,Toast.LENGTH_LONG).show();
                    user1=text_user.getText().toString();
                    pass1=text_pass.getText().toString();
                    sharedpreferences = getSharedPreferences( mypreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(spname1, user1);
                    editor.putString(sppass1, pass1);
                    editor.commit();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"done sp   k"+user1+"nhi hoya",Toast.LENGTH_LONG).show();

                    sharedpreferences = getSharedPreferences( mypreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                }
                String username = text_user.getText().toString().toLowerCase();
                String pass = text_pass.getText().toString();

                if(username.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter the Valid Input", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean chk = db.emailpass(username,pass);
                    if(chk == true)
                    {
                        Toast.makeText(getApplicationContext(),"Login Successfuly",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,renti_list_view.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No such Details are found please register your self",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }


    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("exit!!!!!")
                .setCancelable(true)
                .setTitle("Do you really want to close the application ?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        System.exit(0);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface,int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert11 = builder.create();
        alert11.show();
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
    }
}
