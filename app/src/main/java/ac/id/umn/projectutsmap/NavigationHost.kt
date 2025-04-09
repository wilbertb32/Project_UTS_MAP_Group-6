package ac.id.umn.projectutsmap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import androidx.compose.runtime.getValue
import java.util.Calendar

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("journey") { JourneyScreen(navController) } // no change here
        composable("calendar") { CalendarScreen(navController) }
        composable("new_entry") { NewEntryScreen(navController) }
        composable("edit_screen") {
            val showDeleteDialog = remember { mutableStateOf(false) }
            EditScreen(navController, showDeleteDialog)
        }
        composable("entry_detail/{entry}") { backStackEntry ->
            val entryJson = backStackEntry.arguments?.getString("entry") ?: ""
            val entry = Gson().fromJson(entryJson, JournalEntry::class.java)

            EntryDetailScreen(
                date = entry.date,
                time = entry.time,
                content = entry.content,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }
        composable("media") { MediaScreen(navController) }
        composable("atlas") { AtlasScreen(navController) }
    }
}
