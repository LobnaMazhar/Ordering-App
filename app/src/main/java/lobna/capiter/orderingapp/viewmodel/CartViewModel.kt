package lobna.capiter.orderingapp.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lobna.capiter.orderingapp.model.CartModel
import lobna.capiter.orderingapp.repository.CartRepository

class CartViewModel : ViewModel() {

    val isEmptyObservable = ObservableBoolean(false)
    val isOrderDoneObservable = ObservableBoolean(false)

    private val _data = MutableLiveData<List<CartModel>?>()
    val data: LiveData<List<CartModel>?>
        get() = _data

    val backClick = MutableLiveData<Boolean>()

    fun getCartData(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = CartRepository.getAll(context)
            _data.postValue(response)
        }
    }

    fun makeOrder(view: View) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = CartRepository.clear(view.context)
            _data.postValue(null)
            isOrderDoneObservable.set(true)
        }
    }

    fun backToHome(view: View) {
        backClick.postValue(true)
    }

    fun setEmpty(isEmpty: Boolean) {
        isEmptyObservable.set(isEmpty)
    }
}