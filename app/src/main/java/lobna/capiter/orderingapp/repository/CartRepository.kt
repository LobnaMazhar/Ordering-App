package lobna.capiter.orderingapp.repository

import android.content.Context
import lobna.capiter.orderingapp.database.MyRoomDatabase
import lobna.capiter.orderingapp.model.CartModel

object CartRepository {

    suspend fun insertItem(context: Context, cart: CartModel) {
        MyRoomDatabase.invoke(context).cartDao().insert(cart)
    }

    suspend fun updateItem(context: Context, id: String, quantity: Int) {
        MyRoomDatabase.invoke(context).cartDao().updateItem(id, quantity)
    }

    suspend fun getQuantity(context: Context, id: String): List<Int> {
        return MyRoomDatabase.invoke(context).cartDao().getQuantity(id)
    }

    suspend fun getAll(context: Context): List<CartModel> {
        return MyRoomDatabase.invoke(context).cartDao().getAll()
    }

    suspend fun deleteItem(context: Context, id: String) {
        MyRoomDatabase.invoke(context).cartDao().deleteItem(id)
    }

    suspend fun clear(context: Context) {
        MyRoomDatabase.invoke(context).cartDao().clear()
    }
}