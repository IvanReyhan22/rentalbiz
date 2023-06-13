package com.bangkit.rentalbiz.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.bangkit.rentalbiz.data.CategoryData
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDropDown(
    value: String,
    label: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = CategoryData.categoryList

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        Column {
            if (!label.isNullOrEmpty()) {
                Paragraph(
                    title = label,
                    type = ParagraphType.MEDIUM,
                    fontWeight = FontWeight.Medium,
                    color = Shades90,
                    modifier = modifier.padding(bottom = AppTheme.dimens.spacing_8)
                )
            }
            Box {
                TextField(
                    value = value,
                    onValueChange = {
                        expanded = true
                        onValueChange(it)
                    },
                    shape = RoundedCornerShape(AppTheme.dimens.radius_12),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Neutral100
                    ),
                    placeholder = {
                        Paragraph(
                            title = suggestions[0].name,
                            type = ParagraphType.MEDIUM,
                            color = Neutral500
                        )
                    },
                    trailingIcon = {
                        Icon(
                            icon, "drowpdown icon",
                            Modifier.clickable { expanded = !expanded },
                        )
                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier
                        .widthIn(min = 1.dp)
                        .fillMaxWidth()
                        .heightIn(min = AppTheme.dimens.spacing_24)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .heightIn(max = 240.dp)
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    val filtered = suggestions.filter {
                        it.name.contains(value, ignoreCase = true)
                    }
                    filtered.forEach { category ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                onValueChange(category.name)
                            },
                        ) {
                            Paragraph(
                                title = category.name,
                                type = ParagraphType.MEDIUM,
                                color = Neutral500
                            )
                        }
                    }
                }
            }

        }
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = { selectedText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    textFieldSize = coordinates.size.toSize()
//                },
//            label = { Text("Label") },
//            trailingIcon = {
//                Icon(
//                    icon, "contentDescription",
//                    Modifier.clickable { expanded = !expanded },
//                )
//            }
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyDropDownPreview() {
    RentalBizTheme {
        MyDropDown(value = "", onValueChange = {}, label = "Test")
    }
}
