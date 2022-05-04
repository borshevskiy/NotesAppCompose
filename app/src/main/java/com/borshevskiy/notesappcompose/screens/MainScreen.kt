package com.borshevskiy.notesappcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme

@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navHostController.navigate(NavRoute.Add.route)}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note", tint = Color.White)
        }
    }) {
        NoteItem(navHostController = navHostController, title = "Tile", description = "Description")
    }
}

@Composable
fun NoteItem(navHostController: NavHostController,title: String, description: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 24.dp)
        .clickable { navHostController.navigate(NavRoute.Note.route) },
        elevation = 6.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevMainScreen() {
    NotesAppComposeTheme {
        MainScreen(navHostController = rememberNavController())
    }
}