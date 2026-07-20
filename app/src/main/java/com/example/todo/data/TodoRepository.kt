package com.example.todo.data

import com.example.todo.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): Flow<List<Todo>>{
        return todoDao.getAllTodos()
    }

    suspend fun insetTodo(todo: Todo){
        return todoDao.insetTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        return todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        return todoDao.deleteTodo(todo)
    }
}