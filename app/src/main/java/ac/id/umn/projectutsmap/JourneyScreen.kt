package ac.id.umn.projectutsmap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyScreen(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val newEntryJson = backStackEntry?.savedStateHandle?.get<String>("newEntry")
    val newEntry = remember(newEntryJson) {
        newEntryJson?.let { Gson().fromJson(it, JournalEntry::class.java) }
    }

    // Simpan daftar entri di state
    var journalEntries by remember { mutableStateOf(listOf<JournalEntry>()) }

    // Tambahkan entry baru ke list jika ada
    LaunchedEffect(newEntry) {
        newEntry?.let {
            journalEntries = journalEntries + it
            backStackEntry?.savedStateHandle?.remove<String>("newEntry")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Halo, Anonymous", fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { /* TODO: Search action */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* TODO: Navigate to profile */ }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("new_entry")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "New Entry")
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(journalEntries) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            val json = Gson().toJson(entry)
                            navController.navigate("entry_detail/$json")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = entry.date, style = MaterialTheme.typography.titleMedium)
                        Text(text = entry.content.take(50) + "...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { /* Tetap di Journey */ },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Journey") },
            label = { Text("Journey") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("calendar") },
            icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Calendar") },
            label = { Text("Calendar") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("media") },
            icon = { Icon(Icons.Filled.Image, contentDescription = "Media") },
            label = { Text("Media") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("atlas") },
            icon = { Icon(Icons.Filled.Map, contentDescription = "Atlas") },
            label = { Text("Atlas") }
        )
    }
}


