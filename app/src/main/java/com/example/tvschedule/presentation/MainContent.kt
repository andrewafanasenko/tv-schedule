package com.example.tvschedule.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tvschedule.presentation.model.NavigationItem
import com.example.tvschedule.presentation.ui.theme.TVScheduleTheme

@Composable
fun MainContent(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Search,
                    NavigationItem.Bookmarks
                )
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavBarItem(item, currentDestination, navController)
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(navController, innerPadding)
    }
}

@Composable
private fun RowScope.NavBarItem(
    item: NavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = { Icon(item.icon, contentDescription = null) },
        label = { Text(stringResource(item.titleRes)) },
        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContent() {
    TVScheduleTheme {
        MainContent()
    }
}
