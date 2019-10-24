package br.com.paggcerto.pagcertosdk.util

import org.json.JSONObject
import retrofit2.Response

internal object Util {

    private var ENVIRONMENT = "null"
    private var PROTOCOL = "https"
    var PAYMENTS_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var PAYMENTS_V3_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v3/"
    var BILLING_API_URL = "$PROTOCOL://billing.${ENVIRONMENT}paggcerto.com.br/api/v1/"
    var ACCOUNT_API_URL = "$PROTOCOL://account.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var PAYMENT_ACCOUNT_API_URL = "$PROTOCOL://payment-accounts.${ENVIRONMENT}paggcerto.com.br/api/v1/"
    var WARNINGS_API_URL = "$PROTOCOL://warnings.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var RECURRING_API_URL = "$PROTOCOL://recurring.${ENVIRONMENT}paggcerto.com.br/api/v2/"

    fun updateEnvironment(environment: String, protocol: String){
        ENVIRONMENT = environment
        PROTOCOL = protocol

        PAYMENTS_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        PAYMENTS_V3_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v3/"
        BILLING_API_URL = "$PROTOCOL://billing.${ENVIRONMENT}paggcerto.com.br/api/v1/"
        ACCOUNT_API_URL = "$PROTOCOL://account.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        PAYMENT_ACCOUNT_API_URL = "$PROTOCOL://payment-accounts.${ENVIRONMENT}paggcerto.com.br/api/v1/"
        WARNINGS_API_URL = "$PROTOCOL://warnings.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        RECURRING_API_URL = "$PROTOCOL://recurring.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    }

    fun printError(response: Response<String>): String{
        return try {
            JSONObject(response.errorBody()?.string()).getString("error")
        } catch (e: Exception) {
            e.printStackTrace()
            "RESPONSE ERROR"
        }
    }
}