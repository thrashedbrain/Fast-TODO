package com.fast.todoapp.ui.main.adapter

import android.graphics.Paint
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fast.todoapp.R
import com.fast.todoapp.data.models.TaskEntity
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class MainAdapter {

    fun getAdapter(
        recyclerView: RecyclerView,
        onCheckedListener: (TaskEntity) -> Unit,
        onLongListener: (TaskEntity) -> Unit
    ):
            ListAdapter<TaskEntity, RecyclerViewHolder<TaskEntity>> {
        val adapter = adapterOf<TaskEntity> {
            register(
                layoutResource = R.layout.item_task,
                viewHolder = ::MainHolder,
                onBindViewHolder = { mainHolder: MainHolder, i: Int, taskEntity: TaskEntity ->
                    mainHolder.binding.taskTxt.text = taskEntity.title
                    mainHolder.binding.checkbox.isChecked = taskEntity.checked
                    setTextStyle(taskEntity.checked, mainHolder.binding.taskTxt)
                    mainHolder.binding.checkbox.setOnClickListener {
                        onCheckedListener(taskEntity)
                    }
                    mainHolder.binding.root.setOnLongClickListener {
                        onLongListener(taskEntity)
                        true
                    }
                }
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
        return adapter
    }

    fun setTextStyle(b: Boolean, textView: TextView) {
        when (b) {
            true -> textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            false -> textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}