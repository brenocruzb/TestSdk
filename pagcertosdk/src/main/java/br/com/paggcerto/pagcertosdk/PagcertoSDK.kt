package br.com.paggcerto.pagcertosdk

import android.content.Context
import androidx.room.Room
import br.com.pagcertopinpad.model.payment.Bin
import br.com.pagcertopinpad.service.PinpadService
import br.com.paggcerto.pagcertosdk.model.account.response.Token
import br.com.paggcerto.pagcertosdk.model.dao.database.AppDatabase

import br.com.paggcerto.pagcertosdk.rest.payment.PaymentNetwork
import br.com.paggcerto.pagcertosdk.util.Util

object PagcertoSDK{

    var token: Token? = null
    lateinit var pinpadService : PinpadService

    var environment: Environment? = null
    set(value) {
        field = value
        val environmentStr =
            when(value){
                Environment.SANDBOX -> "sandbox."
                Environment.HOMOL -> "homol."
                Environment.MIGRATION -> "migration."
                Environment.PRODUCTION -> ""
                else -> ""
            }

        val protocol = if(environment == Environment.HOMOL) "http" else "https"
        Util.updateEnvironment(environmentStr, protocol)
    }

    fun activate(context: Context, pagcertoSDKResponse: PagcertoSDKResponse){

        val db = Room.databaseBuilder(context, AppDatabase::class.java, "bins")
            .allowMainThreadQueries()
            .build()
        val binDao = db.binDao()

        if(binDao.all.isEmpty()){
            PaymentNetwork().getBin(object : PagcertoCallBack<List<Bin>>{
                override fun onSuccess(obj: List<Bin>) {
                    obj.forEach { item ->
                        binDao.insertAll(br.com.paggcerto.pagcertosdk.model.dao.Bin(
                            id = 0,
                            cardBrand = item.cardBrand,
                            regex = item.regex,
                            debit = item.debit,
                            emvSupported = item.emvSupported,
                            maximumInstallment = item.maximumInstallment.toInt()))
                    }

                    pinpadService = PinpadService(obj)
                    db.close()

                    pagcertoSDKResponse.onResult(true, "PagcertoSDK ativa.")
                }

                override fun onError(code: Int, message: String) {
                    pagcertoSDKResponse.onResult(false, message)
                }
            })
        }else{
            val bins: List<Bin> = binDao.all.map { item -> Bin(
                cardBrand = item.cardBrand,
                regex = item.regex,
                debit = item.debit,
                emvSupported = item.emvSupported,
                maximumInstallment = item.maximumInstallment
            ) }

            pinpadService = PinpadService(bins)
            db.close()

            pagcertoSDKResponse.onResult(true, "PagcertoSDK ativa.")
        }
    }

    fun isActive(): Boolean = ::pinpadService.isInitialized && pinpadService.listBins.isNotEmpty()
}