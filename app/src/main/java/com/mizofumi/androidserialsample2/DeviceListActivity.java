package com.mizofumi.androidserialsample2;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mizofumi.androidserialsample2.List.DeviceList;
import com.mizofumi.androidserialsample2.List.DeviceListAdapter;

public class DeviceListActivity extends AppCompatActivity {
    ListView listView;
    Button reload;
    private DeviceListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        listView = (ListView)findViewById(R.id.deviceList);
        reload = (Button)findViewById(R.id.reload);

        adapter = new DeviceListAdapter(this);
        listView.setAdapter(adapter);

        getDeviceList();

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearDevice();
                getDeviceList();
            }
        });
    }

    private void getDeviceList(){
        Serial serial = new Serial();
        for (BluetoothDevice d : serial.getDevices()) {
            adapter.addDevice(new DeviceList(d.getName(),d.getAddress()));
        }
    }
}
