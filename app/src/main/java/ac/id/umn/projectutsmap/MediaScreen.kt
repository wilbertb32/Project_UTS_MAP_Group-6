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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(navController: NavController) {
    val dummyEntries = listOf(
        mapOf(
            "date" to "2024-04-09",
            "time" to "10:00",
            "content" to "Went hiking",
            "imageUrl" to "https://images.unsplash.com/photo-1506744038136-46273834b3fb"
        ),
        mapOf(
            "date" to "2024-04-08",
            "time" to "14:00",
            "content" to "Coffee time",
            "imageUrl" to "https://images.unsplash.com/photo-1509042239860-f550ce710b93"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Media Gallery") },

                actions = {
                    IconButton(onClick = { /* search */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* profile */ }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBarMedia(navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(dummyEntries.filter { it["imageUrl"]?.isNotEmpty() == true }) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            val json = Gson().toJson(entry)
                            navController.navigate("entry_detail/$json")
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(entry["date"] ?: "")
                        Text((entry["content"] ?: "").take(50) + "...")
                        entry["imageUrl"]?.let { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

    @Composable
    fun BottomNavigationBarMedia(navController: NavController) {
        NavigationBar {
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate("journey") },
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
                selected = true,
                onClick = { /* Tetap Di Media */ },
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

