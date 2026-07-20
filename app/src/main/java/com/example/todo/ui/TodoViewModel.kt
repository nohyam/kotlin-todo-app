package com.example.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepository
import com.example.todo.model.Todo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel(){

    val todos: StateFlow<List<Todo>> =
        repository.getAllTodos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun insertTodo(todo: Todo){
        viewModelScope.launch {
            repository.insetTodo(todo)
        }
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
}