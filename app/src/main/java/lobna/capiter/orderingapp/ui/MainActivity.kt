package lobna.capiter.orderingapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import lobna.capiter.orderingapp.databinding.ActivityMainBinding
import lobna.capiter.orderingapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private val productsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.productsRecycler.adapter = productsAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.getProducts(this@MainActivity).distinctUntilChanged()
                .collectLatest {
                    productsAdapter.submitData(it)
                }
        }
    }
}