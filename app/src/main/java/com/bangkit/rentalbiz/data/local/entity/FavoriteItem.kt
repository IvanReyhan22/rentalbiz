package com.bangkit.rentalbiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite")
data class FavoriteItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("persyaratan")
    val persyaratan: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("tersedia")
    val tersedia: String,

    @field:SerializedName("kategori")
    val kategori: String,

    @field:SerializedName("id_penyedia")
    val id_penyedia: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga")
    val harga: String,

    @field:SerializedName("totalSewa")
    val totalSewa: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String
)