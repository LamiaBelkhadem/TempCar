package com.example.swe401swe2009867
import DatabaseHandler
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonRegister.setOnClickListener { registerUser() }
        buttonLogin.setOnClickListener { navigateToLogin() }
    }

    private fun registerUser() {
        val username = editTextUsername.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = DatabaseHandler(this)

        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put("username", username)
        values.put("email", email)
        values.put("password", password)

        val newRowId = db.insert("user", null, values)

        if (newRowId != -1L) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

            navigateToLogin()
        } else {
            // Registration failed
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    private fun navigateToLogin() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
