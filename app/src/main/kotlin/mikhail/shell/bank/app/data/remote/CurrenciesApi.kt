package mikhail.shell.bank.app.data.remote

import mikhail.shell.bank.app.data.dto.CurrencyDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CurrenciesApi {
    @GET("{apiKey}/latest/{from}")
    fun fetchCurrencies(
        @Header("Authorization") auth: String,
        @Path("apiKey") apiKey: String,
        @Path("from") from: String
    ): Call<CurrencyDto>
}