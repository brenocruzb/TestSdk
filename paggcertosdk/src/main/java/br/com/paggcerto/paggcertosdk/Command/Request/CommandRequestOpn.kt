package br.com.paggcerto.paggcertosdk.Command.Request

internal class CommandRequestOpn: RequestCard {

    /**Este comando aloca os recursos de hardware e software necessários ao funcionamento do pinpad**/
    override fun createRequest(): String {
        return "OPN"
    }
}