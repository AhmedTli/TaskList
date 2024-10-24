package com.example.tasklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklist.model.Task




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListApp()
        }
    }
}

@Composable
fun TaskListApp() {

    var tasks by remember { mutableStateOf(generateInitialTasks()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // LazyColumn for scrolling list of tasks
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItem(task = task, onToggleCompleted = {
                    // Update the task completion status
                    tasks = tasks.map {
                        if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it
                    }
                }, onDelete = {

                    tasks = tasks.filter { it.id != task.id }
                })
            }
        }

        // Button to add a new task
        Button(onClick = {
            val newTask = Task(id = tasks.size + 1, title = "Task ${tasks.size + 1}")
            tasks = tasks + newTask
        }) {
            Text("Add Task")
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggleCompleted: () -> Unit, onDelete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {


        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = task.title,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onToggleCompleted() }
        )

        Text(
            text = "X",
            color = Color.Red,
            fontSize = 20.sp,
            modifier = Modifier
                .clickable(onClick = onDelete)
                .padding(8.dp)
        )
    }
}

// Function to generate the initial list of tasks
fun generateInitialTasks(): List<Task> {
    return List(100) { index -> Task(id = index + 1, title = "Task ${index + 1}") }
}
