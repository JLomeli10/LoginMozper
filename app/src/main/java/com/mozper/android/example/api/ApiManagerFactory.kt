package com.mozper.android.example.api

import android.annotation.SuppressLint
import com.mozper.android.example.BuildConfig
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

object ApiManagerFactory {
    private const val BASE_URL = "https://demo3535907.mockable.io/"

    fun makeRetrofitService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    }

    private fun makeOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(invoke { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 503) {
                throw Exception("Error al conectar :(")
            }
            response })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(TrustManager()), null)

            okHttpClient.addInterceptor(makeLoggingInterceptor(HttpLoggingInterceptor.Level.NONE))
                .sslSocketFactory(sslContext.socketFactory, TrustManager())
                .hostnameVerifier(HostVerifier())

        return okHttpClient.build()
    }

    private fun makeLoggingInterceptor(level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = level
        return logging
    }

    @SuppressLint("CustomX509TrustManager")
    class TrustManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    class HostVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String?, session: SSLSession?): Boolean = true
    }

}