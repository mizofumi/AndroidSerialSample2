package com.mizofumi.androidserialsample2.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mizofumi.androidserialsample2.MainActivity;
import com.mizofumi.androidserialsample2.R;

import java.util.ArrayList;

/**
 * Created by mizofumi on 2016/12/16.
 */

public class DeviceListAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    ArrayList<DeviceList> deviceLists = new ArrayList<>();

    public DeviceListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDevice(DeviceList deviceList){
        deviceLists.add(deviceList);
        this.notifyDataSetChanged();
    }

    public void clearDevice(){
        deviceLists.clear();
        this.notifyDataSetChanged();
    }

    public void removeDevice(int position){
        deviceLists.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return deviceLists.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.devicelist,parent,false);
        TextView deviceName = (TextView)view.findViewById(R.id.deviceName);
        TextView deviceMacAddress = (TextView)view.findViewById(R.id.deviceMacAddress);

        deviceName.setText(deviceLists.get(position).getName());
        deviceMacAddress.setText(deviceLists.get(position).getMacAddress());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("deviceName",deviceLists.get(position).getName());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
