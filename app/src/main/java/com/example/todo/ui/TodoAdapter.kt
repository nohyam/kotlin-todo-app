package com.example.todo.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Todo

class TodoAdapter (private val todoList : List<Todo>, private val onTodoChecked : (Todo)->Unit) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    class TodoViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        val titleText = itemView.findViewById<TextView>(R.id.titleText)
        val todoCheckBox = itemView.findViewById<CheckBox>(R.id.todoCheckBox)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoAdapter.TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_items,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val todo = todoList[position]

        holder.titleText.text = todo.title
        holder.todoCheckBox.isChecked = todo.isCompleted
        holder.todoCheckBox.setOnClickListener {
            onTodoChecked(todo)
        }

        if (todo.isCompleted){
            holder.titleText.paintFlags =
                holder.titleText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.titleText.paintFlags =
                holder.titleText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

}