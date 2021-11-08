package com.fast.todoapp.ui.main.adapter

import android.view.View
import com.fast.todoapp.data.models.TaskEntity
import com.fast.todoapp.databinding.ItemTaskBinding
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class MainHolder(view: View) : RecyclerViewHolder<TaskEntity>(view) {
    val binding = ItemTaskBinding.bind(view)
}