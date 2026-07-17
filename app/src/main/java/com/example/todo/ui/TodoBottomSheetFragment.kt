package com.example.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.todo.R
import com.example.todo.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TodoBottomSheetFragment(
    private val todo: Todo? = null,
    private val onTodoSave : (String, String) -> Unit) : BottomSheetDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoTitle = view.findViewById<EditText>(R.id.inputTitle)
        val todoContent = view.findViewById<EditText>(R.id.todoContents)
        val saveBtn = view.findViewById<Button>(R.id.todoSaveBtn)

        if (todo != null){
            todoTitle.setText(todo.title)
            todoContent.setText(todo.contents)
            saveBtn.text = "수정"
        }

        saveBtn.setOnClickListener {
            val title = todoTitle.text.toString().trim()
            val contents = todoContent.text.toString().trim()

            if (title.isEmpty()){
                todoTitle.error = "할 일을 입력해 주세요"
                return@setOnClickListener
            }

            onTodoSave(title,contents)

            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_bottom_sheet, container, false)
    }
}