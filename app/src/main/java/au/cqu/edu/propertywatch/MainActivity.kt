package au.cqu.edu.propertywatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.cqu.edu.propertywatch.database.PropertyRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the PropertyRepository singleton instance
        PropertyRepository.initialize(this)

        // Set the layout for the activity
        setContentView(R.layout.activity_main)

        // Replace the fragment container with PropertyListFragment if savedInstanceState is null
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PropertyListFragment.newInstance())
                .commitNow()
        }

    }
}
