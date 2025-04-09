package ac.id.umn.projectutsmap

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.gson.Gson
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavController) {
    val context = LocalContext.current
    val currentMonth = remember { YearMonth.now() }
    val today = remember { LocalDate.now() }
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val locale = Locale.getDefault()

    val days = remember {
        val list = mutableListOf<LocalDate?>()
        val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        repeat(startDayOfWeek) { list.add(null) } // Blank cells before start
        for (day in 1..daysInMonth) {
            list.add(currentMonth.atDay(day))
        }
        while (list.size % 7 != 0) list.add(null) // Fill the end of grid
        list
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        currentMonth.month.name, // You can use currentMonth.month.name if dynamic
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
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
        bottomBar = { BottomNavigationBarCalendar(navController) }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(8.dp)) {

            // Day headers (S M T W T F S)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Monthly Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(days) { date ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(1.dp, Color.LightGray)
                            .background(
                                if (date == today) Color(0xFFE3F2FD) else Color.Transparent
                            )
                            .clickable(enabled = date != null) {
                                // Navigate to entry_detail or other logic
//                                date?.let {
//                                    navController.navigate("entry_detail/${it.toString()}")
//                                }
                                Toast.makeText(context, "TANGGAL DI KLIK", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        date?.let {
                            Text(
                                text = it.dayOfMonth.toString(),
                                fontWeight = if (it == today) FontWeight.Bold else FontWeight.Normal,
                                color = if (it == today) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
    //sampe sini


@Composable
fun BottomNavigationBarCalendar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("journey") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Journey") },
            label = { Text("Journey") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { /* Tetap di Calendar */ },
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


