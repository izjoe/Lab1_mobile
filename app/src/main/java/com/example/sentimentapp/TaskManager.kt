package com.example.sentimentapp

data class Task(val id: Int, val description: String, val isCompleted: Boolean = false)

class TaskManager {
    private var nextId = 1
    private val _tasks = mutableListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(description: String) {
        if (description.isNotBlank()) {
            _tasks.add(Task(nextId++, description))
        }
    }

    fun toggleTask(id: Int) {
        val index = _tasks.indexOfFirst { it.id == id }
        if (index != -1) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun removeTask(id: Int) {
        _tasks.removeAll { it.id == id }
    }
}