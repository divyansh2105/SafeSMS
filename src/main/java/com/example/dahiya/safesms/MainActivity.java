package com.example.dahiya.safesms;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ListView lv_sms;
    ArrayList<SmsModel> smsModelArrayList;
    SmsAdapter smsAdapter;
    ProgressDialog progressDialog;
    private int REQUEST_CODE = 1;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    EditText input,input_mobile,input_message,old,newp,edittext_mobile;
    Button button,button2,button3;
    TextView tv;
    FloatingActionButton floatingActionButton;
    byte[] encrypted;
    public static Global global;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String restoredText,restoredMobile,MOBILE;

    int count=0;


//    private static MainActivity inst;
//    ArrayList<String> smsMessagesList = new ArrayList<String>();
//    ListView smsListView;
//    ArrayAdapter arrayAdapter;
//    private SwipeRefreshLayout mSwipeRefreshLayout;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading sms");

        smsModelArrayList = new ArrayList<>();
        lv_sms = (ListView) findViewById(R.id.lv_sms);
        smsAdapter = new SmsAdapter(MainActivity.this,smsModelArrayList);
        lv_sms.setAdapter(smsAdapter);



        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){

// permission is already granted
// here you can directly access contacts

            readSms();
        }else{

//persmission is not granted yet
//Asking for permission
            Toast.makeText(this,"Has not got permission",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CODE);

        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        input = new EditText(MainActivity.this);
//        tv=new TextView(MainActivity.this);

//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        input.setLayoutParams(lp);


        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout_sendsms, null);
                input_mobile = (EditText) mView.findViewById(R.id.input_mobile);
                input_message = (EditText) mView.findViewById(R.id.input_message);
                button2 = (Button) mView.findViewById(R.id.button2);
                input_message.setHint("Enter message");
                input_mobile.setHint("Enter phone no.");


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String str_mobile=input_mobile.getText().toString();
                        str_mobile="+91"+str_mobile;

                        String str_message=input_message.getText().toString();
//                        str_message="Sent from SafeSMS\n"+str_message;
//                        SmsManager smsManager=SmsManager.getDefault();
//                        smsManager.sendTextMessage(str_mobile,null,str_message,null,null);
//                        Toast.makeText(getApplicationContext(), "SMS Sent!",
//                                Toast.LENGTH_LONG).show();
                        Databasehandler databasehandler = new Databasehandler(MainActivity.this, null, null, 1);
                        Cursor data = databasehandler.getcursor();
                        String n = null;
                        String e = null;
                        while (data.moveToNext()) {
                            if (str_mobile.equals(data.getString(0))) {
                                n = data.getString(1);

                                e = data.getString(2);
                            }
//            Log.d("check",data.getString(0)+" "+ data.getString(1)+ "  "+
//            data.getString(2));

                        }

                        RSA rsa = new RSA();
                        Log.d("str",str_message);
//                        byte[] x=str_message.getBytes("ISO-8859-1");
//                        String s_en=new String(x,"ISO-8859-1");
                        try {
                            encrypted = rsa.encrypt(str_message.getBytes("ISO-8859-1"), n, e);
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }

                        //Log.d("str",s_en);
                        String str2 = null;
                        try {
                            str2 = new String(encrypted,"ISO-8859-1");
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        str2 = "Sent from SafeSMS\n" + str2;
                        Log.d("str2",str2);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(str_mobile, null, str2, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();


                        input_message.setHint("Enter message");
                        input_mobile.setHint("Enter phone no.");
                        input_mobile.setText("");
                        input_message.setText("");

                    }
                });
            }
        });


        lv_sms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Enter private key");
//                builder.setMessage("in order to view the entire message");
//                builder.setView(input);
//                builder.setNeutralButton("Submit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String input_s=input.getText().toString();
//                        if(input_s=="12345")
//                        {
//                            Log.d("alert","Success");
//                        }
//                        else
//                        {
//
//                            input.setHint("Wrong key. Try Again");
//                            Log.d("alert","Failure");
//                        }
//                    }
//                });
//                builder.show();


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                input = (EditText) mView.findViewById(R.id.input);
                button = (Button) mView.findViewById(R.id.button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input_s=input.getText().toString();

                        String password=global.password;
                        if(input_s.equals(restoredText))
                        {
                            Log.d("alert","Success");
                            dialog.dismiss();
                            Intent i;
                            i = new Intent(MainActivity.this, Fullscreen_sms.class);
//
                            SmsModel s=smsModelArrayList.get(position);
                            i.putExtra("phone",s.mobile);
                            i.putExtra("message",s.message);
                            startActivity(i);
                        }
                        else
                        {
                            if(count>3)
                            {
                                Toast.makeText(MainActivity.this, restoredText+" "+restoredMobile, Toast.LENGTH_SHORT).show();
                                count=0;
                            }
                            input.setText("");
                            input.setHint("Wrong key.");
                            Log.d("alert","Failure"+input_s);
                            count++;
                        }
                    }
                });
            }


        });


        Databasehandler databasehandler=new Databasehandler(this,null,null,1);
        databasehandler.deleteVideo("+917678203335");
        databasehandler.deleteVideo("+919871806344");
        databasehandler.deleteVideo("+919810328826");
        databasehandler.deleteVideo("+919818152732");
        databasehandler.deleteVideo("+918383846605");
        databasehandler.deleteVideo("+919650020104");
        Structure s1=new Structure("+919871806344","656032702381310751187474580355957263756862785156675116631051"
        ,"838100859057899");
        databasehandler.addVideo(s1);
        Structure s2=new Structure("+919810328826","662857366177013138523111914534849389595973030405462015007109"
                ,"629839630292027");
        databasehandler.addVideo(s2);
        Structure s3=new Structure("+919818152732","722186408176522579811275904092327903084921248588368506168283"
                ,"1085732499098393");
        databasehandler.addVideo(s3);
        Structure s4=new Structure("+918383846605","1206278931472012555204498507982463895647870404220646469569547"
                ,"663625953610511");
        databasehandler.addVideo(s4);
        Structure s5=new Structure("+919650020104","771400678145287099335514429787278685953390051111970896425277"
                ,"772257362517713");
        databasehandler.addVideo(s5);
        Structure s6=new Structure("+917678203335","721709290802152315749388625348210359324723036638463894379977"
                ,"738990982134857");
//        Structure s1=new Structure("+919871806344","596349936892598708669461"
//                ,"1043857");
//        databasehandler.addVideo(s1);
//        Structure s2=new Structure("+919810328826","518744906789177534820131"
//                ,"792283");
//        databasehandler.addVideo(s2);
//        Structure s3=new Structure("+919818152732","1068532706772972232677359"
//                ,"526733");
//        databasehandler.addVideo(s3);
//        Structure s4=new Structure("+918383846605","694642895152291020318967"
//                ,"761183");
//        databasehandler.addVideo(s4);
//        Structure s5=new Structure("+919650020104","695529243166920240186061"
//                ,"960863");
//        databasehandler.addVideo(s5);
//        Structure s6=new Structure("+917678203335","616543358591018570164813"
//                ,"551651");
        databasehandler.addVideo(s6);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String my_mobile = telephonyManager.getLine1Number();
        //Log.d("my_no",my_mobile);
        my_mobile="+"+my_mobile;
        global=new Global(my_mobile);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        restoredText = prefs.getString("pass", "12345");
        restoredMobile=prefs.getString("mob","");

        if(restoredMobile.equals(""))
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_layout_mobile, null);
            edittext_mobile = (EditText) mView.findViewById(R.id.edittext_mobile);
            Button button4 = (Button) mView.findViewById(R.id.button4);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MOBILE=edittext_mobile.getText().toString();

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("mob", MOBILE);
                    restoredMobile=MOBILE;
                    editor.apply();

                    dialog.dismiss();

                }
            });
        }

//        if (restoredText == null) {
//            String name = prefs.getString("pass", "12345");//"No name defined" is the default value.
//
//        }
//        else
//        {
//            restoredText="12345";
//        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();

                readSms();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    public void readSms(){

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null ,null,null);
        startManagingCursor(c);

        progressDialog.show();
        // Read the sms data
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {

                String mobile = c.getString(c.getColumnIndexOrThrow("address")).toString();
                String message = c.getString(c.getColumnIndexOrThrow("body")).toString();

                //adding item to array list
                smsModelArrayList.add(new SmsModel(mobile, message));
                c.moveToNext();
            }

        }
        //c.close();

        progressDialog.dismiss();
        // notifying listview adapter

        smsAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_layoutsettings, null);
            old = (EditText) mView.findViewById(R.id.old);
            newp = (EditText) mView.findViewById(R.id.newp);
            button3 = (Button) mView.findViewById(R.id.button3);
            old.setHint("Enter old password");
            newp.setHint("Enter new password");

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
//            Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String password=global.password;
                    Log.d("my_no",password);
                    if(old.getText().toString().equals(restoredText))
                    {
                        Log.d("alert","Success");
                        Toast.makeText(MainActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
//                        global.password=newp.getText().toString();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("pass", newp.getText().toString());
                        restoredText=newp.getText().toString();
                        editor.apply();
                        dialog.dismiss();

                    }
                    else
                    {
                        old.setText("");
                        newp.setText("");
                        old.setHint("Wrong password");

                        Log.d("alert","Failure");
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void refreshSmsInbox() {
//        ContentResolver contentResolver = getContentResolver();
//        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
//        int indexBody = smsInboxCursor.getColumnIndex("body");
//        int indexAddress = smsInboxCursor.getColumnIndex("address");
//        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
//        arrayAdapter.clear();
//        do {
//            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
//                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
//            arrayAdapter.add(str);
//        } while (smsInboxCursor.moveToNext());
//    }
//
//    public void updateList(final String smsMessage) {
//        arrayAdapter.insert(smsMessage, 0);
//        arrayAdapter.notifyDataSetChanged();
//    }
//
//    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//        try {
//            String[] smsMessages = smsMessagesList.get(pos).split("\n");
//            String address = smsMessages[0];
//            String smsMessage = "";
//            for (int i = 1; i < smsMessages.length; ++i) {
//                smsMessage += smsMessages[i];
//            }
//
//            String smsMessageStr = address + "\n";
//            smsMessageStr += smsMessage;
//            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void onClick(View v) {
//        Intent i=new Intent(getActivity(),Add_remove.class);
//        startActivity(i);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Enter password");
        builder.setMessage("in order to view the entire message");
        builder.show();
    }


//    public void fab_clicked(View view) {
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
//        View mView = getLayoutInflater().inflate(R.layout.dialog_layout_sendsms, null);
//        input_mobile = (EditText) mView.findViewById(R.id.input_mobile);
//        input_message = (EditText) mView.findViewById(R.id.input_message);
//        button = (Button) mView.findViewById(R.id.button);
//
//        mBuilder.setView(mView);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                input_mobile=findViewById(R.id.send);
//                String str_mobile=input_mobile.getText().toString();
//                input_mobile=findViewById(R.id.send);
//                String str_message=input_message.getText().toString();
//                str_message="Sent from SafeSMS"+str_message;
//                SmsManager smsManager=SmsManager.getDefault();
//                smsManager.sendTextMessage(str_mobile,null,str_mobile,null,null);
//                Toast.makeText(getApplicationContext(), "SMS Sent!",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }




}
