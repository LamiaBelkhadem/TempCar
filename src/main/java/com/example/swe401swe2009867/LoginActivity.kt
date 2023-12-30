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

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin) // Note: Check the ID
        textViewRegister = findViewById(R.id.textView2)

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
        if (username.equals("ADMIN", ignoreCase = true) && password.equals("ADMIN", ignoreCase = true)) {
            // Redirect to AdminClass
            val adminIntent = Intent(this, AdminHomeActivity::class.java)
            startActivity(adminIntent)
            finish()
            return}


        val dbHelper = DatabaseHandler(this)

        val db = dbHelper.readableDatabase

        val projection = arrayOf("id")

        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query("user", projection, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            val userId= dbHelper.getUserIdByUsername(username)
            intent.putExtra("userId", userId)
            startActivity(intent)

            finish() // Close the LoginActivity
        } else {
            Toast.makeText(applicationContext, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
        db.close()
    }

    private fun navigateToRegister() {
        // Navigate to the RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
