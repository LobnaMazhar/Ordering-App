package lobna.capiter.orderingapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import lobna.capiter.orderingapp.R
import lobna.capiter.orderingapp.databinding.ActivityMainBinding
import lobna.capiter.orderingapp.preferences.ConfigurationFile
import lobna.capiter.orderingapp.ui.BaseActivity
import lobna.capiter.orderingapp.ui.cart.CartActivity
import lobna.capiter.orderingapp.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private val productsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.productsRecycler.adapter = productsAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.getProducts(this@MainActivity).distinctUntilChanged()
                .collectLatest { productsAdapter.submitData(it) }
        }
    }

    /**
     * Overridden method responsible for the creation of the menu
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    /**
     * Overridden method responsible for selecting any item from the menu
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart -> {
                Intent(this, CartActivity::class.java).run { startActivity(this) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        productsAdapter.notifyDataSetChanged()
    }
}