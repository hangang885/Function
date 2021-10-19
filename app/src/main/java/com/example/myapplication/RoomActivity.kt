package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        var newUser = User("한강","22","010-3151-5947")

        //싱글톤 패턴을 사용 안한 경우
/*        val db =Room.databaseBuilder(
            applicationContext,
            Appdatabase::class.java,
            "user-database"
        ).allowMainThreadQueries() // 강제로 실행
        .build()
        db.UserDao().insert(newUser)

        */
        //싱글톤 패턴을 사용한 경우
        val db = UserDatabase.getInstance(applicationContext)
       CoroutineScope(Dispatchers.IO).launch {  //코루틴 사용
           db!!.userDao().insert(newUser)
           var d = db!!.userDao().getAll()
           Log.d("Select * From User",d.toString())

       }


    }
}

@Entity
data class User(
    var name:String,
    var age: String,
    var phone: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

@Dao
interface UserDao{

    @Query("Select * From User")
    fun getAll() : List<User>

    @Query("Delete from user where name=:name ")
    fun deleteUserByName(name:String)


    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao():UserDao

    companion object{
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase?{
            if(instance == null){
                synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user-database"
                    ).build()
                }
            }

            return instance
        }

    }

}