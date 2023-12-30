package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin) // Note: Check the ID
        textViewRegister = findViewById(R.id.textView2)

        // Set up click listeners
        buttonLogin.setOnClickListener { loginUser() }
        textViewRegister.setOnClickListener { navigateToRegister() }
    }

    private fun loginUser() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }



        // Create an instance of your DatabaseHandler
        val dbHelper = DatabaseHandler(this)

        // Get a readable database
        val db = dbHelper.readableDatabase

        // Define the columns you want to retrieve
        val projection = arrayOf("id")

        // Define the selection criteria
        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)

        // Query the 'user' table for the given username and password
        val cursor = db.query("user", projection, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            // User found, proceed to login
            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()

            // Optionally, you can navigate to the main activity
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            val userId= dbHelper.getUserIdByUsername(username)
            intent.putExtra("userId", userId)
            startActivity(intent)

            finish() // Close the LoginActivity
        } else {
            // User not found or password incorrect
            Toast.makeText(applicationContext, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }

        // Close the cursor and the database when done
        cursor.close()
        db.close()
    }

    private fun navigateToRegister() {
        // Navigate to the RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
