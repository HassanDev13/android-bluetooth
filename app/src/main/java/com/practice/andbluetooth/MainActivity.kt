package com.practice.andbluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.permissionx.guolindev.PermissionX
import com.practice.andbluetooth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()  {
    val REQUEST_ENABLE_BT = 99
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bluetoothManager: BluetoothManager =  getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        binding.go.setOnClickListener {
            PermissionX.init(this).permissions(Manifest.permission.BLUETOOTH)
                .request { allGranted, grantedList, deniedList ->
                    if(allGranted){
                        allPermissionGranted(bluetoothManager , bluetoothAdapter)
                        if(grantedList.size != 0){
                            Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }


    private fun allPermissionGranted(
        bluetoothManager: BluetoothManager,
        bluetoothAdapter: BluetoothAdapter?
    ) {

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter?.isEnabled) {
                actionBlueTooth()
            } else {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return
                }
                getResult.launch(enableBluetoothIntent)
            }
        }
    }

    private fun actionBlueTooth() {

    }
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.i("INFO" ,"resultCode : ${it.resultCode} ")
        if(it.resultCode == Activity.RESULT_OK){
            actionBlueTooth()
        }
    }




}