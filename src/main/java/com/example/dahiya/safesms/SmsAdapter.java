package com.example.dahiya.safesms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class SmsAdapter extends BaseAdapter{

    ArrayList<SmsModel> smsModelArrayList;
    Context context;
    private static LayoutInflater inflater=null;;

    public SmsAdapter(MainActivity mainActivity, ArrayList<SmsModel> smsModelArrayList) {
// TODO Auto-generated constructor stub

        context=mainActivity;
        this.smsModelArrayList = smsModelArrayList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return smsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv_mobile;
        TextView tv_message;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        SmsModel item = smsModelArrayList.get(position);
        view = inflater.inflate(R.layout.layout_sms_item, null);

        holder.tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
        holder.tv_message = (TextView) view.findViewById(R.id.tv_message);

        String mes=item.getMessage();
        String mes2="";
        int count=0;
        for(int i=0;i<mes.length();i++)
        {
            if(count>7 || mes2.length()>45 || mes.charAt(i)=='\n')
            {
                mes2+="...,";
                break;
            }
            if(mes.charAt(i)==' ')
            {
                count++;
            }
            mes2=mes2+mes.charAt(i);
        }
        holder.tv_mobile.setText(item.getMobile());
        holder.tv_message.setText(mes2);

        return view;
    }

}
