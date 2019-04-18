package ru.sshex.exchange.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.sshex.exchange.data.dto.CurrencyListResponse

interface Api {
    @GET("latest")
    fun getCurrencyRate(@Query("base") currency: String): Observable<CurrencyListResponse>
}