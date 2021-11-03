package lobna.capiter.orderingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lobna.capiter.orderingapp.model.CartModel

@Database(entities = [CartModel::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: MyRoomDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: getInstance(context).also { instance = it }
        }

        private fun getInstance(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, MyRoomDatabase::class.java, "cart_products"
            ).build()

    }

    abstract fun cartDao(): CartDao
}