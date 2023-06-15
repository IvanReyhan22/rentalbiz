package com.bangkit.rentalbiz.data.remote

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val address: String,
    val city: String,
    val phone: String,
)

data class TransactionRequest(
    val id_barang: Int,
    val tanggal_pinjam: String,
    val tanggal_kembali: String,
    val jumlah: Int,
)

data class ProductRequest(
    val nama: String,
    val stok: String,
    val deskripsi: String,
    val harga: String,
    val persyaratan: String,
    val kategori: String
)