package com.bangkit.rentalbiz.data

data class Category(
    val name: String,
)

object CategoryData {
    val categoryList = listOf<Category>(
        Category(name = "Populer"),
        Category(name = "Electronic"),
        Category(name = "Dapur"),
        Category(name = "Travel"),
        Category(name = "Musik"),
        Category(name = "Fashion"),
        Category(name = "Transportasi"),
        Category(name = "Motor"),
        Category(name = "Mobil"),
        Category(name = "Alat Medis"),
        Category(name = "Hobi"),
        Category(name = "Office"),
    )
}