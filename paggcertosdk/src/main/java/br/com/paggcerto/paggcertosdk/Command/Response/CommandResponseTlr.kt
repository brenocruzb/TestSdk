package br.com.paggcerto.paggcertosdk.Command.Response

internal class CommandResponseTlr constructor(response: String?): CommandResponseBase(response){

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n

        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}