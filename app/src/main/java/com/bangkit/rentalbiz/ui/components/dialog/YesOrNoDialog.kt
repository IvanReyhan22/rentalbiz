package com.bangkit.rentalbiz.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Error400
import com.bangkit.rentalbiz.ui.theme.Neutral100
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun YesOrNoDialog(
    title: String,
    subTitle: String,
    image: Painter,
    iconColor: Color = Error400,
    yesTitle: String = "Ya",
    noTitle: String = "Tidak",
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
    openDialog: () -> Unit,
    closeDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { closeDialog() }) {
        Card(
            shape = RoundedCornerShape(AppTheme.dimens.radius_16),
            modifier = Modifier.padding(
                AppTheme.dimens.spacing_12,
                AppTheme.dimens.spacing_6,
                AppTheme.dimens.spacing_12,
                AppTheme.dimens.spacing_12
            ),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(Color.White)
            ) {
                Image(
                    painter = image,
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = iconColor
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),

                    )

                Column(modifier = Modifier.padding(16.dp)) {
                    Heading(
                        title = title,
                        type = HeadingType.H5,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = AppTheme.dimens.spacing_6)
                            .fillMaxWidth()
                    )
                    Paragraph(
                        title = subTitle,
                        type = ParagraphType.MEDIUM,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                top = AppTheme.dimens.spacing_12,
                                start = AppTheme.dimens.spacing_24,
                                end = AppTheme.dimens.spacing_24
                            )
                            .fillMaxWidth(),
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(Neutral100),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(
                        onClick = {
                            closeDialog()
                            onNoClick()
                        }
                    ) {
                        Paragraph(
                            title = noTitle,
                            type = ParagraphType.MEDIUM,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    top = AppTheme.dimens.spacing_6,
                                    bottom = AppTheme.dimens.spacing_6,
                                ),
                        )
                    }

                    TextButton(
                        onClick = {
                            closeDialog()
                            onYesClick()
                        },
                    ) {
                        Paragraph(
                            title = yesTitle,
                            type = ParagraphType.MEDIUM,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    top = AppTheme.dimens.spacing_6,
                                    bottom = AppTheme.dimens.spacing_6,
                                ),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun YesOrNoDialogPreview() {
    RentalBizTheme() {
        YesOrNoDialog(
            openDialog = {},
            closeDialog = {},
            title = "Warning",
            subTitle = "Are you sure to delete this item",
            image = painterResource(id = R.drawable.ic_outline_warning),
            onYesClick = { /*TODO*/ },
            onNoClick = { /*TODO*/ })
    }
}