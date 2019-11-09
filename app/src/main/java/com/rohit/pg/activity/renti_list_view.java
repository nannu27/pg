package com.rohit.pg.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rohit.pg.R;
import com.rohit.pg.model.renti_model;
import com.rohit.pg.sql.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;
public class renti_list_view extends AppCompatActivity {
    List<renti_model> list;
    ListView listView;
    ArrayList<String> theList;
    renti_model fname;
   // String last_name,gender,father_name,mobile,p_mobile,occupation,permanent_add,working_add,pg_num,room_num,bed_num;
   // byte[] id_image,profile_image;
    FloatingActionButton floatingActionButton;
    DataBaseHelper dataBaseHelper;
    ImageView delete,reload,back;
    Spinner option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renti_list_view);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back);
        reload = findViewById(R.id.reload);
        listView = findViewById(R.id.list);
        option = findViewById(R.id.option);

        dataBaseHelper = new DataBaseHelper(this);
        floatingActionButton = findViewById(R.id.fab_Note);
        theList = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(renti_list_view.this,MainActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_all();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retive();
                Toast.makeText(getApplicationContext(),"Data List Refreshed..:)",Toast.LENGTH_LONG).show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),rentee_registration.class);
                startActivity(i);
            }
        });
        retive();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                renti_model Renti_model = list.get(i);
                final String fname = Renti_model.getFirst_name();
                final String lname = Renti_model.getLast_name();
                final String phone = Renti_model.getMobile();
                final String w_phone = Renti_model.getWhatsapp();
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                renti_model Renti_model = list.get(i);
                Intent intent = new Intent(getApplicationContext(),show_details.class);
                intent.putExtra("renti",Renti_model);
                startActivity(intent);
            }
        });
    }
    public void retive()
    {
        list = dataBaseHelper.getDetails();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }
    public void delete_all()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Note:- Data will be permanently delete all DATA...!")
                .setCancelable(true)
                .setTitle("Do you really want to Delete ?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean delete = dataBaseHelper.delete_all();
                        if(delete == true)
                        {
                            Toast.makeText(getApplicationContext(),"Record Deleted Successfully",Toast.LENGTH_LONG).show();
                            retive();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Problem in deleting Record",Toast.LENGTH_LONG).show();
                        }
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
    }


    @Override
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
