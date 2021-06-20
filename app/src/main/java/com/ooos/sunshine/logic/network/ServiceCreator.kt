package com.ooos.sunshine.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> createRetrofit(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    // 泛型实化.使用 ServiceCreator.create<***Service>() 方式创建，简化代码，英语编程。
    inline fun <reified T> create(): T = createRetrofit(T::class.java)
}