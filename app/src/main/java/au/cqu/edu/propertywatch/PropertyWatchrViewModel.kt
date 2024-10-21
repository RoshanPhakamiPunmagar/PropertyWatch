package au.cqu.edu.propertywatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import au.cqu.edu.propertywatch.api.PropertyItem

class PropertyWatchrViewModel : ViewModel() {

    // LiveData object holding the list of property items
    val propertyItemLiveData: LiveData<List<PropertyItem>>

    // Initialize propertyItemLiveData by fetching property items from the API
    init {
        propertyItemLiveData = WatchrFetchr().fetchProperties()
    }
}
