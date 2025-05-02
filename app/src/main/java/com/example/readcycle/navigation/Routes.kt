package com.example.readcycle.navigation

sealed class Routes(val route:String) {
    object Map: Routes("map")
    object Timer: Routes("timer")
    object Top: Routes("top")

}
