package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.selects.select
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RentalInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_info)
        val backButton: Button = findViewById(R.id.backButton)
        val confirmButton: Button = findViewById(R.id.confirmButton)
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val carPrice = intent.getDoubleExtra("carPrice",0.0)
        val userId = intent.getIntExtra(
            "userId",0)

        val name = intent.getStringExtra("name") ?: "N/A"
        val ic = intent.getStringExtra("ic") ?: "N/A"
        val durationNumber = intent.getDoubleExtra("durationNumber", 0.0)
        val selectedDuration = intent.getStringExtra("selectedDuration")?: "N/A"
        val date = intent.getLongExtra("date", 0L)

        var rentPrice = 0.0
        if (selectedDuration == "days") {
            rentPrice = durationNumber * carPrice
        } else if (selectedDuration == "months") {
            rentPrice = durationNumber * carPrice * 30
        } else if (selectedDuration == "weeks") {
            rentPrice = durationNumber * carPrice * 7
        }

        val tax = (rentPrice * 0.07).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
        val totalPrice = rentPrice + tax

        val nametv= findViewById<TextView>(R.id.name)
        val emailtv = findViewById<TextView>(R.id.email)
        val datetv = findViewById<TextView>(R.id.date)
        val durationtv = findViewById<TextView>(R.id.duration)
        val rentPricetv = findViewById<TextView>(R.id.rentalPrice)
        val taxtv = findViewById<TextView>(R.id.tax)
        val totalPricetv = findViewById<TextView>(R.id.total)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(Date(date))
        datetv.text = dateString


        nametv.text = name
        emailtv.text=ic
        durationtv.text= "${durationNumber.toString()} ${selectedDuration.toString()}"
        rentPricetv.text="${rentPrice.toString()} $"
        taxtv.text="${tax.toString()} $"
        totalPricetv.text="${totalPrice.toString()} $"


        val confirmationCheckBox: CheckBox = findViewById(R.id.confirmationCheckBox)
        Log.d("RentalInfoActivity", "Received car price: $carPrice")

        // Initially disable the button
        confirmButton.isEnabled = false

        // Listener for checkbox changes
        confirmationCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Enable the button when the checkbox is checked
            confirmButton.isEnabled = isChecked

        }

        backButton.setOnClickListener {
            val intent = Intent(this, RentalActivity::class.java)

            startActivity(intent)
        }

        confirmButton.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)
            val carId = databaseHandler.getCarIdByName(carName)
            Log.d("carIDDDD:",carId.toString())
            Log.d(userId.toString(), carId.toString())
            if(carId!=null &&userId!=null) {
    // Insert the rental into the database
    databaseHandler.insertRental(
        userId,
        carId,
        name,
        ic,
        "${durationNumber.toString()} ${selectedDuration.toString()}",
        dateString,
        totalPrice
    )
}
            // Start the ConfirmationActivity
            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra("userId", userId)

            startActivity(intent)
        }


    }}