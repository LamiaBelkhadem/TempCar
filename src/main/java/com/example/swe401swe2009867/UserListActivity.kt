package com.example.swe401swe2009867

import DatabaseHandler
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
data class User(
    val id: Int,
    val email: String,
    val username: String,
    val password: String
)


class UserListActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)
        val backbtn: Button = findViewById(R.id.back)


        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)


        val databaseHandler = DatabaseHandler(this)
        val users = databaseHandler.getAllUsers()

        val adapter = UserAdapter(users,databaseHandler)
        userRecyclerView.adapter = adapter


        backbtn.setOnClickListener {
            val intent = Intent(this, AdminHomeActivity::class.java)

            startActivity(intent)
        }
    }
    class UserAdapter(private val users: List<User>,private val dbHandler: DatabaseHandler) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvUserName  :TextView = view.findViewById(R.id.tvUserName)
            val tvName  :TextView = view.findViewById(R.id.tvName)
            val tvEmail: TextView = view.findViewById(R.id.tvEmail)
            val tvPassword: TextView = view.findViewById(R.id.tvPassword)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card_item, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users[position]

            holder.tvName.text="Name: ${user.username}"
            holder.tvUserName.text =user.username
            holder.tvEmail.text = "Email: ${user.email}"
            holder.tvPassword.text = "Rental Price: ${user.password}"

        }

        override fun getItemCount() = users.size
    }

}
