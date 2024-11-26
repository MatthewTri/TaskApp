package c14220080.example.taskapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var _rv : RecyclerView
    private lateinit var myDB : MyDatabase

    // Global variables to store data
    private val task_id = ArrayList<String>()
    private val task_name = ArrayList<String>()
    private val task_description = ArrayList<String>()
    private val task_date = ArrayList<String>()
    private val isTaskStarted = ArrayList<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myDB = MyDatabase(this)


        var _addButton = findViewById<FloatingActionButton>(R.id.add_button)

        _rv = findViewById<RecyclerView>(R.id.recyclerView)

        _addButton.setOnClickListener {
            // Add a new item to the RecyclerView
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        storeData()

        val taskAdapter = TaskAdapter(this@MainActivity, task_id, task_name, task_description, task_date)
        _rv.adapter = taskAdapter
        _rv.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun storeData() {
        val cursor: Cursor = myDB.readAllData()
        if (cursor.count == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0))
                task_name.add(cursor.getString(1))
                task_date.add(cursor.getString(3))
                task_description.add(cursor.getString(2))
//                isTaskStarted.add(cursor.getInt(4) == 1);
            }
        }
    }
}