package au.cqu.edu.propertywatch.api

import retrofit2.Call
import retrofit2.http.GET

interface WatchrApi {

    // Define a GET request to fetch properties from the API
    @GET("properties.json")
    fun fetchProperties(): Call<WatchrResponse>
}
