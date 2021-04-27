package edu.kcg.mobile.database.room

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import edu.kcg.mobile.database.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RoomActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "room_db2"
        ).build()
        userDao = db.userDao()

        findViewById<Button>(R.id.button_save_room).setOnClickListener { launch { saveValues() } }
        findViewById<Button>(R.id.button_update_room).setOnClickListener { launch { updateByName() } }
        findViewById<Button>(R.id.button_delete_room).setOnClickListener { launch { deleteByName() } }
        findViewById<Button>(R.id.button_list_all_room).setOnClickListener { loadValues(it) }
    }

    private suspend fun saveValues() {
        val name = findViewById<EditText>(R.id.input_name_room).text.toString()
        val age = findViewById<EditText>(R.id.input_age_room).text.let {
            if (it.isNotEmpty()) it.toString().toInt() else 0
        }
        val id = withContext(Dispatchers.IO) {
            userDao.insert(User(name, age))
        }
        withContext(Dispatchers.Main) {
            // ID of -1 means an error
            if (id >= 0) {
                findViewById<EditText>(R.id.input_name_room).text.clear()
                findViewById<EditText>(R.id.input_age_room).text.clear()
                Toast.makeText(this@RoomActivity, "The new record has ID $id", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@RoomActivity, "Error occurred while saving the data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun updateByName() {
        val name = findViewById<EditText>(R.id.input_name_room).text.toString()
        val age = findViewById<EditText>(R.id.input_age_room).text.let {
            if (it.isNotEmpty()) it.toString().toInt() else 0
        }
        val count = withContext(Dispatchers.IO) {
            val users = userDao.findByName(name)
            users.forEach { it.age = age }
            userDao.update(users)
        }
        withContext(Dispatchers.Main) {
            val s = if (count == 1) "" else "s"
            Toast.makeText(this@RoomActivity, "Updated $count record$s", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun deleteByName() {
        val name = findViewById<EditText>(R.id.input_name_room).text.toString()
        val count = withContext(Dispatchers.IO) {
            userDao.delete(userDao.findByName(name))
        }
        withContext(Dispatchers.Main) {
            val s = if (count == 1) "" else "s"
            Toast.makeText(this@RoomActivity, "Deleted $count record$s", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadValues(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val users = userDao.getAll()
            val allUsers = users.joinToString(" ### ") { "${it.name} ${it.age}" }
            Snackbar.make(view, allUsers, Snackbar.LENGTH_LONG).show()
        }
    }

}