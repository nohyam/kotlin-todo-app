package com.example.todo.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    private var todoList: List<Todo> = emptyList()
    private var nextTodoId = 5L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoList = listOf(
            Todo(title = "공부하기", id = 1L),
            Todo(title = "요리하기", id = 2L),
            Todo(title = "운동하기", id = 3L),
            Todo(title = "독서하기", id = 4L),
        )

        val todoRecyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(
            onTodoChecked = { checkedTodo ->
                todoList = todoList.map { todo ->
                    if (todo.id == checkedTodo.id) {
                        todo.copy(
                            isCompleted = !todo.isCompleted
                        )
                    } else {
                        todo
                    }
                }
                todoAdapter.submitList(todoList)
            },
            onTodoDelete = { deleteTodo ->
                MaterialAlertDialogBuilder(this)
                    .setTitle("정말 삭제하시겠습니까?")
                    .setMessage("삭제하면 복구하실수 없습니다.")
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("삭제") { _, _ ->

                        todoList = todoList.filterNot {
                            it.id == deleteTodo.id
                        }
                        todoAdapter.submitList(todoList)


                    }
                    .show()

            },
            onTodoClick = { clickedTodo ->
                val todoUpdateBottomSheet =
                    TodoBottomSheetFragment(clickedTodo) { todoTitle, todoContents ->
                        val updatedTodo = clickedTodo.copy(
                            title = todoTitle,
                            contents = todoContents
                        )

                        todoList = todoList.map { todo ->
                            if (todo.id == updatedTodo.id) {
                                updatedTodo
                            } else {
                                todo
                            }
                        }
                        todoAdapter.submitList(todoList)
                    }
                todoUpdateBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
            }
        )

        todoAdapter.submitList(todoList)

        findViewById<ImageButton>(R.id.todoAddBtn).setOnClickListener {
            val todoBottomSheet = TodoBottomSheetFragment { todoTitle, todoContents ->
                val todo = Todo(
                    id = nextTodoId++,
                    title = todoTitle,
                    contents = todoContents
                )

                todoList = todoList + todo
                todoAdapter.submitList(todoList)
            }
            todoBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
        }


        todoRecyclerView.adapter = todoAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}