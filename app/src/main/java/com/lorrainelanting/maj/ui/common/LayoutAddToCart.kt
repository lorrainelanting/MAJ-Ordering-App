package com.lorrainelanting.maj.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil

class LayoutAddToCart(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var layoutAddToCartListener: LayoutAddToCartListener? = null

    companion object {
        const val ID = "id"
    }

    init {
        inflate(context, R.layout.layout_add_to_cart, this)

        //Custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LayoutAddToCart)

        val btnExit = findViewById<ImageView>(R.id.btnExit)
        val frameLayout = findViewById<FrameLayout>(R.id.frameAddToCartLayout)
        val customSnackBar = findViewById<ConstraintLayout>(R.id.clSnackBar)

        // Consume touch event stop propagation
        customSnackBar.setOnTouchListener { v, event ->  true}

        /**
         * Close events
         * */
        frameLayout.setOnClickListener{
            close()
        }

        btnExit.setOnClickListener {
            close()
        }

        attributes.recycle()
    }

    private fun close() {
        this.isVisible = false
    }

    fun bind(product: Product, quantity:Int) {
        val picasso = Picasso.get()
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val unitProductPrice = findViewById<TextView>(R.id.txtUnitCost)
        val productDescription = findViewById<TextView>(R.id.txtProductDescription)
        val tvQuantity = findViewById<TextView>(R.id.txtProductQty)
        val btnMinus = findViewById<FrameLayout>(R.id.btnMinus)
        val btnAdd = findViewById<FrameLayout>(R.id.btnAdd)
        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)

        picasso.load(product.imgUrl).placeholder(R.color.colorSecondary).error(R.drawable.ic_soft_drink).into(imgProduct)
        unitProductPrice.text = CurrencyUtil.format(product.price)
        productDescription.text = "${product.description} ${product.packQty}"
        tvQuantity.text = quantity.toString()

        /**
         * Button event listeners
         * */
        btnMinus.setOnClickListener {
            layoutAddToCartListener?.onMinusBtnClick(product)
        }

        btnAdd.setOnClickListener {
            layoutAddToCartListener?.onAddBtnClick(product)
        }

        btnAddToCart.setOnClickListener {
            layoutAddToCartListener?.onAddToCartBtnClick(product)
        }
    }

    fun setLayoutAddToCartListener(listener: LayoutAddToCartListener) {
        this.layoutAddToCartListener = listener
    }

    interface LayoutAddToCartListener {
        fun onMinusBtnClick(product: Product)

        fun onAddBtnClick(product: Product)

        fun onAddToCartBtnClick(product: Product)
    }
}