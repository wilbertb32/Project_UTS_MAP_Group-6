package ac.id.umn.projectutsmap

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(
    date: String,
    time: String,
    content: String,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    navController: NavController,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Edit clicked!", Toast.LENGTH_SHORT).show()
                        navController.navigate("edit_screen")
                        onEditClick()
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f, fill = false)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onPrevClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                }
                IconButton(onClick = onNextClick) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        }
    }
}


