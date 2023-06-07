package com.bangkit.rentalbiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("tersedia")
    val tersedia: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga")
    val harga: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("jumlahSewa")
    val jumlahSewa: Int
)