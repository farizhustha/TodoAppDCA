package com.dicoding.todoapp.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DatePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()

    private val viewModel: AddTaskViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var edtAddTitle: TextInputEditText
    private lateinit var edtAddDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)

        setupView()
        setupViewModelObserver()
    }

    private fun setupView() {
        edtAddTitle = findViewById(R.id.add_ed_title)
        edtAddDescription = findViewById(R.id.add_ed_description)
    }

    private fun setupViewModelObserver() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                val newTask = Task(
                    title = edtAddTitle.text.toString(),
                    description = edtAddDescription.text.toString(),
                    dueDateMillis = dueDateMillis
                )
                viewModel.insertTask(newTask)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}