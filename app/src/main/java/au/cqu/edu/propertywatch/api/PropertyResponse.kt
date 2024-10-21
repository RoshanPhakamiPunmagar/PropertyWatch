package au.cqu.edu.propertywatch.api

import com.google.gson.annotations.SerializedName

class PropertyResponse {
    // PropertyItems field containing a list of PropertyItems fetched from the API
    @SerializedName("property")
    lateinit var propertyItems: List<PropertyItem>
}
