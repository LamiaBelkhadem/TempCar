import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun findUserByUsernameAndPassword(username: String, password: String): User?
    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}
