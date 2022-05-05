package com.borshevskiy.notesappcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.borshevskiy.notesappcompose.MainViewModel
import com.borshevskiy.notesappcompose.MainViewModelFactory
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme
import com.borshevskiy.notesappcompose.utils.TYPE_FIREBASE
import com.borshevskiy.notesappcompose.utils.TYPE_ROOM

@Composable
fun StartScreen(navHostController: NavHostController) {

    val mViewModel: MainViewModel
    = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(text = "What will we use?")
            Button(onClick = {
                mViewModel.initDatabase(TYPE_ROOM)
                navHostController.navigate(NavRoute.Main.route) },
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 8.dp)) {
                Text(text = "Room Database")
            }
            Button(onClick = {
                mViewModel.initDatabase(TYPE_FIREBASE)
                navHostController.navigate(NavRoute.Main.route) },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)) {
                Text(text = "Firebase Database")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevStartScreen() {
    NotesAppComposeTheme {
        StartScreen(navHostController = rememberNavController())
    }
}