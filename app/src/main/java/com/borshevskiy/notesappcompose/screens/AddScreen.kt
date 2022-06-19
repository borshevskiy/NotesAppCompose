package com.borshevskiy.notesappcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.ui.theme.NotesAppComposeTheme
import com.borshevskiy.notesappcompose.utils.Constants.Keys.ADD_NEW_NOTE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.ADD_NOTE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.NOTE_DESC
import com.borshevskiy.notesappcompose.utils.Constants.Keys.NOTE_TITLE

@Composable
fun AddScreen(navHostController: NavHostController, mViewModel: MainViewModel) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Scaffold {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(text = ADD_NEW_NOTE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            OutlinedTextField(value = title, onValueChange = { title = it
            isButtonEnabled = title.isNotEmpty() && description.isNotEmpty() }, label = {
                Text(text = NOTE_TITLE) }, isError = title.isEmpty())
            OutlinedTextField(value = description, onValueChange = {description = it
                isButtonEnabled = title.isNotEmpty() && description.isNotEmpty() }, label = {
                Text(text = NOTE_DESC) }, isError = description.isEmpty())
            Button(modifier = Modifier.padding(16.dp),
                enabled = isButtonEnabled,
                onClick = {
                    mViewModel.addNote(Note(title = title, description = description)) {
                        navHostController.navigate(NavRoute.Main.route)
                    }
                }
            ) {
                Text(text = ADD_NOTE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAddScreen() {
    NotesAppComposeTheme {
        val mViewModel: MainViewModel
                = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))
        AddScreen(navHostController = rememberNavController(), mViewModel = mViewModel)
    }
}