import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val password: String, // Consider encryption for real applications
    val isAdmin: Boolean,
    val email:String,
)
