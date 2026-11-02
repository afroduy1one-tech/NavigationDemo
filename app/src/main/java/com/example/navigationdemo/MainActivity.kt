package com.example.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navigationdemo.screens.Home
import com.example.navigationdemo.screens.Profile
import com.example.navigationdemo.screens.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(HomeScreen)

    val onNavigation: (NavKey) -> Unit = { screen ->
        backStack.add(screen)
    }

    val onClearBackStack: () -> Unit = {
        while (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {

            entry<HomeScreen> {
                Home(onNavigation)
            }

            entry<WelcomeScreen>(
                metadata = mapOf("extraDataKey" to "extraDataValue")
            ) { key ->
                Welcome(
                    onNavigation,
                    key.name
                )
            }

            entry<ProfileScreen> {
                Profile(onClearBackStack)
            }
        }
    )
}