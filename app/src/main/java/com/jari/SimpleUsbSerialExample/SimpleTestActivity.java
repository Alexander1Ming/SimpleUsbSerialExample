package com.jari.SimpleUsbSerialExample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.HexDump;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author:sunchen
 * @createdtime:2019/10/29
 * @email:sunchencs@foxmail.com
 * 用于测试发送报文到对方串口
 */
public class SimpleTestActivity extends AppCompatActivity {

    private Button sendBtn;
    SerialInputOutputManager usbIoManager;
    public static final int WRITE_WAIT_MILLIS = 1000;
    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d("Example", "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    SimpleTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleTestActivity.this.updateReceivedData(data);
                        }
                    });
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);

        sendBtn = (Button) findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(v -> sendMsg());
    }

    void sendMsg() {
        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
            return;
        }

        UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
        try {
            port.open(connection);
            port.setParameters(19200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            usbIoManager = new SerialInputOutputManager(port, mListener);
            Executors.newSingleThreadExecutor().submit(usbIoManager);
            port.write(HexUtils.toByteArray(getHexMsg()), WRITE_WAIT_MILLIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateReceivedData(byte[] data) {
        final String message = "Read " + data.length + " bytes: \n"
                + HexDump.dumpHexString(data) + "\n\n";
        Log.e("Example" , message);
    }

    public String getHexMsg() {
        String dataStr = "7e";
        byte start = 0x7e;
        byte end = 0X7E;
        System.out.println(start == end);
        byte[] bytes = HexUtils.toByteArray(dataStr);
        System.out.println(bytes[0] == start);
        return dataStr;
    }
}
