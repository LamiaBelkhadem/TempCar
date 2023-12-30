import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.swe401swe2009867.Car

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tempCar.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, email TEXT,username TEXT, password TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS car ( id INTEGER PRIMARY KEY, imageResId INTEGER, name TEXT, description TEXT, price REAL, mileage INTEGER, fuel TEXT, transmission TEXT, seats INTEGER, available INTEGER)")


        db.execSQL("CREATE TABLE IF NOT EXISTS rental (id INTEGER PRIMARY KEY,user INTEGER, car INTERGER,name TEXT, ic TEXT, duration TEXT, start TEXT, price DOUBLE)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
    fun insertRental( userId: Int, carId: Int,name: String, ic: String, duration: String, startDate: String, price: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("user", userId)
        values.put("car", carId)
        values.put("name", name)
        values.put("ic", ic)
        values.put("duration", duration)
        values.put("start", startDate)
        values.put("price", price)

        db.insert("rental", null, values)
        db.close()
        Log.d("DatabaseHandler", "Rental Created: userId: $userId, carId: $carId Name: $name, IC: $ic, Duration: $duration, Start Date: $startDate, Price: $price")

    }
    fun getUserIdByUsername(username: String): Int? {
        val db = this.readableDatabase
        var userId: Int? = null

        // Query to find the user by username
        val cursor = db.query(

            "user",
            arrayOf("id"),
            "username = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"))
        }

        cursor.close()
        db.close()

        return userId
    }
    fun getCarIdByName(carName: String): Int? {
        val db = this.readableDatabase
        var carId: Int? = null

        // Query to find the car by name
        val cursor = db.query(
            "car", // Table name
            arrayOf("id"), // Columns to return
            "name = ?", // Selection criteria
            arrayOf(carName), // Selection arguments
            null, // Group by
            null, // Having
            null // Order by
        )

        if (cursor.moveToFirst()) {
            carId = cursor.getInt(cursor.getColumnIndex("id"))
        }

        cursor.close()
        db.close()

        return carId
    }
    fun insertCar(car: Car) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("imageResId", car.imageResId)
        values.put("name", car.name)
        values.put("description", car.desc)
        values.put("price", car.price)
        values.put("mileage", car.mileage)
        values.put("fuel", car.fuel)
        values.put("transmission", car.transmission)
        values.put("seats", car.seats)
        values.put("available", if (car.available) 1 else 0)

        db.insert("car", null, values)
        db.close()
    }
    fun getAllCars(): List<Car> {
        val carList = mutableListOf<Car>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM car", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val imageResId = cursor.getInt(cursor.getColumnIndex("imageResId"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))
                val mileage = cursor.getInt(cursor.getColumnIndex("mileage"))
                val fuel = cursor.getString(cursor.getColumnIndex("fuel"))
                val transmission = cursor.getString(cursor.getColumnIndex("transmission"))
                val seats = cursor.getInt(cursor.getColumnIndex("seats"))
                val available = cursor.getInt(cursor.getColumnIndex("available")) == 1

                val car = Car(imageResId, name, description, price, mileage, fuel, transmission, seats, available)
                carList.add(car)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return carList
    }
    fun isCarTableEmpty(): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM car", null)
        if (cursor != null && cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            cursor.close()
            db.close()
            return count == 0
        }
        cursor?.close()
        db.close()
        return true
    }

}
