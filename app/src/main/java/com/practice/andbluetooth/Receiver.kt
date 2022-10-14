package com.practice.andbluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class Receiver : BroadcastReceiver(){
    val TAG = "Receiver"
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            BluetoothDevice.ACTION_FOUND -> {
                val device : BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                val deviceName = device?.name
                val deviceHardwareAddress = device?.address

                Log.d(TAG, "onReceive: ${deviceName} $deviceHardwareAddress")
            }
        }
    }

}