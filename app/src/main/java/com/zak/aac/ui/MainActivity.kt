package com.zak.aac.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zak.aac.R
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy


/**
 *
 * @author zak
 * @date 2021/7/19
 * @email linhenji@163.com / linhenji17@gmail.com
 */
class MainActivity: AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val okClient = OkHttpClient.Builder().eventListener(object : EventListener() {

            override fun callStart(call: okhttp3.Call) {
                Log.d(TAG, "callStart: ")
            }

            override fun proxySelectStart(call: okhttp3.Call, url: HttpUrl) {
                Log.d(TAG, "proxySelectStart: ")
            }

            override fun proxySelectEnd(call: okhttp3.Call, url: HttpUrl, proxies: List<Proxy>) {
                Log.d(TAG, "proxySelectEnd: ")
            }

            override fun dnsStart(call: okhttp3.Call, domainName: String) {
                Log.d(TAG, "dnsStart: ")
            }

            override fun dnsEnd(
                call: okhttp3.Call,
                domainName: String,
                inetAddressList: List<InetAddress>
            ) {
                Log.d(TAG, "dnsEnd: ")
            }

            override fun connectStart(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy
            ) {
                Log.d(TAG, "connectStart: ")
            }

            override fun secureConnectStart(call: okhttp3.Call) {
                Log.d(TAG, "secureConnectStart: ")
            }

            override fun secureConnectEnd(call: okhttp3.Call, handshake: Handshake?) {
                Log.d(TAG, "secureConnectEnd: ")
            }

            override fun connectEnd(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy,
                protocol: Protocol?
            ) {
                Log.d(TAG, "connectEnd: ")
            }

            override fun connectFailed(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy,
                protocol: Protocol?,
                ioe: IOException
            ) {
                Log.d(TAG, "connectFailed: ")
            }

            override fun connectionAcquired(call: okhttp3.Call, connection: Connection) {
                Log.d(TAG, "connectionAcquired: ")
            }

            override fun connectionReleased(call: okhttp3.Call, connection: Connection) {
                Log.d(TAG, "connectionReleased: ")
            }

            override fun requestHeadersStart(call: okhttp3.Call) {
                Log.d(TAG, "requestHeadersStart: ")
            }

            override fun requestHeadersEnd(call: okhttp3.Call, request: Request) {
                Log.d(TAG, "requestHeadersEnd: ")
            }

            override fun requestBodyStart(call: okhttp3.Call) {
                Log.d(TAG, "requestBodyStart: ")
            }

            override fun requestBodyEnd(call: okhttp3.Call, byteCount: Long) {
                Log.d(TAG, "requestBodyEnd: ")
            }

            override fun requestFailed(call: okhttp3.Call, ioe: IOException) {
                Log.d(TAG, "requestFailed: ")
            }

            override fun responseHeadersStart(call: okhttp3.Call) {
                Log.d(TAG, "responseHeadersStart: ")
            }

            override fun responseHeadersEnd(call: okhttp3.Call, response: okhttp3.Response) {
                Log.d(TAG, "responseHeadersEnd: ")
            }

            override fun responseBodyStart(call: okhttp3.Call) {
                Log.d(TAG, "responseBodyStart: ")
            }

            override fun responseBodyEnd(call: okhttp3.Call, byteCount: Long) {
                Log.d(TAG, "responseBodyEnd: ")
            }

            override fun responseFailed(call: okhttp3.Call, ioe: IOException) {
                Log.d(TAG, "responseFailed: ")
            }

            override fun callEnd(call: okhttp3.Call) {
                Log.d(TAG, "callEnd: ")
            }

            override fun callFailed(call: okhttp3.Call, ioe: IOException) {
                Log.d(TAG, "callFailed: ")
            }

            override fun satisfactionFailure(call: okhttp3.Call, response: okhttp3.Response) {
                Log.d(TAG, "satisfactionFailure: ")
            }

            override fun cacheHit(call: okhttp3.Call, response: okhttp3.Response) {
                Log.d(TAG, "cacheHit: ")
            }

            override fun cacheMiss(call: okhttp3.Call) {
                Log.d(TAG, "cacheMiss: ")
            }

            override fun cacheConditionalHit(call: okhttp3.Call, cachedResponse: okhttp3.Response) {
                Log.d(TAG, "cacheConditionalHit: ")
            }


        })

        val retrofit = Retrofit.Builder()
//            .baseUrl("https://www.zakli.cn/")
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okClient.build())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        findViewById<Button>(R.id.send).setOnClickListener {
            apiService.listRepos("ZakAnun")?.enqueue(object : Callback<Repo?> {

                override fun onResponse(call: Call<Repo?>, response: Response<Repo?>) {
                    Log.d(TAG, "onResponse: ok")
                    findViewById<TextView>(R.id.content).text = response.body()?.toString()
                }

                override fun onFailure(call: Call<Repo?>, t: Throwable) {
                    Log.d(TAG, "onFailure: no ok")
                }
            })
        }
    }

}

interface ApiService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<Repo?>?
}

