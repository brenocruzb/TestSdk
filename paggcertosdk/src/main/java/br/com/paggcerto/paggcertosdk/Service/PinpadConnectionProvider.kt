package br.com.paggcerto.paggcertosdk.Service

import android.annotation.SuppressLint
import android.app.Activity
import android.os.AsyncTask

import br.com.paggcerto.paggcertosdk.Communication.CommandReader
import br.com.paggcerto.paggcertosdk.Communication.CommandWriter
import br.com.paggcerto.paggcertosdk.Model.Table.Aid
import br.com.paggcerto.paggcertosdk.Model.Table.Capk
import br.com.paggcerto.paggcertosdk.PaggcertoSDK
import br.com.paggcerto.paggcertosdk.Rest.Tables.TablesNetwork
import br.com.paggcerto.paggcertosdk.Service.Interfaces.PinpadServiceCallBack
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorBinInstall
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorBinNotFound
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorCreateCard
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorDownloadTables
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorGetNumberCard
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorInstallDebit
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorLoadTables
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorPinpadCommunication
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorReadChip
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorReadMagneticStripe
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorRequiredChip
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorTransaction
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.transactionCanceled
import br.com.paggcerto.paggcertosdk.Util.Util.baneseRegex
import br.com.paggcerto.paggcertosdk.Command.Request.*
import br.com.paggcerto.paggcertosdk.Command.Response.*
import br.com.paggcerto.paggcertosdk.Model.*
import br.com.paggcerto.paggcertosdk.Model.Support.PinpadObject
import br.com.paggcerto.paggcertosdk.ReadCardInterface
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil
import br.com.paggcerto.paggcertosdk.Util.StringMessageUtil.errorRequiredMagneticStripe
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@SuppressLint("StaticFieldLeak")
internal class PinpadConnectionProvider constructor(private val activity: Activity,
                                           pinpadObject: PinpadObject?,
                                           private val installments: Int,
                                           private val readCardInterface: ReadCardInterface?,
                                           private val pinpadServiceCallBack: PinpadServiceCallBack): AsyncTask<CommandRequestGcr?, Void, Boolean>(){

    private var writer: CommandWriter? = pinpadObject?.writer
    private var reader: CommandReader? = pinpadObject?.reader

    private val card = Pagg_Card()

    private var isOnline = false

    private var gcrSecondTrail: List<String>? = null
    private var selectedCardBrand: Pagg_Bin? = null

    private var errorMessage: String = ""

    override fun doInBackground(vararg params: CommandRequestGcr?): Boolean {
        val requestGcr: CommandRequestGcr = params[0] ?: return false

        if(!requestGcr.credit && installments > 1){
            errorMessage = errorInstallDebit
            return false
        }

        var status = true
        writer?.writeRequest(requestGcr.createRequest())//Request GCR
        var responseGcr = reader?.getResponse()// Response GCR
        var commandResponseGcr = CommandResponseGcr(responseGcr)
        if(commandResponseGcr.needLoadTable){
            getTables(requestGcr)
            writer?.writeRequest(requestGcr.createRequest())
            responseGcr = reader?.getResponse()
            commandResponseGcr = CommandResponseGcr(responseGcr)
            when {
                commandResponseGcr.needLoadTable -> {
                    errorMessage = errorLoadTables
                    status = false
                }
                commandResponseGcr.hasError() -> {
                    errorMessage = errorDownloadTables
                    status = false
                }
                else -> fillCard(requestGcr, commandResponseGcr)
            }
        }else if(commandResponseGcr.userCancelled){
            errorMessage = transactionCanceled
            status = false
        }else if(commandResponseGcr.readChipError){
            errorMessage = errorReadChip
            status = false
        }else if(commandResponseGcr.timeout){
            errorMessage = StringMessageUtil.errorTimeOut
            status = false
        }else if(commandResponseGcr.errorReadMagneticStripe){
            errorMessage = errorReadMagneticStripe
            status = false
        }
        else if(!commandResponseGcr.hasError()){
            fillCard(requestGcr, commandResponseGcr)
        }else{
            errorMessage = errorTransaction
            status = false
        }

        if(status){
            status = executeCardProcess(requestGcr, commandResponseGcr)
        }

        return status
    }

    override fun onPostExecute(result: Boolean) {
        if(result)
            pinpadServiceCallBack.onSuccess(card, isOnline)
        else
            pinpadServiceCallBack.onError(errorMessage)
    }

    private fun fillCard(commandRequestGcr: CommandRequestGcr, commandResponseGcr: CommandResponseGcr): List<Pagg_Card>? {
        gcrSecondTrail = commandResponseGcr.GCR_TRK2?.split("=") ?: return null
        if(gcrSecondTrail?.size!! < 2) {
            errorMessage = errorGetNumberCard
            return null
        }

        val cardNumber = gcrSecondTrail!![0]

        selectedCardBrand = findBin(commandRequestGcr, cardNumber)

        when {
            selectedCardBrand == null -> {
                errorMessage = errorBinNotFound
            }
            selectedCardBrand?.maximumInstallment!! < installments -> {
                errorMessage = errorBinInstall
            }
            else -> try {

                card.amountPaid = commandRequestGcr.amount.toDouble()
                card.credit = commandRequestGcr.credit
                card.installments = installments

                val year = gcrSecondTrail!![1].substring(0, 2)
                val month = gcrSecondTrail!![1].substring(2, 4)

                card.number = cardNumber

                val locale = Locale("pt", "BR")
                var simpleDateFormat2 = SimpleDateFormat("yy", locale)
                val parsedDate = simpleDateFormat2.parse(year)
                simpleDateFormat2 = SimpleDateFormat("yyyy", locale)
                val newFormattedDate = simpleDateFormat2.format(parsedDate)

                card.expirationYear = newFormattedDate.toInt()
                card.expirationMonth = month.toInt()

                if ((selectedCardBrand?.cardBrand?.equals("banesecard", false)!! || selectedCardBrand?.cardBrand.equals("mastercard", false)) && commandResponseGcr.GCR_CHNAME?.trim()?.length == 0) {

                    card.holderName = "CLIENTE BANESECARD"
                } else {
                    val name = commandResponseGcr.GCR_CHNAME?.split("/")

                    if (name?.size!! >= 2) {
                        card.holderName = name[1].trim { it <= ' ' } + " " + name[0].trim { it <= ' ' }
                    } else {
                        card.holderName = name[0].replace("\\s+$".toRegex(), "")
                    }
                }

                val metadata = Pagg_Metadata()
                metadata.chip = commandResponseGcr.GCR_CARDTYPE?.toInt() == 3
                metadata.sequenceNumber = commandResponseGcr.GCR_PANSEQNO?.toInt()

                val tracks = Pagg_Tracks()
                if (commandResponseGcr.GCR_TRK1 != null && commandResponseGcr.GCR_TRK1?.trim()?.length!! > 0)
                    tracks.number1 = commandResponseGcr.GCR_TRK1
                if (commandResponseGcr.GCR_TRK2 != null && commandResponseGcr.GCR_TRK2?.trim()?.length!! > 0)
                    tracks.number2 = commandResponseGcr.GCR_TRK2
                if (commandResponseGcr.GCR_TRK3 != null && commandResponseGcr.GCR_TRK3?.trim()?.length!! > 0)
                    tracks.number3 = commandResponseGcr.GCR_TRK3

                metadata.tracks = tracks

                card.metadata = metadata

                return Arrays.asList(card)
            }catch (ex: Exception){
                errorMessage = errorCreateCard
            }
        }

        return null
    }

    private fun findBin(commandRequestGcr: CommandRequestGcr, cardNumber: String): Pagg_Bin? {
        val listBins = PaggcertoSDK.getInstance()?.getBins()

        listBins?.forEach { bin ->
            val p = Pattern.compile(bin.regex)
            val m = p.matcher(cardNumber)

            if(m.find()){
                return if(!commandRequestGcr.credit && !bin.debit!!){
                    null
                }else{
                    bin
                }
            }
        }

        return null
    }

    private fun getTables(commandRequestGcr: CommandRequestGcr){
        val tablesNetwork = TablesNetwork()
        val tables =  tablesNetwork.get()

        var result = true

        //TLI
        val commandRequestTli = CommandRequestTli(commandRequestGcr)
        writer?.writeRequest(commandRequestTli.createRequest())

        val responseTli = CommandResponseTli(reader?.getResponse())
        if(responseTli.tableError) result = false

        //TLR
        val listAid = tables?.aidTbl?.aid
        val listCapk = tables?.capkTable?.capk

        listAid?.forEach { aid: Aid ->
            val request = CommandRequestTlr(aid).createRequest()
            writer?.writeRequest(request)

            val commandResponseTlr = CommandResponseTlr(reader?.getResponse())
            if(commandResponseTlr.hasError()) result = false
        }

        listCapk?.forEach { capk: Capk ->
            val request = CommandRequestTlr(capk).createRequest()
            writer?.writeRequest(request)

            val commandResponseTlr = CommandResponseTlr(reader?.getResponse())
            if(commandResponseTlr.hasError()) result = false
        }

        //TLE
        val commandRequestTle = CommandRequestTle()
        writer?.writeRequest(commandRequestTle.createRequest())

        val commandResponseTle = CommandResponseTle(reader?.getResponse())
        if(commandResponseTle.hasError() || commandResponseTle.errorSaveTable) result = false

        if(!result) { /*Falha ao sincronizar tabelas*/ }
    }

    private fun executeCardProcess(commandRequestGcr: CommandRequestGcr, commandResponseGcr: CommandResponseGcr): Boolean {
        val metadata = card.metadata ?: return false

        //verifica se o cartao é banese
        val p = Pattern.compile(baneseRegex)
        val m = p.matcher(card.number)
        val isBanese = m.find()

        if(card.credit && (isBanese || !selectedCardBrand?.emvSupported!!)){

            return if(commandResponseGcr.isMagneticStripe()){
                card.metadata = null

                if(selectedCardBrand?.cardBrand?.equals("banesecard", true)!!){
                    card.securityCode = "9999"
                }else{
                    isOnline = true
                }
                true
            }else{
                errorMessage = errorRequiredMagneticStripe
                false
            }
        }

        if(commandResponseGcr.isMagneticStripe()){

            val additionalData = gcrSecondTrail!![1]
            val serviceCode = additionalData.substring(4, 7)

            // Verifica se o cartao possui CHIP
            if (serviceCode.startsWith("2") || serviceCode.startsWith("6") && (!isBanese || !selectedCardBrand?.emvSupported!!)) {
                errorMessage = errorRequiredChip
                return false
            }
        }

        //Interface de animação
        if(readCardInterface != null)
            activity.runOnUiThread { readCardInterface.didReadCard()}

        if(commandResponseGcr.isMagneticStripe()){//Tarja

            commandRequestGcr.updateAmmountGpn()
            val commandRequestGpn = CommandRequestGpn(commandRequestGcr, commandResponseGcr)
            if(!commandRequestGpn.exception){

                writer?.writeRequest(commandRequestGpn.createRequest())
                val commandResponseGpn = CommandResponseGpn(reader?.getResponse())

                when {
                    commandResponseGpn.userCancelled -> {
                        errorMessage = transactionCanceled
                        return false
                    }
                    commandResponseGpn.errorReadMagneticStripe -> {
                        errorMessage = errorReadMagneticStripe
                        return false
                    }
                    commandResponseGpn.timeout -> {
                        errorMessage = StringMessageUtil.errorTimeOut
                        return false
                    }
                    commandResponseGpn.hasError() -> {
                        errorMessage = errorReadMagneticStripe
                        return false
                    }
                    else -> {
                        metadata.pinOnline = true
                        metadata.pin = commandResponseGpn.GPN_PINBLK
                        metadata.serial = commandResponseGpn.GPN_KSN

                    }
                }

            }else {
                errorMessage = errorPinpadCommunication
                return false
            }

        }else if(commandResponseGcr.isChip()){//Chip

            commandRequestGcr.updateAmmountGoc()
            val commandRequestGoc = CommandRequestGoc(commandRequestGcr)
            if(!commandRequestGoc.exception){

                writer?.writeRequest(commandRequestGoc.createRequest())
                val commandResponseGoc = CommandResponseGoc(reader?.getResponse())

                when {
                    commandResponseGoc.userCancelled -> {
                        errorMessage = transactionCanceled
                        return false
                    }
                    commandResponseGoc.timeout -> {
                        errorMessage = StringMessageUtil.errorTimeOut
                        return false
                    }
                    commandResponseGoc.hasError() -> {
                        errorMessage = errorReadChip
                        return false
                    }
                    else -> {
                        metadata.emvData = commandResponseGoc.GOC_EMVDAT
                        metadata.pinOnline = commandResponseGoc.GOC_PINONL == "1"
                        metadata.pin = commandResponseGoc.GOC_PINBLK
                        metadata.serial = commandResponseGoc.GOC_KSN
                    }
                }
            }else {
                errorMessage = errorPinpadCommunication
                return false
            }
        }

        return true
    }
}