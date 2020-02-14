package com.ai.project.ailab.room

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.room.Room
import com.ai.project.ailab.R
import com.ai.project.ailab.base.AppDatabase
import com.ai.project.ailab.room.entity.Product
import com.ai.project.libui.AiActivity
import kotlinx.android.synthetic.main.activity_room_example.*

class RoomExampleActivity : AiActivity() {
    private lateinit var dataBase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_room_example)
            initObject()
            initOnClick()
        } catch (e: Exception) {
            showAlertToast(e.message)
        }
    }

    private fun initObject() {
        dataBase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app.db").build()
    }

    private fun initOnClick() {
        btn_insert_product.setOnClickListener {
            val product = setProduct()
            insertProductToDb(product)
        }

        btn_get_product.setOnClickListener {
            ll_insert_product.visibility = View.GONE
            ll_show_product.visibility = View.VISIBLE
        }

        btn_back.setOnClickListener {
            if (ll_insert_product.visibility == View.VISIBLE) {
                ll_insert_product.visibility = View.GONE
                ll_show_product.visibility = View.VISIBLE
            } else {
                ll_show_product.visibility = View.GONE
                ll_insert_product.visibility = View.VISIBLE
            }
        }
    }

    private fun setProduct(): Product {
        val productName = et_product_name.text.toString().trim()
        val brand = et_brand.text.toString().trim()
        val price = et_price.text.toString().trim()
        val product = Product()
        product.apply {
            this.productName = productName
            this.brand = brand
            this.price = price
        }
        return product
    }

    private fun insertProductToDb(product: Product) {
        object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg params: Void?): Boolean {
                dataBase.product.insertProduct(product)
                return true
            }

            override fun onPostExecute(result: Boolean?) {
                if (result!!) {
                    showAlertToast("Hore Berhasil")
                }
            }
        }.execute()
    }
}
