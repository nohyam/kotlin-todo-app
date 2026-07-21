package com.example.todo.data

import com.example.todo.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): Flow<List<Todo>>{
        return todoDao.getAllTodos()
    }

    suspend fun insertTodo(todo: Todo){
        todoDao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        todoDao.updateTodo(todo)
    }   

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }
}
