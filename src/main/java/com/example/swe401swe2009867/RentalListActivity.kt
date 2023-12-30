package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
data class Rental(
    val id: Int,
    val userId: Int,
    val carId: Int,
    val name: String,
    val ic: String,
    val duration: String,
    val startDate: String,
    val price: Double
)

class RentalListActivity : AppCompatActivity() {
    private lateinit var rentalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rentals_list_activity)

        rentalRecyclerView = findViewById(R.id.rentalRecyclerView)
        rentalRecyclerView.layoutManager = LinearLayoutManager(this)

        val userId = intent.getIntExtra("userId", 0)
        val databaseHandler = DatabaseHandler(this)
        val rentals = databaseHandler.getAllRentalsByUserId(userId)

        val adapter = RentalAdapter(rentals)
        rentalRecyclerView.adapter = adapter
    }
    class RentalAdapter(private val rentals: List<Rental>) : RecyclerView.Adapter<RentalAdapter.RentalViewHolder>() {

        class RentalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvRentalName: TextView = view.findViewById(R.id.tvRentalName)
            val ivCarImage: ImageView = view.findViewById(R.id.carImage)
            val tvCar: TextView = view.findViewById(R.id.tvCar)
            val tvRentalDate: TextView = view.findViewById(R.id.tvRentalDate)
            val tvDuration: TextView = view.findViewById(R.id.tvDuration)
            val tvRentalPrice: TextView = view.findViewById(R.id.tvRentalPrice)
            val btnEdit: Button = view.findViewById(R.id.edit)
            val btnDelete: Button = view.findViewById(R.id.delete)
            val layoutExpandable: LinearLayout = view.findViewById(R.id.layoutExpandable)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rental_card_item, parent, false)
            return RentalViewHolder(view)
        }

        override fun onBindViewHolder(holder: RentalViewHolder, position: Int) {
            val rental = rentals[position]
            holder.tvRentalName.text = rental.name
            // Bind other views with rental data
            //holder.ivCarImage.setImageResource(rental.imageResId)
            holder.tvRentalDate.text = "Rental Start Date: ${rental.startDate}"
            holder.tvRentalPrice.text = "Rental Price: ${rental.price}"
            holder.tvDuration.text = "Duration: ${rental.duration}"




            // Handle edit and delete button clicks
            holder.btnEdit.setOnClickListener {
                // Handle edit action
            }
            holder.btnDelete.setOnClickListener {
                // Handle delete action
            }
        }

        override fun getItemCount() = rentals.size
    }

}
