package au.cqu.edu.propertywatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import au.cqu.edu.propertywatch.database.Property

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    // GoogleMap instance
    private lateinit var mMap: GoogleMap

    // Property object to display on the map
    private lateinit var property: Property

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Retrieve property data from intent extras
        if (intent != null && intent.hasExtra("EXTRA_PROPERTY")) {
            property = intent.getSerializableExtra("EXTRA_PROPERTY") as Property
        } else {
            // If property data is not available, finish the activity
            finish()
            return
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Called when the map is ready to be used
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Create Latitude and Longitude object for the property location
        val propertyLocation = LatLng(property.lat, property.lon)
        // Add marker for the property location with title and price snippet
        mMap.addMarker(
            MarkerOptions()
                .position(propertyLocation)
                .title(property.address)
                .snippet("Price: \$${property.price}")
        )
        // Move camera to the property location with zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 12f))
    }
}
