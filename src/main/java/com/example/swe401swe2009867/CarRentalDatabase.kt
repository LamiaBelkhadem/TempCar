import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class CarRentalDatabase : RoomDatabase() {
    abstract fun UserDA0(): UserDAO

    // If you have other entities, define their DAOs here as well
}
