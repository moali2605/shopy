package com.example.e_commerce.model.pojo.customer_order_response

data class TotalShippingPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)