package com.example.swe401swe2009867

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView

class CarOverview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_overview)
        val rentCarButton: Button = findViewById(R.id.rentCarButton)
        val backButton: Button = findViewById(R.id.backButton)

        // Retrieve data from the intent
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val desc = intent.getStringExtra("desc") ?: "N/A"
        val carPrice = intent.getDoubleExtra("price", 0.0)
        val carImageResId = intent.getIntExtra("carImageResId", 0) //
        val mileage = intent.getIntExtra("mileage", 0)
        val fuel = intent.getStringExtra("fuel") ?: "N/A"
        val transmission = intent.getStringExtra("transmission") ?: "N/A"
        val seats = intent.getIntExtra("seats", 0)
        val isAvailable = intent.getBooleanExtra("isAvailable", false)

        // Initialize the views
        val carImageView = findViewById<ShapeableImageView>(R.id.carImageView)
        val carDescTextView = findViewById<TextView>(R.id.carDescTextView)
        val carNameTextView = findViewById<TextView>(R.id.carNameTextView)
        val carPriceChip = findViewById<TextView>(R.id.carPriceTextView)
        val mileageLimitTextView = findViewById<TextView>(R.id.mileageLimitTextView)
        val fuelTypeTextView = findViewById<TextView>(R.id.fuelTypeTextView)
        val transmissionTypeTextView = findViewById<TextView>(R.id.transmissionTypeTextView)
        val numberOfSeatsTextView = findViewById<TextView>(R.id.numberOfSeatsTextView)
        val availabilityStatusTextView = findViewById<TextView>(R.id.availabilityStatusTextView)

        // Set data to views
        carImageView.setImageResource(carImageResId)
        carNameTextView.text = carName
        carDescTextView.text = desc
        carPriceChip.text = getString(R.string.price, carPrice)
        mileageLimitTextView.text = getString(R.string.mileage, mileage)
        fuelTypeTextView.text = getString(R.string.fuel, fuel)
        transmissionTypeTextView.text = getString(R.string.transmission, transmission)
        numberOfSeatsTextView.text = getString(R.string.seats, seats)
        availabilityStatusTextView.text =
        getString(R.string.availability, if (isAvailable) "Available" else "Not Available")
        rentCarButton.setOnClickListener {
            val intent = Intent(this, RentalActivity::class.java)
            intent.putExtra("carPrice", carPrice)
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}
