package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Spinner
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RentalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental)
        val carPrice = intent.getDoubleExtra("carPrice", 0.0)
        val rentalId = intent.getIntExtra("rentalId", 0)
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val userId = intent.getIntExtra("userId", 0)
        val carId = intent.getIntExtra("carId", 0)


        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val icEditText: EditText = findViewById(R.id.icEditText)
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val textSpinner: EditText = findViewById(R.id.textSpinner)
        val durationSpinner: Spinner = findViewById(R.id.durationSpinner)
        val submitButton: Button = findViewById(R.id.submitRentButton)

        submitButton.setOnClickListener {
            // Get user input from views
            val name = nameEditText.text.toString()
            val durationNumber = textSpinner.text.toString().toDoubleOrNull() ?: 0.0
            val ic = icEditText.text.toString()
            val selectedDate =
                calendarView.date
            val selectedDuration = durationSpinner.selectedItem.toString()

            var isValid = true

            if (name.isEmpty()) {
                nameEditText.error = "Please enter your name"
                isValid = false
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()

            }



            if (durationNumber==0.0) {
                textSpinner.error = "Please enter a duration"
                isValid = false
                Toast.makeText(this, "Please enter a duration", Toast.LENGTH_SHORT).show()

            }
            if (isValid) {
                val databaseHandler = DatabaseHandler(this)
                val carId = 1//databaseHandler.getCarIdByName(carName)
                if (rentalId != null) {
                    Log.d("user",userId.toString())
                    Log.d("car",carId.toString())
                }
                else{
                    Log.d("no user or car", userId.toString())
                }
                if (rentalId != 0) {
                    val car=databaseHandler.getCarById(carId)

                    var rentPrice = 0.0

                    if (selectedDuration == "days") {
                        if (car != null) {
                            rentPrice = durationNumber * car.price
                        }
                    } else if (selectedDuration == "months") {
                        if (car != null) {
                            rentPrice = durationNumber * car.price * 30
                        }
                    } else if (selectedDuration == "weeks") {
                        if (car != null) {
                            rentPrice = durationNumber * car.price * 7
                        }
                    }

                    val tax = (rentPrice * 0.07).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    val totalPrice = rentPrice + tax
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateString = dateFormat.format(Date(selectedDate))

                    val success = databaseHandler.updateRental(
                        rentalId,
                        userId,
                        carId,
                        name,
                        ic,
                        "${durationNumber.toString()} ${selectedDuration.toString()}",
                        dateString,
                        totalPrice
                    )
                    if (success) {
                        Toast.makeText(this, "Rental updated successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userId", userId)

                        startActivity(intent)


                    } else {
                        Toast.makeText(this, "Failed to update rental", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val intent = Intent(this, RentalInfoActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("durationNumber", durationNumber)
                    intent.putExtra("ic", ic)
                    intent.putExtra("date", selectedDate)
                    intent.putExtra("selectedDuration", durationSpinner.selectedItem.toString())
                    intent.putExtra("carPrice", carPrice)
                    intent.putExtra("carName", carName)
                    intent.putExtra("userId", userId)

                    startActivity(intent)
                }
            } else {
                Log.d("RentalActivity", "Input validation failed")
            }
        }



    }
}
