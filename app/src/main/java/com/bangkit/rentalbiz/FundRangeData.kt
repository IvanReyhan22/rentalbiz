package com.bangkit.rentalbiz

data class Fund(
    val min: Int,
    val max: Int,
)

object FundRangeData {
    val fundRange = listOf<Fund>(
        Fund(min = 0, max = 500000),
        Fund(min = 500000, max = 1000000),
        Fund(min = 1000000, max = 2000000),
        Fund(min = 2000000, max = 3000000),
        Fund(min = 3000000, max = 4000000),
        Fund(min = 4000000, max = 5000000),
        Fund(min = 5000000, max = 6000000),
        Fund(min = 6000000, max = 10000000),
    )
}