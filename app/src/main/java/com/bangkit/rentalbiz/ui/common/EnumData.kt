package com.bangkit.rentalbiz.ui.common

enum class HeadingType {
    D1,
    D2,
    H1,
    H2,
    H3,
    H4,
    H5,
    H6,
}

enum class ButtonSize {
    LARGE,
    MEDIUM,
    SMALL
}

enum class ParagraphType {
    LARGE,
    MEDIUM,
    SMALL,
    XSMALL,
    OVERLINE
}

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    SUCCESS,
    ERROR,
    ACCENT,
    WARNING
}

val TransactionStatus = mapOf(
    1 to "Pending",
    2 to "Sewa",
    3 to "Batal",
    4 to "Selesai"
)