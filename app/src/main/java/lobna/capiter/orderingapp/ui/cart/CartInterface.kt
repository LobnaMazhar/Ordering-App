package lobna.capiter.orderingapp.ui.cart

import lobna.capiter.orderingapp.model.CartModel

interface CartInterface {
    fun itemDeleted(cart: CartModel)
}