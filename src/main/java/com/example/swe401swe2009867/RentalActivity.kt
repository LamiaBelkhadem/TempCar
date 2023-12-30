package com.example.swe401swe2009867

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

class RentalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental)
        val carPrice = intent.getDoubleExtra("carPrice", 0.0)
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val userId = intent.getIntExtra("userId", 0)

        // Initialize views

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val icEditText: EditText = findViewById(R.id.icEditText)
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val textSpinner: EditText = findViewById(R.id.textSpinner)
        val durationSpinner: Spinner = findViewById(R.id.durationSpinner)
        val submitButton: Button = findViewById(R.id.submitRentButton)

        // Set OnClickListener for the Submit button
        submitButton.setOnClickListener {
            // Get user input from views
            val name = nameEditText.text.toString()
            val durationNumber = textSpinner.text.toString().toDoubleOrNull() ?: 0.0
            val ic = icEditText.text.toString()
            val selectedDate =
                calendarView.date
            val selectedDuration = durationSpinner.selectedItem.toString()

            // Validate input
            var isValid = true

            // Check if user name is not empty
            if (name.isEmpty()) {
                nameEditText.error = "Please enter your name"
                isValid = false
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()

            }

            // Check if the email address is valid
            /*if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Please enter a valid email"
                isValid = false
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()}
*/

            if (durationNumber==0.0) {
                textSpinner.error = "Please enter a duration"
                isValid = false
                Toast.makeText(this, "Please enter a duration", Toast.LENGTH_SHORT).show()

            }

            if (isValid) {
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

            }  else {
            // Log or show error to the user
            Log.d("RentalActivity", "Input validation failed")
        }
        }
    }
}
