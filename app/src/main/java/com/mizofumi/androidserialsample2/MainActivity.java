package com.mizofumi.androidserialsample2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView hello;
    TextView status;
    Serial serial;
    String deviceName;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hello = (TextView)findViewById(R.id.hello);
        status = (TextView)findViewById(R.id.status);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide(); //未接続時は非表示

        serial = new Serial();

        deviceName = getIntent().getStringExtra("deviceName");

        Log.d(getLocalClassName(), String.valueOf(serial.isConnected()));

        serial.setSerialListener(new SerialListener() {
            String tmp; //断片化した受信データを一時的に保持する変数

            @Override
            public void opened() {
                status.setText("ポートを開放しました");
                fab.setImageResource(R.drawable.ic_flash_off_white_48dp);
            }

            @Override
            public void open_failed(String errorMessage) {
                status.setText("ポートの開放に失敗しました");
            }

            @Override
            public void read(String data) {

                String recived = "";

                //断片化したデータを結合
                if (data != null || data.length() != 0){
                    if (data.contains("\r\n")){
                        recived = tmp + data.replaceAll("\r\n","");
                        tmp = "";
                    }else {
                        tmp += data;
                    }


                    //データにnullが含まれてない。かつ、データの長さが0以上
                    if (!recived.contains("null") && recived.length() > 0){
                        status.setText("データ受信中..."+recived);
                        Log.d(getLocalClassName(),recived);

                        //データをFloat型に変換
                        float recived_data = Float.parseFloat(recived);




                    }
                }
            }

            @Override
            public void read_failed(String errorMessage) {
                status.setText("データの受信に失敗...");
            }

            @Override
            public void write_success() {
                status.setText("データの送信に成功しました");
            }

            @Override
            public void write_failed(String s) {
                status.setText("データの送信に失敗しました");
            }

            @Override
            public void stoped() {
                status.setText("停止しました");
            }

            @Override
            public void closed() {
                status.setText("ポートを閉鎖しました");
            }

            @Override
            public void close_failed(String s) {
                status.setText("ポートを閉鎖に失敗しました");
            }

            @Override
            public void device_connect_success() {
                status.setText("接続しました");
                fab.setImageResource(R.drawable.ic_flash_on_white_48dp);
                fab.show();
            }

            @Override
            public void device_connect_faild(String s) {
                status.setText("接続失敗しました");
            }
        });

        status.setText("接続中...");
        serial.connectDevice(deviceName,UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!serial.isConnected()){
                    serial.open();
                    serial.run();
                }else {
                    if (serial.isRunnable()){
                        serial.stop();
                        finish();
                    }
                }
            }
        });
    }


    @Override
    public void finish() {
        serial.close();
        super.finish();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
