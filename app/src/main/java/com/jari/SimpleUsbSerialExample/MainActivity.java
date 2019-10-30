package com.jari.SimpleUsbSerialExample;

import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author:sunchen
 * @createdtime:2019/10/29
 * @email:sunchencs@foxmail.com
 */
public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mUsbReceiver;
    private ListView lvDetectedDevice;
    private Button btnToSimpleTestActivity;

    private ArrayList<ListItem> listItems = new ArrayList<>();
    private DeviceAdapter deviceAdapter;
    private int baudRate = 9600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //floating点击事件，刷新设备
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        btnToSimpleTestActivity.findViewById(R.id.btn_to_SimpleTextActivity);
        btnToSimpleTestActivity.setOnClickListener(v -> jumpToSimpleTextActivity());

        lvDetectedDevice = (ListView)findViewById(R.id.lv_detected_devices);
        deviceAdapter = new DeviceAdapter(this, listItems);
        lvDetectedDevice.setAdapter(deviceAdapter);
        lvDetectedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item = listItems.get(position);
                if(item.driver == null) {
                    Toast.makeText(MainActivity.this, "device is null", Toast.LENGTH_SHORT);
                } else {
                    Intent intent = new Intent(MainActivity.this, SerialConsoleActivity.class);
                    intent.putExtra("device", item.device.getDeviceId());
                    intent.putExtra("port", item.port);
                    intent.putExtra("baud", baudRate);
                    startActivity(intent);
                }
            }
        });
    }

    void refresh() {
        UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
        UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();
        UsbSerialProber usbCustomProber = CustomProber.getCustomProber();
        listItems.clear();
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        for(UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbDefaultProber.probeDevice(device);
            if(driver == null) {
                driver = usbCustomProber.probeDevice(device);
            }
            if(driver != null) {
                for(int port = 0; port < driver.getPorts().size(); port++)
                    listItems.add(new ListItem(device, port, driver));
            } else {
                listItems.add(new ListItem(device, 0, null));
            }
        }
        deviceAdapter.notifyDataSetChanged();
    }

    void jumpToSimpleTextActivity() {
        Intent intent = new Intent(MainActivity.this, SimpleTestActivity.class);
        startActivity(intent);
    }


}
