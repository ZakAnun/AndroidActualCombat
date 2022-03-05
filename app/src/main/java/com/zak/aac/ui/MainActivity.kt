package com.zak.aac.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zak.aac.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


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

        val retrofit = Retrofit.Builder()
//            .baseUrl("https://www.zakli.cn/")
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
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

