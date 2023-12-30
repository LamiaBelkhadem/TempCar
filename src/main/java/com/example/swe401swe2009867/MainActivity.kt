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

data class Car(
    val imageResId: Int,
    val name: String,
    val desc: String,
    val price: Double,
    val mileage: Int,
    val fuel: String,
    val transmission: String,
    val seats: Int,
    val available: Boolean
)



class MainActivity : AppCompatActivity() {

    private lateinit var carRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rentals: Button = findViewById(R.id.rentalsButton)

        carRecyclerView = findViewById(R.id.carRecyclerView)
        carRecyclerView.layoutManager = LinearLayoutManager(this)

        val userId = intent.getIntExtra("userId", 0)

        val databaseHandler = DatabaseHandler(this)
        if (databaseHandler.isCarTableEmpty()) {
            val carList = listOf(

            Car(R.drawable.car1, "Toyota Corolla","A reliable and fuel-efficient sedan, perfect for city driving.", 55.0, 250, "Petrol", "Automatic", 5, true),
            Car(R.drawable.hondacivic, "Honda Civic","A sporty and compact car known for its durability and smooth ride.", 60.0, 300, "Petrol", "Automatic", 5, false),
            Car(R.drawable.fordmustang, "Ford Mustang", "A classic American muscle car with high performance and iconic design.",120.0, 200, "Petrol", "Manual", 4, true),
            Car(R.drawable.chevrolet, "Chevrolet Camaro", "An iconic sports car offering exhilarating performance and a refined interior.", 130.0, 200, "Petrol", "Manual", 4, true),
            Car(R.drawable.chevrolet, "BMW 3 Series", "A luxury sedan combining performance, comfort, and technology in a sleek package.", 110.0, 300, "Diesel", "Automatic", 5, false),
            Car(R.drawable.mercedes, "Mercedes-Benz C-Class", "Sophisticated and elegantly designed, this car offers a blend of luxury and performance.", 115.0, 300, "Diesel", "Automatic", 5, true),
            Car(R.drawable.audi, "Audi A4", "A perfect blend of luxury and innovation, known for its advanced features and smooth drive.", 100.0, 300, "Diesel", "Automatic", 5, true),
            Car(R.drawable.nissan, "Nissan Altima", "A family-friendly sedan known for its comfort, space, and fuel efficiency.", 58.0, 300, "Petrol", "Automatic", 5, true),
            Car(R.drawable.volks, "Volkswagen Jetta", "A compact sedan that stands out with its comfortable ride and spacious interior.", 55.0, 300, "Diesel", "Automatic", 5, false),
            Car(R.drawable.hyundai, "Hyundai Sonata", "A hybrid car offering a balance of efficiency, style, and high-tech features.", 53.0, 300, "Hybrid", "Automatic", 5, true),
            Car(R.drawable.kia, "Kia Optima", "A sleek sedan with a comfortable interior, known for its safety and reliability.", 50.0, 300, "Petrol", "Automatic", 5, true),
            Car(R.drawable.mazda, "Mazda 6", "Combining style, driving pleasure, and efficiency, this car offers a premium feel.", 59.0, 300, "Petrol", "Automatic", 5, false))

            carList.forEach { car ->
                databaseHandler.insertCar(car)
            }
        }



        val carsFromDatabase = databaseHandler.getAllCars()
        val adapter = CarAdapter(carsFromDatabase, userId)
        carRecyclerView.adapter = adapter

        rentals.setOnClickListener {
            val intent = Intent(this, RentalListActivity::class.java)

            startActivity(intent)
        }
    }

    class CarAdapter(private val carList: List<Car>, private val userId: Int) :
        RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.car_card_item, parent, false)
            return CarViewHolder(view)
        }

        override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
            val car = carList[position]
            holder.carImage.setImageResource(car.imageResId)
            holder.carName.text = car.name
            holder.carPrice.text = "$${car.price}"

            holder.viewCarButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, CarOverview::class.java)
                intent.putExtra("carName", car.name)
                intent.putExtra("desc", car.desc)
                intent.putExtra("price", car.price)
                intent.putExtra("carImageResId", car.imageResId)
                intent.putExtra("mileage", car.mileage)
                intent.putExtra("fuel", car.fuel)

                intent.putExtra("transmission", car.transmission)
                intent.putExtra("seats", car.seats)
                intent.putExtra("isAvailable", car.available)

                intent.putExtra("userId", userId)

                context.startActivity(intent)
            }

        }

        override fun getItemCount(): Int {
            return carList.size
        }

        class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val carImage: ImageView = itemView.findViewById(R.id.carImage)
            val carName: TextView = itemView.findViewById(R.id.carName)
            val carPrice: TextView = itemView.findViewById(R.id.carPrice)
            val viewCarButton: Button = itemView.findViewById(R.id.viewCarButton)
        }
    }
}
