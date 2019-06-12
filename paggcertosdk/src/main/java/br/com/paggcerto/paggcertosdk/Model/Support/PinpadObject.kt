package br.com.paggcerto.paggcertosdk.Model.Support

import android.bluetooth.BluetoothClass.Device
import android.bluetooth.BluetoothSocket
import br.com.paggcerto.paggcertosdk.Communication.CommandReader
import br.com.paggcerto.paggcertosdk.Communication.CommandWriter
import java.io.InputStream
import java.io.OutputStream

internal class PinpadObject: Device() {
    var id: Int? = null

    var name: String? = null
    var macAddress: String? = null

    @Transient
    var bluetoothSocket: BluetoothSocket? = null
    @Transient
    var inputStream: InputStream? = null
    @Transient
    var outPutStream: OutputStream? = null

    @Transient
    var writer: CommandWriter? = null
    @Transient
    var reader: CommandReader? = null

    fun closeConnection() {
        inputStream?.close()
        outPutStream?.close()
        bluetoothSocket?.close()

        bluetoothSocket = null
    }
}
