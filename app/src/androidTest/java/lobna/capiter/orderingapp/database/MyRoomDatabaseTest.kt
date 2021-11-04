package lobna.capiter.orderingapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import lobna.capiter.orderingapp.model.CartModel
import lobna.capiter.orderingapp.model.ProductModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyRoomDatabaseTest : TestCase() {

    private lateinit var cartDao: CartDao
    private lateinit var myRoomDatabase: MyRoomDatabase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room.inMemoryDatabaseBuilder(context, MyRoomDatabase::class.java).build()
        cartDao = myRoomDatabase.cartDao()
    }

    @After
    @Throws(IOException::class)
    fun close() {
        myRoomDatabase.close()
    }

    @Test
    fun insertAndRead() {
        runBlocking {
            val productModel = ProductModel("123", "testItem", 10, "", 1)
            val cartModel = CartModel(productModel)
            cartDao.insert(cartModel)
            val cartItems = cartDao.getAll()
            assert(cartItems.contains(cartModel))
        }
    }

    @Test
    fun onInsertCountIsOne() {
        runBlocking {
            val productModel = ProductModel("123", "testItem", 10, "", 1)
            val cartModel = CartModel(productModel)
            cartDao.insert(cartModel)
            val quantities = cartDao.getQuantity(productModel.id)
            assert(quantities[0] == 1)
        }
    }

    @Test
    fun clearItems() {
        runBlocking {
            val productModel = ProductModel("123", "testItem", 10, "", 1)
            val cartModel = CartModel(productModel)
            cartDao.insert(cartModel)
            cartDao.clear()
            val cartItems = cartDao.getAll()
            assert(cartItems.isEmpty())
        }
    }

}