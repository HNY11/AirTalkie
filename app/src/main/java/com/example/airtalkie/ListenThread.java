package com.example.airtalkie;

import android.bluetooth.BluetoothSocket;

public class ListenThread {

    private static BluetoothSocket mSocket;

    // get socket object
    public static void setSocketClint(BluetoothSocket socket) {
       mSocket=socket;
    }

    // Return socket object
    public static BluetoothSocket getSocket() {
        return mSocket;
    }

}
