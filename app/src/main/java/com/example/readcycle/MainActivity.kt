package com.example.readcycle

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readcycle.login.LoginScreen
import com.example.readcycle.navigation.Map
import com.example.readcycle.navigation.Routes
import com.example.readcycle.navigation.Timer
import com.example.readcycle.navigation.Top
import com.example.readcycle.navigation.TopLevelRoutes
import com.example.readcycle.ui.theme.ReadCycleTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    init {
                APP = this
    }
    companion object {
        lateinit var APP: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val topLevelRoutes = listOf(
            TopLevelRoutes("Рейтинг",Routes.Top.route, Icons.Outlined.Star),
            TopLevelRoutes("Readомер",Routes.Timer.route, Icons.Outlined.DateRange),
            TopLevelRoutes("Карта",Routes.Map.route, Icons.Outlined.LocationOn)
        )
        setContent {
            val navController = rememberNavController()

            ReadCycleTheme {

                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation{
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            topLevelRoutes.forEach{topLevelRoutes ->
                                BottomNavigationItem(
                                    selected = currentDestination?.route == topLevelRoutes.route,
                                    onClick = { navController.navigate(topLevelRoutes.route)},
                                    icon = { Icon(imageVector=topLevelRoutes.icon, contentDescription = "icon") },
                                    label = {Text(text=topLevelRoutes.name)}
                                )
                            }
                        }
                    }
                ){innerPadding ->
                    LoginScreen()
                    NavHost(navController = navController, startDestination = Routes.Timer.route){
                        composable(Routes.Timer.route){ Timer() }
                        composable(Routes.Map.route){ Map() }
                        composable(Routes.Top.route){ Top() }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadCycleTheme {
        Greeting("Android")
    }
}