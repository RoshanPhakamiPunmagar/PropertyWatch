package au.cqu.edu.propertywatch.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ Property::class ], version=1, exportSchema = false)
abstract class PropertyDatabase : RoomDatabase() {

    // Abstract method to retrieve PropertyDao for database operations
    abstract fun propertyDao(): PropertyDao

}

