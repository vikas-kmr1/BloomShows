package com.android.bloomshows.ui.common_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.SmallPadding

@Composable
fun BottomNavBar() {
    val list = listOf(Icons.Filled.ExitToApp, Icons.Filled.ShoppingCart)
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar(
        modifier = Modifier,
        tonalElevation = SmallPadding,
    ) {
        list.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item, contentDescription = null) },
                label = { Text(text = index.toString()) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },

            )
        }

    }
}

@Preview
@Composable
fun PreviewNavBottomBar(){
    BloomShowsTheme() {
        BottomNavBar()
    }

}


