package com.ai.project.ailab.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_product")
class Product: Serializable {
    @PrimaryKey(autoGenerate = true)
    var productId: Int = 0
    @ColumnInfo(name = "productName")
    var productName: String = ""
    @ColumnInfo(name = "brand")
    var brand: String = ""
    @ColumnInfo(name = "price")
    var price: String = ""


}