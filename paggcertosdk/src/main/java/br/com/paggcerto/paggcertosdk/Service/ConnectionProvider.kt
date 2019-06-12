package br.com.paggcerto.paggcertosdk.Service

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import br.com.paggcerto.paggcertosdk.PaggcertoSDK
import br.com.paggcerto.paggcertosdk.PaggcertoSDKResponse

@SuppressLint("StaticFieldLeak")
class ConnectionProvider constructor(private val context: Context, private val message: String): AsyncTask<Void, Void, Boolean>() {

    private var dialog: ProgressDialog? = null

    var dialogTitle: String = ""
    var dialogMessage: String = ""
    var paggcertoCallBack: PaggcertoSDKResponse? = null

    private val pinpadService = PaggcertoSDK.getInstance()?.pinpadService

    override fun onPreExecute() {
        dialog = ProgressDialog.show(context, dialogTitle, dialogMessage, false, false)
    }

    override fun doInBackground(vararg p0: Void?): Boolean {

        val paymentDevice = pinpadService?.connect()

        if(paymentDevice != null){
            return pinpadService?.writeDisplayMessage(message) ?: false
        }
        return false
    }

    override fun onPostExecute(result: Boolean) {
        paggcertoCallBack?.onResult(result, if(result) "Conexão realizada com sucesso" else "Falha ao se conectar")

        dialog?.dismiss()
    }
}