package au.cqu.edu.propertywatch

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import au.cqu.edu.propertywatch.api.PropertyItem
import au.cqu.edu.propertywatch.api.PropertyResponse
import au.cqu.edu.propertywatch.api.WatchrApi
import au.cqu.edu.propertywatch.api.WatchrResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WatchrFetchr {

    private val watchrApi: WatchrApi

    init {
        // Initialize Retrofit instance and WatchrApi interface
        val retrofit: Retrofit = Retrofit.Builder()
            // Base URL of the API
            .baseUrl("http://jellymud.com/api/")
            // Use Gson for JSON parsing
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        watchrApi = retrofit.create(WatchrApi::class.java)
    }

    // Fetch property items from the API and return LiveData object holding the list of property items
    fun fetchProperties(): LiveData<List<PropertyItem>> {
        val responseLiveData: MutableLiveData<List<PropertyItem>> = MutableLiveData()
        // Create API call
        val request: Call<WatchrResponse> = watchrApi.fetchProperties()

        request.enqueue(object : Callback<WatchrResponse> {

            // Handle API call failure
            override fun onFailure(call: Call<WatchrResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch properties", t)
            }

            // Handle API call success
            override fun onResponse(
                call: Call<WatchrResponse>,
                response: Response<WatchrResponse>
            ) {
                Log.d(TAG, "Response received")
                // Get response body
                val response: WatchrResponse? = response.body()
                // Extract PropertyResponse from WatchrResponse
                val propertyResponse: PropertyResponse? = response?.properties
                // Get property items from PropertyResponse
                val propertyItems: List<PropertyItem> = propertyResponse?.propertyItems
                    ?: mutableListOf()

                responseLiveData.value = propertyItems
            }
        })

        // Return LiveData object
        return responseLiveData
    }
}
