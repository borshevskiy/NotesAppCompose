package com.borshevskiy.notesappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.borshevskiy.notesappcompose.MainViewModel
import com.borshevskiy.notesappcompose.screens.StartScreen
import com.borshevskiy.notesappcompose.screens.MainScreen
import com.borshevskiy.notesappcompose.screens.AddScreen
import com.borshevskiy.notesappcompose.screens.NoteScreen
import com.borshevskiy.notesappcompose.utils.Constants.Keys.ID
import com.borshevskiy.notesappcompose.utils.Constants.Screens.ADD_SCREEN
import com.borshevskiy.notesappcompose.utils.Constants.Screens.MAIN_SCREEN
import com.borshevskiy.notesappcompose.utils.Constants.Screens.NOTE_SCREEN
import com.borshevskiy.notesappcompose.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String) {
    object Start: NavRoute(START_SCREEN)
    object Main: NavRoute(MAIN_SCREEN)
    object Add: NavRoute(ADD_SCREEN)
    object Note: NavRoute(NOTE_SCREEN)
}

@Composable
fun NotesNavHost(mViewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { StartScreen(navController, mViewModel) }
        composable(NavRoute.Main.route) { MainScreen(navController, mViewModel) }
        composable(NavRoute.Add.route) { AddScreen(navController, mViewModel) }
        composable(NavRoute.Note.route + "/{${ID}}") { backStackEntry ->
            NoteScreen(navController, mViewModel, noteId = backStackEntry.arguments?.getString(ID)) }
    }
}