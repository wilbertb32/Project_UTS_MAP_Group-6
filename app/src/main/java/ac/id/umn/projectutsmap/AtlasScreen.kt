package ac.id.umn.projectutsmap

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtlasScreen(navController: NavController) {
    // Dummy data lokasi dari catatan
    val geoTaggedEntries = listOf(
        GeoTaggedEntry("Pantai Kuta", -8.7177, 115.1682),
        GeoTaggedEntry("Monas", -6.1754, 106.8272),
        GeoTaggedEntry("Candi Borobudur", -7.6079, 110.2038)
    )

    // Posisi kamera awal
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-6.2, 106.8), 5f)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBarAtlas(navController) }
    ) { padding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            cameraPositionState = cameraPositionState
        ) {
            geoTaggedEntries.forEach { entry ->
                Marker(
                    state = MarkerState(position = LatLng(entry.lat, entry.lng)),
                    title = entry.title,
                    onClick = {
                        Log.d("AtlasScreen", "Marker clicked: ${entry.title}")
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Klik: ${entry.title}")
                        }
                        true
                    }
                )
            }
        }
    }
}

data class GeoTaggedEntry(
    val title: String,
    val lat: Double,
    val lng: Double,
    val id: String = UUID.randomUUID().toString()
)

@Composable
fun BottomNavigationBarAtlas(navController: NavController) {
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
            selected = false,
            onClick = { navController.navigate("media") },
            icon = { Icon(Icons.Filled.Image, contentDescription = "Media") },
            label = { Text("Media") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { /* Tetap di atlas */ },
            icon = { Icon(Icons.Filled.Map, contentDescription = "Atlas") },
            label = { Text("Atlas") }
        )
    }
}