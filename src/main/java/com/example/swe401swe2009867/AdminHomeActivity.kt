package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val rentalsbtn: Button = findViewById(R.id.rentals)
        val usersbtn: Button = findViewById(R.id.users)
        val logoutbtn: Button = findViewById(R.id.logout)



        rentalsbtn.setOnClickListener {
            val intent = Intent(this, RentalsAllActivity::class.java)
            startActivity(intent)
        }

        logoutbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        usersbtn.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)

            startActivity(intent)
        }
    }}
