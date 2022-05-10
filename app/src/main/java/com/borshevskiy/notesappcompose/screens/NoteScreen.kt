package com.borshevskiy.notesappcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme

@Composable
fun NoteScreen(navHostController: NavHostController, mViewModel: MainViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Card(modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)) {
                Column(modifier = Modifier.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Title",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    Text(text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PrevNoteScreen() {
    NotesAppComposeTheme {
        val mViewModel: MainViewModel
                = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))
        NoteScreen(navHostController = rememberNavController(), mViewModel = mViewModel)
    }
}