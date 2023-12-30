package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RentalListActivity : AppCompatActivity() {

    private lateinit var rentalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rentals_list_activity)

        rentalRecyclerView = findViewById(R.id.rentalRecyclerView)
        rentalRecyclerView.layoutManager = LinearLayoutManager(this)

        val userId = intent.getIntExtra("userId", 0)

        val databaseHandler = DatabaseHandler(this)


    }

}
