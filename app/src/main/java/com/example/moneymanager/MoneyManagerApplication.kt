package com.example.moneymanager

import android.app.Application
import com.example.moneymanager.database.ItemRoomDatabase

class MoneyManagerApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}