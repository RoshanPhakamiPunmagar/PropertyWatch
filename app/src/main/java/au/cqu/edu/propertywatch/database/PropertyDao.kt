package au.cqu.edu.propertywatch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    fun getProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id=(:id)")
    fun getProperty(id: String): LiveData<Property?>

    @Update
    fun updateProperty(property: Property)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProperty(property: Property)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProperties(properties: List<Property>)
}
