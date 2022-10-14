package com.practice.andbluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.practice.andbluetooth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val REQUEST_ENABLE_BT = 99
    private lateinit var binding: ActivityMainBinding

    lateinit var receiver : Receiver

    @SuppressLint("NewApi", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        receiver = Receiver()


        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        binding.go.setOnClickListener {
            PermissionX.init(this).permissions(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT
            )
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {

                    }
                }

            allPermissionGranted(bluetoothManager, bluetoothAdapter)
        }
        binding.startDiscovery.setOnClickListener {
            bluetoothAdapter?.startDiscovery()
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        setContentView(binding.root)
    }


    private fun allPermissionGranted(
        bluetoothManager: BluetoothManager,
        bluetoothAdapter: BluetoothAdapter?
    ) {

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled) {
                actionBlueTooth()
            } else {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                getResult.launch(enableBluetoothIntent)
            }
        }
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i("INFO", "resultCode : ${it.resultCode} ")
            if (it.resultCode == Activity.RESULT_OK) {
                actionBlueTooth()
            }
        }

    private fun actionBlueTooth() {

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


}