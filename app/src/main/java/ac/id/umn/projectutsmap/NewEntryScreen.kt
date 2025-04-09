package ac.id.umn.projectutsmap

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

data class JournalEntry(
    val content: String,
    val date: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentTime = timeFormat.format(Date())

    var noteText by remember { mutableStateOf("") }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            Toast.makeText(context, "Foto berhasil diambil", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Foto gagal diambil", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(currentDate) },
                actions = {
                    IconButton(onClick = {
                        val entry = JournalEntry(
                            content = noteText,
                            date = currentDate,
                            time = currentTime
                        )
                        val json = Gson().toJson(entry)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("newEntry", json)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FloatingActionButton(
                        onClick = { cameraLauncher.launch(null) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = "Add Media")
                    }

                    FloatingActionButton(
                        onClick = {
                            Toast.makeText(context, "Ambil lokasi belum diimplementasi", Toast.LENGTH_SHORT).show()
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Place, contentDescription = "Add Location")
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Tulis catatan di sini...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 20
            )
        }
    }
}




