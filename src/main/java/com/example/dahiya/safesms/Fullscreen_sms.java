package com.example.dahiya.safesms;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.dahiya.safesms.MainActivity.MY_PREFS_NAME;

public class Fullscreen_sms extends AppCompatActivity {

    EditText editText;
    String mes, mobile;
    TextView tv, tv2;
    Button button;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_READ_STATE = 1;
    byte[] encrypted;
    public static Global global;

    private Context context;
    private SubscriptionManager mSubscriptionManager;
    String my_mobile="";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.fullscreen_sms);


        Intent intent = getIntent();
        String f_s=null;
        mes = intent.getStringExtra("message");
        mobile = intent.getStringExtra("phone");
        Log.d("check1",mes);
        String mes2 = "Sent from SafeSMS\n";
        int flag = 0;
        for(int i=0;i<mes.length();i++)
        {
            Log.d("tag1","at"+i+" "+mes.charAt(i)+" ");
        }
        if(mes.length()<mes2.length())
        {
            flag=1;
            f_s=mes;
        }
        else {
            for (int i = 0; i < mes2.length(); i++) {
                if (mes.charAt(i) != mes2.charAt(i)) {
                    flag = 1;
                    f_s = mes;

                }
            }
        }
        mes2=mes;
        mes="";
        for(int i=18;i<mes2.length();i++)
        {
            mes+=mes2.charAt(i);
        }
        if (flag == 0) {
            try {
                f_s="Sent from SafeSMS\n";
                Log.d("check4",mes);
                mes=decrypt(mes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("check2",mes);
        }
        mes2 = "";
        int count = 0;
        String mes3 = "";
        for (int i = 0; i < mes.length(); i++) {
            if (count > 7) {
                mes2 += "\n";
                mes3 = "";
                count = 0;
                //break;
            }
            if (mes3.length() > 35) {
                count = 7;
            }
            if (mes.charAt(i) == ' ') {
                count++;
            }
            mes2 = mes2 + mes.charAt(i);
            mes3 += mes.charAt(i);
        }
        tv = findViewById(R.id.tv_sms);
        Log.d("final",f_s+mes2);
        tv.setText(f_s+mes2);
        tv2 = findViewById(R.id.tv_mobile);
        tv2.setText(mobile);


    }





    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void send_clicked(View view) throws UnsupportedEncodingException {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//
//            if (checkSelfPermission(Manifest.permission.SEND_SMS)
//                    == PackageManager.PERMISSION_DENIED) {
//
//                Log.d("permission", "permission denied to SEND_SMS - requesting it");
//                String[] permissions = {Manifest.permission.SEND_SMS};
//
//                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
//
//            }
//            else
//            {
//                Log.d("permission", "Has permission");
//            }
//        }

//        if (ContextCompat.checkSelfPermission(Fullscreen_sms.this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // We do not have this permission. Let's ask the user
//            Log.d("permission", "permission denied to SEND_SMS - requesting it");
//            ActivityCompat.requestPermissions(Fullscreen_sms.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
//        }
//        else
//            {
//                Log.d("permission", "Has permission");
//            }
        editText = findViewById(R.id.send);
        String str = editText.getText().toString();
        Databasehandler databasehandler = new Databasehandler(this, null, null, 1);
        Cursor data = databasehandler.getcursor();
        String n = null;
        String e = null;
        while (data.moveToNext()) {
            if (mobile.equals(data.getString(0))) {
                n = data.getString(1);

                e = data.getString(2);
            }
//            Log.d("check",data.getString(0)+" "+ data.getString(1)+ "  "+
//            data.getString(2));

        }
        RSA rsa = new RSA();
        Log.d("str",str);
        byte[] x=str.getBytes("ISO-8859-1");
        String s_en=new String(x,"ISO-8859-1");
        encrypted = rsa.encrypt(str.getBytes("ISO-8859-1"), n, e);

        Log.d("str",s_en);
        String str2 = new String(encrypted,"ISO-8859-1");
        str2 = "Sent from SafeSMS\n" + str2;
        Log.d("str2",str2);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(mobile, null, str2, null, null);
        Toast.makeText(getApplicationContext(), "SMS Sent!",
                Toast.LENGTH_LONG).show();
    }

    String decrypt(String mes) throws UnsupportedEncodingException {
        Log.d("mes1",mes);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


        my_mobile=prefs.getString("mob","");

        my_mobile="+91"+my_mobile;
        Log.d("my_no",my_mobile);
        global=new Global(my_mobile);
        Log.d("check1",global.N.toString() + " " + global.D.toString());
        RSA rsa=new RSA();
        Log.d("mes",mes);
        byte[] decrypted=rsa.decrypt(mes.getBytes("ISO-8859-1"),global.N.toString(),global.D.toString());
        Log.d("check_decrypted",(new String(decrypted,"ISO-8859-1")));
        return (new String(decrypted,"ISO-8859-1"));
//        RSA rsa=new RSA();
//        Log.d("mes",mes);
//        byte[] decrypted=rsa.decrypt(mes.getBytes("ISO-8859-1"),global.N.toString(),global.D.toString());
//        Log.d("check_decrypted",(new String(decrypted,"ISO-8859-1")));
//
//        return mes;
    }
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_READ_STATE: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission granted!
//                    // you may now do the action that requires this permission
//                } else {
//                    // permission denied
//                }
//                return;
//            }
//
//        }
//    }
}
