package au.cqu.edu.propertywatch

import androidx.lifecycle.ViewModel
import au.cqu.edu.propertywatch.database.PropertyRepository

class PropertyListViewModel : ViewModel() {

    // Reference to the property repository
    private val propertyRepository = PropertyRepository.get()

    // LiveData object holding the list of properties
    val propertyList = propertyRepository.getProperties()
}
