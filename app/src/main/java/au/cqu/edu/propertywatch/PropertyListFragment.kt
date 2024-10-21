package au.cqu.edu.propertywatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.cqu.edu.propertywatch.adapters.PropertyAdapter
import au.cqu.edu.propertywatch.database.Property
import au.cqu.edu.propertywatch.database.PropertyRepository

class PropertyListFragment : Fragment() {

    // List of properties to be displayed
    private val propertyList: MutableList<Property> = mutableListOf()

    // ViewModel for managing the property list
    private lateinit var mPropertyListViewModel: PropertyListViewModel

    // ViewModel for managing property items received from PropertyWatchr
    private lateinit var mPropertyWatchrViewModel: PropertyWatchrViewModel

    // Adapter for the RecyclerView
    private lateinit var mAdapter: PropertyAdapter

    // Companion object to create new instances of PropertyListFragment
    companion object {
        fun newInstance() = PropertyListFragment()
    }

    // Called when the fragment is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize ViewModel instances
        val context = activity as ViewModelStoreOwner
        mPropertyListViewModel = ViewModelProvider(context).get(PropertyListViewModel::class.java)
        mPropertyWatchrViewModel = ViewModelProvider(context).get(PropertyWatchrViewModel::class.java)
    }

    // Called to create the fragment view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Initialize RecyclerView and its layout manager
        val recyclerView = view.findViewById<RecyclerView>(R.id.property_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize and set the adapter for RecyclerView
        mAdapter = PropertyAdapter(propertyList)
        recyclerView.adapter = mAdapter

        // Observe changes in the property list from PropertyListViewModel
        mPropertyListViewModel.propertyList.observe(
            viewLifecycleOwner, Observer { newPropertyList ->

                // Load test data if the property list is empty
                if (propertyList.isEmpty()) {
                    PropertyRepository.loadTestData()
                    return@Observer
                }

                // Update the property list with new properties
                propertyList.clear()
                propertyList.addAll(newPropertyList)
                mAdapter.notifyDataSetChanged()

                // Save the properties to the database
                PropertyRepository.get().saveProperties(ArrayList(newPropertyList))
            }
        )

        // Observe changes in the property items from PropertyWatchrViewModel
        mPropertyWatchrViewModel.propertyItemLiveData.observe(
            viewLifecycleOwner, Observer { propertyItems ->
                // Loop through the list of property items and add them to the property list
                propertyItems.forEach { propertyItem ->
                    val property = Property(
                        address = propertyItem.address,
                        price = propertyItem.price,
                        phone = propertyItem.phone,
                        lat = propertyItem.lat,
                        lon = propertyItem.lon
                    )
                    propertyList.add(property)
                }

                // Notify adapter of data change
                mAdapter.notifyDataSetChanged()

                // Save the properties to the database
                PropertyRepository.get().saveProperties(ArrayList(propertyList))
            }
        )

        return view
    }
}
