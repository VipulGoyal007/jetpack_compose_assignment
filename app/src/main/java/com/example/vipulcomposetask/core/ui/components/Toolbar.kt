package com.example.vipulcomposetask.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.vipulcomposetask.LocalNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    title: String,
    isBackArrow: Boolean = false,
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    ),
    onBackClick: (() -> Unit)? = null
) {
    val navController = LocalNavController.current
    TopAppBar(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .shadow(elevation = 5.dp),
        colors = topAppBarColors,
        navigationIcon = {
            AnimatedVisibility(isBackArrow) {
                Icon(
                    modifier = Modifier.clickable {
                        onBackClick?.invoke() ?: run {
                            navController.navigateUp()
                        }
                    },
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = topAppBarColors.titleContentColor
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomToolbarPreview() {
    MaterialTheme {
        Surface {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalNavController provides navController) {
                CustomToolbar(
                    title = "Crypto Listing"
                )
            }
        }
    }
}
