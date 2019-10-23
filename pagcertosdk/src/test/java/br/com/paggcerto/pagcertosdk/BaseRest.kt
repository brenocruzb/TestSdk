package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.account.response.Token
import org.junit.Assert.assertTrue
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch

abstract class BaseRest {
    private val thici_couto = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hc" +
            "y54bWxzb2FwLm9yZy93cy8yMDA5LzA5L2lkZW50aXR5L2NsYWltcy9hY3RvciI6IlVzZXIiLCJBcHBsaWNhd" +
            "GlvbklkIjoiNCIsIkhvbGRlcklkIjoiMTciLCJVc2VySWQiOiIxNyIsIkxpbmtDb2RlIjoiMzA3MzEwNjkiL" +
            "CJTYWx0IjoiNzg2MzJkMDNhY2RmNGM4YThlY2FhMzc2OGQ5ZTdiYzMiLCJpc3MiOiJhMDczOGNkZCIsImF1Z" +
            "CI6ImVhMmI0N2VhYmMwNDMifQ.qGj48Pok2RRlx4HwyMIt_g9Yg9fCeAeuG8EnNNPfqMU"

    private val selder = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bW" +
            "xzb2FwLm9yZy93cy8yMDA5LzA5L2lkZW50aXR5L2NsYWltcy9hY3RvciI6IlVzZXIiLCJBcHBsaWNhdGlvbk" +
            "lkIjoiNCIsIkhvbGRlcklkIjoiMzMiLCJVc2VySWQiOiIzMyIsIkxpbmtDb2RlIjoiODE5NTA0MDMiLCJTYW" +
            "x0IjoiNDI1NGU5NGJhNTYwNDAzMmIwNDQ4ZjI5NjY4ODFlNjUiLCJpc3MiOiJhMDczOGNkZCIsImF1ZCI6Im" +
            "VhMmI0N2VhYmMwNDMifQ.3cu0BpkeIo-_Iq9KsTq1FArF7Wgp55wHQw9wDRHs41A"

    private val elder = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWx" +
            "zb2FwLm9yZy93cy8yMDA5LzA5L2lkZW50aXR5L2NsYWltcy9hY3RvciI6IlVzZXIiLCJBcHBsaWNhdGlvbk" +
            "lkIjoiNCIsIkhvbGRlcklkIjoiMTE0IiwiVXNlcklkIjoiMTE0IiwiTGlua0NvZGUiOiIzMTQ5ODA3NyIsI" +
            "lNhbHQiOiJmNmU1NTNmNDVmNjA0N2JkODYyNzhiNjc5Y2VkMmRhNSIsImlzcyI6ImEwNzM4Y2RkIiwiYXVk" +
            "IjoiZWEyYjQ3ZWFiYzA0MyJ9.6Tq2N4hFlYM_D3L3KVNTZNzOXp5sgIHV11uqE0Lb4Hk"

    private val breno = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW" +
            "1hcy54bWxzb2FwLm9yZy93cy8yMDA5LzA5L2lkZW50aXR5L2NsYWltcy9hY3RvciI6IlVzZXIiLCJBcH" +
            "BsaWNhdGlvbklkIjoiNCIsIkhvbGRlcklkIjoiMTAyNTciLCJVc2VySWQiOiIxMDI1NyIsIkxpbmtDb2" +
            "RlIjoiMDE2NTMzODEiLCJTYWx0IjoiYTExMTU2NjUzZDI3NDc4ZTgwZWEzOWYwODFjNzRjNWIiLCJpc3" +
            "MiOiJhMDczOGNkZCIsImF1ZCI6ImVhMmI0N2VhYmMwNDMifQ.vTAdDhTZS5kdVf4Dm2g0lySeizmTft8" +
            "J7Hp_XoCxOxM"

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        PagcertoSDK.environment = Environment.HOMOL
        PagcertoSDK.token = Token(thici_couto)
    }

    protected fun assert(signal: CountDownLatch, method: String, code: Int, message: String? = null){
        assertTrue(true)
        println("[[$method]]: Code -> $code${if(!message.isNullOrEmpty()) "; Message -> $message" else ""}")
        signal.countDown()
    }
}