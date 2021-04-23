package com.vitocuaderno.maj.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.vitocuaderno.maj.R

class LayoutAddToCart(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    init {
        val layoutId = R.layout.layout_add_to_cart

        inflate(context, layoutId, this)

        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val unitProductPrice = findViewById<TextView>(R.id.txtUnitCost)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LayoutAddToCart)
        imgProduct.setImageDrawable(attributes.getDrawable(R.styleable.LayoutAddToCart_image))
        unitProductPrice.text = attributes.getString(R.styleable.LayoutAddToCart_txtUnitPrice)
        attributes.recycle()
    }
}