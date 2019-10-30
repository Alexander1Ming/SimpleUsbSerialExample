package com.jari.SimpleUsbSerialExample;

/**
 * @author:sunchen
 * @createdtime:2019/10/29
 * @email:sunchencs@foxmail.com
 */
interface SerialListener {
    void onSerialConnect();
    void onSerialConnectError(Exception e);
    void onSerialRead(byte[] data);
    void onSerialIoError(Exception e);
}
