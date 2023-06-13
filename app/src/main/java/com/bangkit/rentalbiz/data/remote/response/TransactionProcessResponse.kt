package com.bangkit.rentalbiz.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionProcessResponse(

    @field:SerializedName("total_harga_sewa")
    val totalHargaSewa: Int? = null,

    @field:SerializedName("jumlah")
    val jumlah: Int? = null,

    @field:SerializedName("id_barang")
    val idBarang: Int? = null,

    @field:SerializedName("tanggal_pinjam")
    val tanggalPinjam: String? = null,

    @field:SerializedName("tanggal_kembali")
    val tanggalKembali: String? = null,

    @field:SerializedName("id_penyewa")
    val idPenyewa: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("error")
    val error: String? = null
)

data class TransactionListResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("transactions")
    val transactions: List<TransactionsItem>? = null
)

data class TransactionByIdResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("transaction")
    val transaction: TransactionsItem? = null
)

data class TransactionsItem(

    @field:SerializedName("total_harga_sewa")
    val totalHargaSewa: Int? = null,

    @field:SerializedName("jumlah")
    val jumlah: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_barang")
    val idBarang: Int? = null,

    @field:SerializedName("tanggal_pinjam")
    val tanggalPinjam: String? = null,

    @field:SerializedName("tanggal_kembali")
    val tanggalKembali: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_penyewa")
    val idPenyewa: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("nama_barang")
    val namaBarang: String? = null,

    @field:SerializedName("nama_penyewa")
    val namaPenyewa: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,
)


