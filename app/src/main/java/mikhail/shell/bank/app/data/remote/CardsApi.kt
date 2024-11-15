package mikhail.shell.bank.app.data.remote

import retrofit2.http.GET

interface CardsApi {
    @GET("cards")
    suspend fun getCards()
}