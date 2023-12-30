package com.example.swe401swe2009867
import android.os.Bundle
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

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Set up click listeners
        buttonRegister.setOnClickListener { registerUser() }
      //  buttonLogin.setOnClickListener { navigateToLogin() }
    }

    private fun registerUser() {
        val username = editTextUsername.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val user = User(username = username, email = email, password = password, isAdmin = false)

        // Insert user into database
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseClient.getDatabase(applicationContext).UserDAO().insertUser(user)
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()

        Toast.makeText(this, "Registration logic not implemented", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        Toast.makeText(this, "Login navigation not implemented", Toast.LENGTH_SHORT).show()
    }
}
