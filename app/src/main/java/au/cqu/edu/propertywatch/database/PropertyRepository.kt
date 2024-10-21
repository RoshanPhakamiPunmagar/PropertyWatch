package au.cqu.edu.propertywatch.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

// Repository class for managing Property entities and database operations
class PropertyRepository private constructor(context: Context) {

    // Room database instance
    private val propertyDatabase: PropertyDatabase = Room.databaseBuilder(
        context.applicationContext, PropertyDatabase::class.java, "property-database"
    ).build()

    // PropertyDao instance
    private val propertyDao = propertyDatabase.propertyDao()

    // Executor for database operations
    private val executor = Executors.newSingleThreadExecutor()

    // Method to retrieve properties from the database
    fun getProperties(): LiveData<List<Property>> = propertyDao.getProperties()

    // Method to save properties to the database
    fun saveProperties(properties: List<Property>) {
        executor.execute {
            propertyDao.saveProperties(properties)
        }
    }

    // Companion object for singleton pattern and loading test data
    companion object {
        @Volatile
        private var INSTANCE: PropertyRepository? = null
        var testDataLoaded = false

        // Initialize the PropertyRepository singleton instance
        fun initialize(context: Context) {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PropertyRepository(context).also { INSTANCE = it }
            }
        }

        // Get the singleton instance of PropertyRepository
        fun get(): PropertyRepository {
            return INSTANCE ?: throw IllegalStateException("PropertyRepository not initialized")
        }

        // Load test data into the database
        fun loadTestData() {
            if (testDataLoaded) return

            val propertyArray: List<Property> = listOf(
                Property(
                    address = "149-153 Bunda Street, Cairns",
                    price = 200000,
                    phone = "0403404111",
                    lat = -16.928893272553427,
                    lon = 145.77021565990813
                ),
                Property(
                    address = "197 Draper Street, Cairns",
                    price = 350000,
                    phone = "0403404222",
                    lat = -16.928893272553427,
                    lon = 145.77021565990813
                ),
                Property(
                    address = "198 Grafton Street, Cairns",
                    price = 800000,
                    phone = "0403404333",
                    lat = -16.916479904985984,
                    lon = 145.76987256094102
                ),
                Property(
                    address = "3 Cominos Place, Cairns",
                    price = 550000,
                    phone = "0403404444",
                    lat = -16.945571,
                    lon = 145.745504
                ),
                Property(
                    address = "McGuigan Street, Earlville",
                    phone = "0403404555",
                    lat = -16.945571,
                    lon = 145.741207
                ),
                Property(
                    address = "6-8 Quigley Street, Manunda",
                    price = 455000,
                    phone = "0403404666",
                    lat = 16.929026,
                    lon = 145.762279
                )
            )

            // Save test data to the database
            get().saveProperties(propertyArray)
            testDataLoaded = true
        }
    }
}
