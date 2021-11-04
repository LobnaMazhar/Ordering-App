package lobna.capiter.orderingapp.viewmodel

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @Before
    fun setupViewModel() {
        cartViewModel = CartViewModel()
    }

    @Test
    fun setEmpty_True() {
        cartViewModel.setEmpty(true)
        assertTrue(cartViewModel.isEmptyObservable.get())
    }

    @Test
    fun setEmpty_False() {
        cartViewModel.setEmpty(false)
        assertFalse(cartViewModel.isEmptyObservable.get())
    }
}