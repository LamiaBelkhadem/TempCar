package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RentalsAllActivity : AppCompatActivity() {
    private lateinit var rentalAllRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rental_all_list_activity)

        rentalAllRecyclerView = findViewById(R.id.rentalAllRecyclerView)
        rentalAllRecyclerView.layoutManager = LinearLayoutManager(this)


        val databaseHandler = DatabaseHandler(this)
        val rentals = databaseHandler.getAllRentals()

        val adapter = RentalAllAdapter(rentals,databaseHandler)
        rentalAllRecyclerView.adapter = adapter
    }
    class RentalAllAdapter(private val rentals: List<Rental>,private val dbHandler: DatabaseHandler) : RecyclerView.Adapter<RentalAllAdapter.RentalViewHolder>() {

        class RentalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvUser  :TextView = view.findViewById(R.id.tvUser)
            val tvRentalName: TextView = view.findViewById(R.id.tvRentalName)
            val ivCarImage: ImageView = view.findViewById(R.id.carImage)
            val tvCar: TextView = view.findViewById(R.id.tvCar)
            val tvRentalDate: TextView = view.findViewById(R.id.tvRentalDate)
            val tvDuration: TextView = view.findViewById(R.id.tvDuration)
            val tvRentalPrice: TextView = view.findViewById(R.id.tvRentalPrice)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rental_all_card_item, parent, false)
            return RentalViewHolder(view)
        }

        override fun onBindViewHolder(holder: RentalViewHolder, position: Int) {
            val rental = rentals[position]
            val car=dbHandler.getCarById(rental.carId)
            if (car != null) {

                holder.tvCar.text = "Car: ${car.name}"
                holder.ivCarImage.setImageResource(car.imageResId)

            }
            holder.tvUser.text="User: ${rental.userId}"
            holder.tvRentalName.text = rental.name
            holder.tvRentalDate.text = "Rental Start Date: ${rental.startDate}"
            holder.tvRentalPrice.text = "Rental Price: ${rental.price}"
            holder.tvDuration.text = "Duration: ${rental.duration}"
        }

        override fun getItemCount() = rentals.size
    }

}
