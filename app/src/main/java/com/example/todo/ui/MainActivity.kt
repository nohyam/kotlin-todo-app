package com.example.todo.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.TodoDatabase
import com.example.todo.data.TodoRepository
import com.example.todo.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels {
        val database = TodoDatabase.getDatabase(applicationContext)
        val repository = TodoRepository(database.todoDao())

        TodoViewModelFactory(repository)
    }

    private lateinit var todoAdapter: TodoAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoRecyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(
            onTodoChecked = { todo ->
                val checkedTodo = todo.copy(
                    isCompleted = !todo.isCompleted
                )
                viewModel.updateTodo(checkedTodo)
            },
            onTodoDelete = { todo ->
                MaterialAlertDialogBuilder(this)
                    .setTitle("정말 삭제하시겠습니까?")
                    .setMessage("삭제하면 복구하실수 없습니다.")
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("삭제") { _, _ ->

                        viewModel.deleteTodo(todo)


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

                        viewModel.updateTodo(updatedTodo)

                    }
                todoUpdateBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
            }
        )


        findViewById<ImageButton>(R.id.todoAddBtn).setOnClickListener {
            val todoBottomSheet = TodoBottomSheetFragment { todoTitle, todoContents ->
                val todo = Todo(
                    title = todoTitle,
                    contents = todoContents
                )
                viewModel.insertTodo(todo)
            }
            todoBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
        }


        todoRecyclerView.adapter = todoAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todos.collect { todos ->
                    todoAdapter.submitList(todos)
                }
            }
        }

    }
}