package com.bangkit.rentalbiz.ui.components.modal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.CategoryData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.theme.AppTheme

@Composable
fun CategoryBottomSheetModal(
    onCategoryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var category by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_12),
        modifier = modifier.fillMaxHeight(1F)
    ) {
        /* Category List */
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Heading(title = stringResource(R.string.Category), type = HeadingType.H6)
        }
        items(CategoryData.categoryList) { data ->
            MyButton(
                title = data.name,
                size = ButtonSize.SMALL,
                type = if (category == data.name) ButtonType.ACCENT else ButtonType.SECONDARY,
                onClick = {
                    category = data.name
                    onCategoryChange(category)
                },
            )
        }
//        item(span = { GridItemSpan(this.maxLineSpan) }) {
//            MyButton(
//                title = stringResource(R.string.pick_category),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = AppTheme.dimens.spacing_8),
//                onClick = {
//
//                }
//            )
//        }
    }
}