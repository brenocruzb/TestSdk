package br.com.paggcerto.paggcertosdk

import br.com.paggcerto.paggcertosdk.Model.Pagg_Bin
import br.com.paggcerto.paggcertosdk.Model.Pagg_Token
import br.com.paggcerto.paggcertosdk.Rest.Payment.PaymentNetwork
import br.com.paggcerto.paggcertosdk.Service.PinpadService
import br.com.paggcerto.paggcertosdk.Util.Util

class PaggcertoSDK{

    private val listBins = ArrayList<Pagg_Bin>()
    internal var token: Pagg_Token? = null

    val pinpadService = PinpadService()

    companion object {
        private var instance: PaggcertoSDK? = null

        @Synchronized
        private fun createInstance(){
            if(instance == null){
                instance = PaggcertoSDK()
            }
        }

        fun getInstance(): PaggcertoSDK?{
            if(instance == null) createInstance()
            return instance
        }
    }

    fun setEnvironment(environment: Environment){

        val environmentStr = if(environment == Environment.SANDBOX) "sandbox." else if(environment == Environment.HOMOL) "homol." else ""
        val protocol = if(environment == Environment.HOMOL) "http" else "https"
        Util.updateEnvironment(environmentStr, protocol)

    }

    fun activate(token: String, environment: Environment, paggcertoSDKResponse: PaggcertoSDKResponse){

        setEnvironment(environment)

        this@PaggcertoSDK.token = Pagg_Token().apply { this.token = token }

        PaymentNetwork().getBin(object : PaggcertoCallBack<ArrayList<Pagg_Bin>>{
            override fun onSuccess(obj: ArrayList<Pagg_Bin>) {
                listBins.clear()
                listBins.addAll(obj)

                paggcertoSDKResponse.onResult(true, "PaggcertoSDK ativa.")
            }

            override fun onError(code: Int, message: String) {
                this@PaggcertoSDK.token = null
                paggcertoSDKResponse.onResult(false, message)
            }
        })

    }

    fun isActive(): Boolean{
        return listBins.size > 0
    }

    fun getBins(): ArrayList<Pagg_Bin>{
        return listBins
    }
}