package c14220080.example.taskapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateActivity : AppCompatActivity() {

    private lateinit var edit_name: EditText
    private lateinit var edit_description: EditText
    private lateinit var edit_date: EditText
    private lateinit var edit_button: Button
//    private lateinit var delete_button: Button

    var id: String? = null
    var name: String? = null
    var description: String? = null
    var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        edit_name = findViewById<EditText>(R.id.edit_name)
        edit_description = findViewById<EditText>(R.id.edit_description)
        edit_date = findViewById<EditText>(R.id.edit_date)
        edit_button = findViewById<Button>(R.id.edit_button)
//        delete_button = findViewById<Button>(R.id.delete_button)

        getAndSetIntentData()

        edit_date.setOnClickListener {
            showDatePicker()
        }

        edit_button.setOnClickListener {

            val myDB = MyDatabase(this@UpdateActivity)
            name = edit_name.text.toString().trim()
            date = edit_date.text.toString().trim()
            description = edit_description.text.toString().trim()
            myDB.updateData(id,name,date,description)

            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)

            // Tutup AddActivity agar tidak menumpuk di back stack
            finish()
        }


//        delete_button.setOnClickListener {
//            confirmDialog()
//        }
    }


    private fun getAndSetIntentData() {
        if (getIntent() .hasExtra("id") && getIntent() .hasExtra("name") && getIntent().hasExtra("date") &&
            getIntent().hasExtra("description")) {
            // Getting data from intent
            id = getIntent().getStringExtra("id")
            name = getIntent().getStringExtra("name")
            date = getIntent().getStringExtra("date")
            description = getIntent().getStringExtra("description")

            // Setting data

            edit_name.setText(name)
            edit_date.setText(date)
            edit_description.setText(description)
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val currentDate = java.util.Calendar.getInstance()
        val year = currentDate.get(java.util.Calendar.YEAR)
        val month = currentDate.get(java.util.Calendar.MONTH)
        val day = currentDate.get(java.util.Calendar.DAY_OF_MONTH)

        // Tampilkan DatePickerDialog
        val datePickerDialog = android.app.DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal menjadi "YYYY-MM-DD"
                val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                edit_date.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


//    private fun confirmDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Delete $name?")
//        builder.setMessage("Are you sure you want to delete $name?")
//        builder.setPositiveButton("Yes") { dialog, which ->
//            val myDB = MyDatabase(this@UpdateActivity)
//            myDB.deleteOneRow(id)
//            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        builder.setNegativeButton("No") { dialog, which ->
//            dialog.dismiss()
//        }
//        builder.create().show()
//    }
}