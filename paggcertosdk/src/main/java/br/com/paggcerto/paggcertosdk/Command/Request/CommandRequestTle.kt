package br.com.paggcerto.paggcertosdk.Command.Request

internal class CommandRequestTle: RequestCard {

    var CMD_ID: String? = null

    init {
        CMD_ID = "TLE"
    }

    override fun createRequest(): String {
        return CMD_ID!!
    }
}