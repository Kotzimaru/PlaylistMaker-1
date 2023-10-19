package com.example.playlistmaker1.search.data.network

class RetrofitNetworkClient(
    private val searchApi: SearchApi,
    private val validator: InternetConnectionValidator,
) : NetworkClient {

    override suspend fun doRequest(query: String): Response {

        return if (!validator.isConnected()) {
            Response().apply { resultCode = -1 }

        } else {
            val response = try {
                searchApi.search(query)
            } catch (e: Throwable) {
                null
            }

            val result = response?.body()
            result?.apply { resultCode = response.code() } ?: Response().apply { resultCode = 400 }
        }
    }
}
