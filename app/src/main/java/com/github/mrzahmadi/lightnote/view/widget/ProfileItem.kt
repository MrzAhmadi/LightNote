package com.github.mrzahmadi.lightnote.view.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.mrzahmadi.lightnote.data.model.Option
import com.github.mrzahmadi.lightnote.ui.theme.cardBackgroundColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor
import com.github.mrzahmadi.lightnote.ui.theme.softGrayColor
import com.github.mrzahmadi.lightnote.utils.DAY_GROUP_PREVIEW
import com.github.mrzahmadi.lightnote.utils.NIGHT_GROUP_PREVIEW

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    option: Option,
    onClick: ((Option) -> Unit)? = null
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke(option)
            }
            .background(
                cardBackgroundColor()
            )
    ) {
        val (iconRef, titleRef, descriptionRef) = createRefs()

        if (option.imageVector != null)
            Icon(
                modifier = modifier
                    .height(32.dp)
                    .width(32.dp)
                    .constrainAs(
                        iconRef
                    ) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                imageVector = option.imageVector,
                contentDescription = option.title,
                tint = softGrayColor()
            )

        if (option.painter != null)
            Icon(
                modifier = modifier
                    .height(32.dp)
                    .width(32.dp)
                    .constrainAs(
                        iconRef
                    ) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                painter = option.painter,
                contentDescription = option.title,
                tint = softGrayColor()
            )

        Text(
            modifier = modifier
                .constrainAs(
                    titleRef
                ) {
                    start.linkTo(iconRef.end, 16.dp)
                    top.linkTo(iconRef.top)
                    bottom.linkTo(descriptionRef.top, 2.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
            text = option.title,
            style = TextStyle(
                color = primaryDarkColor(),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                textDirection = TextDirection.Content
            )
        )

        Text(
            modifier = modifier
                .constrainAs(
                    descriptionRef
                ) {
                    start.linkTo(iconRef.end, 16.dp)
                    bottom.linkTo(iconRef.bottom)
                    top.linkTo(titleRef.bottom, 2.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
            text = option.description,
            style = TextStyle(
                color = softGrayColor(),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                textDirection = TextDirection.Content
            )
        )

    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    group = DAY_GROUP_PREVIEW
)
@Composable
fun ProfileItemDayPreview() {
    ProfileItem(
        option = Option(
            Option.Action.ClearData,
            Icons.Outlined.Settings,
            null,
            "Title",
            "Description"
        )
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = NIGHT_GROUP_PREVIEW
)
@Composable
fun ProfileItemNightPreview() {
    ProfileItem(
        option = Option(
            Option.Action.ClearData,
            Icons.Outlined.Settings,
            null,
            "Title",
            "Description"
        )
    )
}