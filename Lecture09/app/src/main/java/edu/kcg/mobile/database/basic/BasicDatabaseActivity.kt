package edu.kcg.mobile.database.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import edu.kcg.mobile.database.R

class BasicDatabaseActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_db)

        val db = DatabaseHandler(this, DB_NAME, 1)
        databaseManager = DatabaseManager(db)

        findViewById<Button>(R.id.button_save).setOnClickListener { saveValues() }
        findViewById<Button>(R.id.button_update).setOnClickListener { updateByName() }
        findViewById<Button>(R.id.button_delete).setOnClickListener { deleteByName() }
        findViewById<Button>(R.id.button_list_all).setOnClickListener { loadValues(it) }
    }

    private fun saveValues() {
        val name = findViewById<EditText>(R.id.input_name).text.toString()
        val age = findViewById<EditText>(R.id.input_age).text.let {
            it?.toString()?.toIntOrNull() ?: 0
        }

        val id = databaseManager.insertValues(User(name, age))

        if (id >= 0) {
            findViewById<EditText>(R.id.input_name).text.clear()
            findViewById<EditText>(R.id.input_age).text.clear()
            Toast.makeText(this, "The new record has ID=$id", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error occurred while saving the values", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateByName() {
        val name = findViewById<EditText>(R.id.input_name).text.toString()
        val age = findViewById<EditText>(R.id.input_age).text.let {
            it?.toString()?.toIntOrNull() ?: 0
        }

        val count = databaseManager.updateByName(User(name, age))
        val s = if (count == 1) "" else "s"
        Toast.makeText(this, "Updated $count record$s", Toast.LENGTH_LONG).show()
    }

    private fun deleteByName() {
        val name = findViewById<EditText>(R.id.input_name).text.toString()
        val count = databaseManager.deleteByName(name)
        val s = if (count == 1) "" else "s"
        Toast.makeText(this, "Deleted $count record$s", Toast.LENGTH_LONG).show()
    }

    private fun loadValues(view: View) {
        val users = databaseManager.getAll()
        val allUsers = users.joinToString(" ### ") { "${it.name} ${it.age}" }
        Snackbar.make(view, allUsers, Snackbar.LENGTH_LONG).show()
    }

}