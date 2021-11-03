package lobna.capiter.orderingapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import lobna.capiter.orderingapp.model.CartModel

@Dao
interface CartDao {

    @Insert
    fun insert(cart: CartModel)

    @Query("UPDATE CartModel SET quantity=:quantity WHERE id=:id")
    fun updateItem(id: String, quantity: Int)

    @Query("SELECT quantity FROM CartModel WHERE :id=id")
    fun getQuantity(id: String): List<Int>

    @Query("SELECT * FROM CartModel")
    fun getAll(): List<CartModel>

    @Query("DELETE FROM CartModel WHERE :id=id")
    fun deleteItem(id: String)

    @Query("DELETE FROM CartModel")
    fun clear()
}