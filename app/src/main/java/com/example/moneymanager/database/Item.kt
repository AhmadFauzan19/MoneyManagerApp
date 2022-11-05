package com.example.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "activity")
    val activity: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "amount")
    val amount: Double
)

fun Item.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(amount)
