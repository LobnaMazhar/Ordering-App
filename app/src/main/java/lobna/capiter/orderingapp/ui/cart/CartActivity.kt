package lobna.capiter.orderingapp.ui.cart

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import lobna.capiter.orderingapp.databinding.ActivityCartBinding
import lobna.capiter.orderingapp.model.CartModel
import lobna.capiter.orderingapp.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var activityCartBinding: ActivityCartBinding
    private val cartViewModel by viewModels<CartViewModel>()

    private val items = ArrayList<CartModel>()
    private val cartAdapter = CartAdapter(items, object : CartInterface {
        override fun itemDeleted(cart: CartModel) {
            deleteItem(cart)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(activityCartBinding.root)
        activityCartBinding.cvm = cartViewModel

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initCart()

        cartViewModel.backClick.observe(this, { onBackPressed() })
    }

    private fun initCart() {
        activityCartBinding.cartRecycler.adapter = cartAdapter
        cartViewModel.data.observe(this, {
            if (it.isNullOrEmpty())
                items.clear()
            else items.addAll(it)
            cartAdapter.notifyDataSetChanged()
            cartViewModel.setEmpty(items.isNullOrEmpty())
        })
        cartViewModel.getCartData(this)
    }

    fun deleteItem(cart: CartModel) {
        items.remove(cart)
        cartAdapter.notifyDataSetChanged()
        cartViewModel.setEmpty(items.isNullOrEmpty())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}