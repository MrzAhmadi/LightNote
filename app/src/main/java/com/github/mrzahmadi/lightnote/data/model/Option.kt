package com.github.mrzahmadi.lightnote.data.model

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.github.mrzahmadi.lightnote.BuildConfig
import com.github.mrzahmadi.lightnote.R

data class Option(
    val action: Action,
    val imageVector: ImageVector? = null,
    val painter: Painter? = null,
    val title: String,
    val description: String
) {

    enum class Action {
        SELECT_THEME,
        CLEAR_DATA,
        CHECK_FOR_UPDATE,
        GITHUB,
        ABOUT
    }

    companion object {

        @Composable
        fun getProfileOptionList(context: Context): ArrayList<Option> {
            return arrayListOf<Option>().apply {
                // Add options

                add(
                    Option(
                        Action.SELECT_THEME,
                        imageVector = null,
                        painter = painterResource(id = R.drawable.baseline_dark_mode),
                        context.getString(R.string.item_option_title_night_mode),
                        context.getString(R.string.item_option_description_theme_system)
                    )
                )

                add(
                    Option(
                        Action.CLEAR_DATA,
                        imageVector = Icons.Outlined.Delete,
                        painter = null,
                        context.getString(R.string.item_option_title_clear_data),
                        context.getString(R.string.item_option_description_clear_data)
                    )
                )

                add(
                    Option(
                        Action.CHECK_FOR_UPDATE,
                        imageVector = Icons.Outlined.Refresh,
                        painter = null,
                        context.getString(R.string.item_option_title_check_for_update),
                        context.getString(R.string.item_option_description_check_for_update)
                    )
                )


                add(
                    Option(
                        Action.GITHUB,
                        imageVector = null,
                        painter = painterResource(id = R.drawable.github_mark),
                        context.getString(R.string.item_option_title_github),
                        context.getString(R.string.item_option_description_github)
                    )
                )

                add(
                    Option(
                        Action.ABOUT,
                        imageVector = null,
                        painter = painterResource(id = R.drawable.app_icon),
                        context.getString(R.string.item_option_title_about),
                        context.getString(
                            R.string.item_option_description_about,
                            BuildConfig.VERSION_NAME
                        )
                    )
                )

            }

        }
    }

}
