package c14220080.example.taskapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar


class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var _addButton = findViewById<Button>(R.id.add_button);
        var task_name = findViewById<EditText>(R.id.task_name);
        var task_description = findViewById<EditText>(R.id.task_description);
        var task_date = findViewById<EditText>(R.id.task_date);

        task_date.setOnClickListener { v ->
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this@AddActivity,
                { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    // Format tanggal (YYYY-MM-DD)
                    val selectedDate =
                        selectedYear.toString() + "-" + (selectedMonth + 1) + "-" + selectedDay
                    task_date.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        _addButton.setOnClickListener {
            val myDB = MyDatabase(this@AddActivity)
            myDB.addTask(task_name.text.toString().trim(),
                task_description.text.toString().trim(),
                task_date.text.toString().trim())

            val intent = Intent(this@AddActivity, MainActivity::class.java)
            startActivity(intent)

            // Close AddActivity so it doesn't stack up in the back stack
            finish()
        }
    }
}