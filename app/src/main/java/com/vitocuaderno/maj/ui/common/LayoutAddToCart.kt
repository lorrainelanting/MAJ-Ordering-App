package com.vitocuaderno.maj.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.util.CurrencyUtil

class LayoutAddToCart(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val layoutAddToCartListener: LayoutAddToCartListener? = null

    companion object {
        const val ID = "id"
    }

    init {
        inflate(context, R.layout.layout_add_to_cart, this)

        //Custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LayoutAddToCart)

        val productQuantity = findViewById<TextView>(R.id.txtProductQty)
        val btnMinus = findViewById<FrameLayout>(R.id.btnMinus)
        val btnAdd = findViewById<FrameLayout>(R.id.btnAdd)
        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)
        val btnExit = findViewById<ImageView>(R.id.btnExit)
        val frameLayout = findViewById<FrameLayout>(R.id.frameAddToCartLayout)
        val customSnackBar = findViewById<ConstraintLayout>(R.id.clSnackBar)

        productQuantity.text = attributes.getString(R.styleable.LayoutAddToCart_txt_quantity)

        // Consume touch event stop propagation
        customSnackBar.setOnTouchListener { v, event ->  true}

        /**
         * Button event listeners
         * */
        btnMinus.setOnClickListener {
            layoutAddToCartListener?.onMinusBtnClick()
        }

        btnAdd.setOnClickListener {
            layoutAddToCartListener?.onAddBtnClick()
        }

        btnAddToCart.setOnClickListener {
            layoutAddToCartListener?.onAddToCartBtnClick()
        }

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

    fun bind(product: Product) {
        val picasso = Picasso.get()
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val unitProductPrice = findViewById<TextView>(R.id.txtUnitCost)
        val productDescription = findViewById<TextView>(R.id.txtProductDescription)

        picasso.load(product.productImgUrl).placeholder(R.color.colorSecondary).error(R.drawable.ic_soft_drink).into(imgProduct)
        unitProductPrice.text = CurrencyUtil.format(product.productUnitCost)
        productDescription.text = "${product.productDescription} ${product.productPackQty}"
    }

    interface LayoutAddToCartListener {
        fun onMinusBtnClick()

        fun onAddBtnClick()

        fun onAddToCartBtnClick()
    }
}