package com.borshevskiy.notesappcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.borshevskiy.notesappcompose.utils.Constants.Keys.BACK
import com.borshevskiy.notesappcompose.utils.Constants.Keys.DELETE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.DESC
import com.borshevskiy.notesappcompose.utils.Constants.Keys.EDIT_NOTE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.EMPTY
import com.borshevskiy.notesappcompose.utils.Constants.Keys.NONE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.TITLE
import com.borshevskiy.notesappcompose.utils.Constants.Keys.UPDATE
import com.borshevskiy.notesappcompose.utils.DB_TYPE
import com.borshevskiy.notesappcompose.utils.TYPE_FIREBASE
import com.borshevskiy.notesappcompose.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(navHostController: NavHostController, mViewModel: MainViewModel, noteId: String?) {

    val notes = mViewModel.readAllNotes().observeAsState(listOf()).value
    val note = when(DB_TYPE.value) {
        TYPE_ROOM -> notes.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        TYPE_FIREBASE -> notes.firstOrNull { it.firebaseId == noteId } ?: Note()
        else -> Note()
    }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(EMPTY) }
    var desc by remember { mutableStateOf(EMPTY) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = TITLE) },
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text(text = DESC) },
                        isError = desc.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            mViewModel.updateNote(Note(id = note.id, title = title, description = desc, firebaseId = note.firebaseId)) {
                                navHostController.navigate(NavRoute.Main.route)
                            }
                        }
                    ) {
                        Text(text = UPDATE)
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                        Text(
                            text = note.description,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            title = note.title
                            desc = note.description
                            bottomSheetState.show()
                        }
                    }) {
                        Text(text = UPDATE)
                    }
                    Button(onClick = {
                        mViewModel.deleteNote(note = note) {
                            navHostController.navigate(NavRoute.Main.route)
                        }
                    }) {
                        Text(text = DELETE)
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navHostController.navigate(NavRoute.Main.route)
                    }
                ) {
                    Text(text = BACK)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevNoteScreen() {
    NotesAppComposeTheme {
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))
        NoteScreen(
            navHostController = rememberNavController(),
            mViewModel = mViewModel,
            noteId = "1"
        )
    }
}