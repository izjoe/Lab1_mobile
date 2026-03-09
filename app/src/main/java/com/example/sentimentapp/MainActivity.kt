package com.example.sentimentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sentimentapp.ui.theme.SentimentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SentimentAppTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = {
                        currentRoute = "home"
                        navController.navigate("home")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Face, contentDescription = "Sentiment") },
                    label = { Text("Sentiment") },
                    selected = currentRoute == "sentiment",
                    onClick = {
                        currentRoute = "sentiment"
                        navController.navigate("sentiment")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Quotes") },
                    label = { Text("Quotes") },
                    selected = currentRoute == "quotes",
                    onClick = {
                        currentRoute = "quotes"
                        navController.navigate("quotes")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = currentRoute == "tasks",
                    onClick = {
                        currentRoute = "tasks"
                        navController.navigate("tasks")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Convert") },
                    label = { Text("Convert") },
                    selected = currentRoute == "convert",
                    onClick = {
                        currentRoute = "convert"
                        navController.navigate("convert")
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("sentiment") { SentimentScreen() }
            composable("quotes") { QuoteScreen() }
            composable("tasks") { TaskScreen() }
            composable("convert") { UnitConverterScreen() }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Portfolio App Hub", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Welcome to my Android showcase. This hub demonstrates:\n\n" +
                    "• Multi-screen Navigation\n" +
                    "• Modern UI with Jetpack Compose\n" +
                    "• State Management\n" +
                    "• Logic & Data Handling",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun SentimentScreen() {
    var text by remember { mutableStateOf("") }
    var sentiment by remember { mutableStateOf(Sentiment.NEUTRAL) }
    val analyzer = remember { SentimentAnalyzer() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sentiment Analyzer", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = text,
            onValueChange = {
                text = it
                sentiment = analyzer.analyze(it)
            },
            label = { Text("Enter text (try 'happy' or 'sad')") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        val (sentimentText, color) = when (sentiment) {
            Sentiment.POSITIVE -> "Positive \uD83D\uDE0A" to Color(0xFF4CAF50)
            Sentiment.NEGATIVE -> "Negative \uD83D\uDE1E" to Color(0xFFF44336)
            Sentiment.NEUTRAL -> "Neutral \uD83D\uDE10" to Color(0xFF9E9E9E)
        }
        Text("Result:", fontSize = 18.sp)
        Text(sentimentText, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun QuoteScreen() {
    val quoteGenerator = remember { QuoteGenerator() }
    var currentQuote by remember { mutableStateOf(quoteGenerator.getRandomQuote()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Daily Inspiration", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "\"${currentQuote.text}\"",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Text(
                    text = "- ${currentQuote.author}",
                    modifier = Modifier.align(Alignment.End).padding(top = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { currentQuote = quoteGenerator.getRandomQuote() }) {
            Text("Next Quote")
        }
    }
}

@Composable
fun TaskScreen() {
    val tasks = remember { mutableStateListOf<Task>() }
    var nextId by remember { mutableStateOf(1) }
    var newTaskText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Task Tracker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("What needs to be done?") }
            )
            IconButton(onClick = {
                if (newTaskText.isNotBlank()) {
                    tasks.add(Task(nextId++, newTaskText))
                    newTaskText = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = {
                        val index = tasks.indexOf(task)
                        if (index != -1) {
                            tasks[index] = task.copy(isCompleted = !task.isCompleted)
                        }
                    },
                    onDelete = { tasks.remove(task) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = task.description,
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) Color.Gray else Color.Unspecified
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun UnitConverterScreen() {
    var celsiusText by remember { mutableStateOf("") }
    var fahrenheitText by remember { mutableStateOf("") }
    val converter = remember { UnitConverter() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Temperature Converter", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = celsiusText,
            onValueChange = {
                celsiusText = it
                val c = it.toDoubleOrNull()
                fahrenheitText = if (c != null) converter.celsiusToFahrenheit(c).toString() else ""
            },
            label = { Text("Celsius (°C)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fahrenheitText,
            onValueChange = {
                fahrenheitText = it
                val f = it.toDoubleOrNull()
                celsiusText = if (f != null) converter.fahrenheitToCelsius(f).toString() else ""
            },
            label = { Text("Fahrenheit (°F)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}