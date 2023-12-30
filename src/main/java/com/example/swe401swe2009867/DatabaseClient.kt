import android.content.Context
import androidx.room.Room

object DatabaseClient {
    @Volatile
    private var INSTANCE: CarRentalDatabase? = null

    fun getDatabase(context: Context): CarRentalDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CarRentalDatabase::class.java,
                "my-database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
