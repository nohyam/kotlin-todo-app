package com.example.todo.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoList = mutableListOf(
            Todo(title = "공부하기"),
            Todo(title = "요리하기"),
            Todo(title = "운동하기"),
            Todo(title = "독서하기"),
        )

        val todoRecyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(todoList){
            checkedTodo -> val index = todoList.indexOfFirst {
                it.id == checkedTodo.id
            }
            if (index == -1){
                return@TodoAdapter
            }
            val changedTodo = checkedTodo.copy(
                isCompleted = !checkedTodo.isCompleted
            )
            todoList[index] = changedTodo
            todoAdapter.notifyItemChanged(index)
        }

        findViewById<ImageButton>(R.id.todoAddBtn).setOnClickListener {
            val todoBottomSheet = TodoBottomSheetFragment{
                todoTitle,todoContents -> val todo = Todo(
                    title = todoTitle,
                    contents = todoContents
                )

                todoList.add(todo)
                todoAdapter.notifyItemInserted(todoList.lastIndex)
            }
            todoBottomSheet.show(supportFragmentManager,"TodoBottomSheet")
        }

        todoRecyclerView.adapter = todoAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}