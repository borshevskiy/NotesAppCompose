package com.borshevskiy.notesappcompose.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.borshevskiy.notesappcompose.MainViewModel
import com.borshevskiy.notesappcompose.MainViewModelFactory
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme

@Composable
fun MainScreen(navHostController: NavHostController, mViewModel: MainViewModel) {

    val notes = mViewModel.readAllNotes().observeAsState(listOf()).value

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navHostController.navigate(NavRoute.Add.route)}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note", tint = Color.White)
        }
    }) {
        LazyColumn {
            items(notes) { note ->
                NoteItem(navHostController = navHostController, note = note)
            }
        }
    }
}

@Composable
fun NoteItem(navHostController: NavHostController,note: Note) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 24.dp)
        .clickable { navHostController.navigate(NavRoute.Note.route) },
        elevation = 6.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = note.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = note.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevMainScreen() {
    NotesAppComposeTheme {
        val mViewModel: MainViewModel
                = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))
        MainScreen(navHostController = rememberNavController(), mViewModel = mViewModel)
    }
}