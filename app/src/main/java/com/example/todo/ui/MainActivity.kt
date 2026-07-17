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
    private var nextTodoId = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoList = mutableListOf(
            Todo(title = "공부하기", id = 1L),
            Todo(title = "요리하기", id = 2L),
            Todo(title = "운동하기", id = 3L),
            Todo(title = "독서하기", id = 4L),
        )

        val todoRecyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(
            todoList,
            onTodoChecked = { checkedTodo ->
                val index = todoList.indexOfFirst {
                    it.id == checkedTodo.id
                }
                if (index == -1) {
                    return@TodoAdapter
                }
                val changedTodo = checkedTodo.copy(
                    isCompleted = !checkedTodo.isCompleted
                )
                todoList[index] = changedTodo
                todoAdapter.notifyItemChanged(index)
            },
            onTodoDelete = { deleteTodo ->
                MaterialAlertDialogBuilder(this)
                    .setTitle("정말 삭제하시겠습니까?")
                    .setMessage("삭제하면 복구하실수 없습니다.")
                    .setNegativeButton("취소"){
                        dialog, which -> dialog.dismiss()
                    }
                    .setPositiveButton("삭제"){
                        _,_ ->
                        val index = todoList.indexOfFirst {
                            it.id == deleteTodo.id
                        }

                        if (index != -1){
                            todoList.removeAt(index)
                            todoAdapter.notifyItemRemoved(index)
                        }

                    }
                    .show()

            },
            onTodoClick = {
                clickedTodo ->
                val todoUpdateBottomSheet = TodoBottomSheetFragment(clickedTodo){todoTitle, todoContents->
                    val updatedTodo = clickedTodo.copy(
                        title = todoTitle,
                        contents = todoContents
                    )
                    val index = todoList.indexOfFirst {
                        it.id == updatedTodo.id
                    }
                    if (index == -1)
                        return@TodoBottomSheetFragment

                    todoList[index] = updatedTodo
                    todoAdapter.notifyItemChanged(index)
                }
                todoUpdateBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
            }
        )

        findViewById<ImageButton>(R.id.todoAddBtn).setOnClickListener {
            val todoBottomSheet = TodoBottomSheetFragment { todoTitle, todoContents ->
                val todo = Todo(
                    id = nextTodoId++,
                    title = todoTitle,
                    contents = todoContents
                )

                todoList.add(todo)
                todoAdapter.notifyItemInserted(todoList.lastIndex)
            }
            todoBottomSheet.show(supportFragmentManager, "TodoBottomSheet")
        }

        todoRecyclerView.adapter = todoAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}