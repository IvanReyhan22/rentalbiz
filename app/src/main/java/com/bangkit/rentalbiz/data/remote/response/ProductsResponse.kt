package com.bangkit.rentalbiz.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductsResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("items")
    val items: List<Product>? = null
)

data class ProductResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("item")
    val item: Product? = null

)


@Parcelize
data class Product(

    @field:SerializedName("persyaratan")
    val persyaratan: String? = null,

    @field:SerializedName("tersedia")
    val tersedia: Int? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("kategori")
    val kategori: String? = null,

    @field:SerializedName("stok")
    val stok: Int? = null,

    @field:SerializedName("id_penyedia")
    val idPenyedia: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("harga")
    val harga: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("totalSewa")
    val totalSewa: Int? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null
):Parcelable
