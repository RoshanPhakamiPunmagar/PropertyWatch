package au.cqu.edu.propertywatch.adapters

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import au.cqu.edu.propertywatch.MapsActivity
import au.cqu.edu.propertywatch.PropertyListViewModel
import au.cqu.edu.propertywatch.R
import au.cqu.edu.propertywatch.database.Property

class PropertyAdapter(var properties: List<Property>) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    // ViewModel to manage property details
    private lateinit var mPropertyListViewModel: PropertyListViewModel

    // Inflate the layout for each item of the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        // Initialize PropertyListViewModel
        val viewModelStoreOwner = parent.context as ViewModelStoreOwner
        mPropertyListViewModel =
            ViewModelProvider(viewModelStoreOwner).get(PropertyListViewModel::class.java)

        return ViewHolder(view)
    }

    // Return the size of the dataset
    override fun getItemCount() = properties.size

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }

    // ViewHolder class
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        lateinit var property: Property

        // Views in the item layout
        private val addressView: TextView = view.findViewById(R.id.address)
        private val phoneView: TextView = view.findViewById(R.id.phone)
        private val priceView: TextView = view.findViewById(R.id.price)
        private val emailButton: Button = view.findViewById(R.id.email_button)

        init {
            // Set OnClickListener for item view and email button
            itemView.setOnClickListener(this)
            emailButton.setOnClickListener { sendEmail() }
        }

        // Handle item click event
        override fun onClick(v: View) {
            val intent = Intent(v.context, MapsActivity::class.java)
            intent.putExtra("EXTRA_PROPERTY", property)
            v.context.startActivity(intent)
        }

        // Bind property data to views
        fun bind(property: Property) {
            this.property = property
            addressView.text = property.address
            phoneView.text = property.phone
            priceView.text = property.price.toString()
        }

        // Method to send email
        private fun sendEmail() {
            // Check if address and price are present
            if (property.address.isNullOrEmpty() || property.price == null) {
                Log.e(TAG, "Address or price is missing. Cannot send email.")
                return
            }

            // Create email content
            val emailTitle = view.context.getString(R.string.email_title)
            val emailMessage = view.context.getString(
                R.string.email_message,
                property.address,
                property.price
            )

            // Create email intent
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("rajuphakami2020@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, emailTitle)
                putExtra(Intent.EXTRA_TEXT, emailMessage)
            }
            // Start email activity
            view.context.startActivity(Intent.createChooser(intent, "Send using:"))
        }

    }
}
