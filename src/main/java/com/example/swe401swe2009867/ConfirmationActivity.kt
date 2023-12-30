package com.example.swe401swe2009867

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userId = intent.getIntExtra("userId", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        val backButton: Button = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", userId)

            startActivity(intent)
        }

    }}