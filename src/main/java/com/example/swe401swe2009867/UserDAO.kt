import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    fun getUserByUsername(username: String): User?

    // Add other database operations as needed
}
