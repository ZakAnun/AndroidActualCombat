package com.zak.aac.ui

import android.os.Bundle
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
import timber.log.Timber
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
                Timber.d("callStart:")
            }

            override fun proxySelectStart(call: okhttp3.Call, url: HttpUrl) {
                Timber.d("proxySelectStart: ")
            }

            override fun proxySelectEnd(call: okhttp3.Call, url: HttpUrl, proxies: List<Proxy>) {
                Timber.d("proxySelectEnd: ")
            }

            override fun dnsStart(call: okhttp3.Call, domainName: String) {
                Timber.d("dnsStart: ")
            }

            override fun dnsEnd(
                call: okhttp3.Call,
                domainName: String,
                inetAddressList: List<InetAddress>
            ) {
                Timber.d("dnsEnd: ")
            }

            override fun connectStart(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy
            ) {
                Timber.d("connectStart: ")
            }

            override fun secureConnectStart(call: okhttp3.Call) {
                Timber.d("secureConnectStart: ")
            }

            override fun secureConnectEnd(call: okhttp3.Call, handshake: Handshake?) {
                Timber.d("secureConnectEnd: ")
            }

            override fun connectEnd(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy,
                protocol: Protocol?
            ) {
                Timber.d("connectEnd: ")
            }

            override fun connectFailed(
                call: okhttp3.Call,
                inetSocketAddress: InetSocketAddress,
                proxy: Proxy,
                protocol: Protocol?,
                ioe: IOException
            ) {
                Timber.d("connectFailed: ")
            }

            override fun connectionAcquired(call: okhttp3.Call, connection: Connection) {
                Timber.d("connectionAcquired: ")
            }

            override fun connectionReleased(call: okhttp3.Call, connection: Connection) {
                Timber.d("connectionReleased: ")
            }

            override fun requestHeadersStart(call: okhttp3.Call) {
                Timber.d("requestHeadersStart: ")
            }

            override fun requestHeadersEnd(call: okhttp3.Call, request: Request) {
                Timber.d("requestHeadersEnd: ")
            }

            override fun requestBodyStart(call: okhttp3.Call) {
                Timber.d("requestBodyStart: ")
            }

            override fun requestBodyEnd(call: okhttp3.Call, byteCount: Long) {
                Timber.d("requestBodyEnd: ")
            }

            override fun requestFailed(call: okhttp3.Call, ioe: IOException) {
                Timber.d("requestFailed: ")
            }

            override fun responseHeadersStart(call: okhttp3.Call) {
                Timber.d("responseHeadersStart: ")
            }

            override fun responseHeadersEnd(call: okhttp3.Call, response: okhttp3.Response) {
                Timber.d("responseHeadersEnd: ")
            }

            override fun responseBodyStart(call: okhttp3.Call) {
                Timber.d("responseBodyStart: ")
            }

            override fun responseBodyEnd(call: okhttp3.Call, byteCount: Long) {
                Timber.d("responseBodyEnd: ")
            }

            override fun responseFailed(call: okhttp3.Call, ioe: IOException) {
                Timber.d("responseFailed: ")
            }

            override fun callEnd(call: okhttp3.Call) {
                Timber.d("callEnd: ")
            }

            override fun callFailed(call: okhttp3.Call, ioe: IOException) {
                Timber.d("callFailed: ")
            }

            override fun satisfactionFailure(call: okhttp3.Call, response: okhttp3.Response) {
                Timber.d("satisfactionFailure: ")
            }

            override fun cacheHit(call: okhttp3.Call, response: okhttp3.Response) {
                Timber.d("cacheHit: ")
            }

            override fun cacheMiss(call: okhttp3.Call) {
                Timber.d("cacheMiss: ")
            }

            override fun cacheConditionalHit(call: okhttp3.Call, cachedResponse: okhttp3.Response) {
                Timber.d("cacheConditionalHit: ")
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
                    Timber.d("onResponse: ok")
                    findViewById<TextView>(R.id.content).text = response.body()?.toString()
                }

                override fun onFailure(call: Call<Repo?>, t: Throwable) {
                    Timber.d("onFailure: no ok")
                }
            })
        }

        print("Hello World")
    }

}

interface ApiService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<Repo?>?
}

