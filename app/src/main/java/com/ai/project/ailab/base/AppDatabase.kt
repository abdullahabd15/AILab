package com.ai.project.ailab.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ai.project.ailab.interfaces.ProductDao
import com.ai.project.ailab.room.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract var product: ProductDao
}