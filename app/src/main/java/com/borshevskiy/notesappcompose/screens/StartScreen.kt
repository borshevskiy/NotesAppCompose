package com.borshevskiy.notesappcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme

@Composable
fun StartScreen(navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(text = "What will we use?")
            Button(onClick = { navHostController.navigate(NavRoute.Main.route) },
            modifier = Modifier.width(200.dp).padding(vertical = 8.dp)) {
                Text(text = "Room Database")
            }
            Button(onClick = { navHostController.navigate(NavRoute.Main.route) },
                modifier = Modifier.width(200.dp).padding(vertical = 8.dp)) {
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