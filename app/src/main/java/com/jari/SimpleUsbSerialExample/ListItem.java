package com.jari.SimpleUsbSerialExample;

import android.hardware.usb.UsbDevice;

import com.hoho.android.usbserial.driver.UsbSerialDriver;

public class ListItem {
    UsbDevice device;
    int port;
    UsbSerialDriver driver;

    ListItem(UsbDevice device, int port, UsbSerialDriver driver) {
        this.device = device;
        this.port = port;
        this.driver = driver;
    }
}
