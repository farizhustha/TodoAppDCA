package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var edtDetailTitle: TextInputEditText
    private lateinit var edtDetailDescription: TextInputEditText
    private lateinit var edtDetailDue: TextInputEditText
    private lateinit var btnDetailDelete: Button
    private val viewModel: DetailTaskViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        setupView()
        setupViewModelObserver()
    }

    private fun setupView() {
        edtDetailTitle = findViewById(R.id.detail_ed_title)
        edtDetailDescription = findViewById(R.id.detail_ed_description)
        edtDetailDue = findViewById(R.id.detail_ed_due_date)
        btnDetailDelete = findViewById(R.id.btn_delete_task)

        btnDetailDelete.setOnClickListener {
            viewModel.deleteTask()
            finish()
        }
    }

    private fun setupViewModelObserver() {
        val id = intent.getIntExtra(TASK_ID, 0)
        viewModel.setTaskId(id)
        viewModel.task.observe(this) { task ->
            if (task != null) {
                edtDetailTitle.setText(task.title)
                edtDetailDescription.setText(task.description)
                edtDetailDue.setText(DateConverter.convertMillisToString(task.dueDateMillis))
            }
        }
    }
}