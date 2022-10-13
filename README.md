
## Bluetooth, In simple Words : 

I build this simple project for practice purposes, be free to use the source code whatever you want. 

Bluetooth allows a device to wirelessly exchange data with other Bluetooth devices. 
  
By using Using the Bluetooth APIs, an app can perform the following:

- Scan for other Bluetooth devices.
- Query the local Bluetooth adapter for paired Bluetooth devices.
- Establish RFCOMM channels. 
- Connect to other devices through service discovery.
- Transfer data to and from other devices.
- Manage multiple connections.


#### What are RFCOMM channels? 

RFCOMM is a simple transport protocol, the device release this channel that had linked it to the discoverable device after the pairing process is completed.

#### What is Device Discovery ?
Discovery is a scanning procedure that searches the local area for Bluetooth-enabled devices and requests some information about each one. This process is sometimes referred to as discovering, inquiring or scanning.
A nearby Bluetooth device responds to a discovery request only if it is currently accepting information requests being discoverable.
#### What is the discoverable mode? 
In order to make your device visible to all other devices, you need to make your device in discovery mode.
#### What is Pairing Process?
Pairing, sometimes known as bonding, is a process used in computer networking that helps set up an initial linkage between computing devices to allow communication between them.
When Pairing Process has been done between two or more devices, they can reconnect automatically in the future as long as they’re in range of each other and neither device has removed the bond.




## Randome Code To Share :

```kotlin
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
```

getPairedDevices(bluetoothAdapter: BluetoothAdapter?) : function here job to get all available device ready for pairing process


## Contributing

Contributions are always welcome!



