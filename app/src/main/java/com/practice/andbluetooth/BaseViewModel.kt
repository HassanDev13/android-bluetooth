package com.practice.andbluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {
    private val pairedDevicesList = mutableListOf<BluetoothDevice>()
    private val pairedDevicesEvent = Channel<DataState<MutableList<BluetoothDevice>>>()
    val pairedDevices = pairedDevicesEvent.receiveAsFlow()

    @SuppressLint("MissingPermission")
    fun getPairedDevices (bluetoothAdapter: BluetoothAdapter?) = viewModelScope.launch {
        val pDevice : Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

        if(pDevice == null){
           pairedDevicesEvent.send(DataState.Error("Error unknown"))
           return@launch
        }

        pDevice.forEach {
            pairedDevicesList.add(it)
        }
        if(pairedDevicesList.isEmpty()){
            pairedDevicesEvent.send(DataState.Info("There no paired device"))
            return@launch
        }

        pairedDevicesEvent.send(DataState.Success(pairedDevicesList))
    }






}