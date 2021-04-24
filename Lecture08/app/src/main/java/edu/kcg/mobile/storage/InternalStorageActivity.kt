package edu.kcg.mobile.storage

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class InternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal)

        findViewById<Button>(R.id.save_to_file).setOnClickListener { saveTextToInternalFile() }
        findViewById<FloatingActionButton>(R.id.float_button).setOnClickListener {
            loadTextFromInternalFile(it)
        }
    }

    private fun saveTextToInternalFile() {
        val fileText = findViewById<TextView>(R.id.file_text).text.toString()
        openFileOutput(FILENAME_BASIC_EX, Context.MODE_PRIVATE).use {
            it.write(fileText.toByteArray())
        }
        ObjectOutputStream(openFileOutput(FILENAME_OBJECT_EX, Context.MODE_PRIVATE)).use {
            it.writeObject(Message(fileText))
        }
    }

    private fun loadTextFromInternalFile(view: View) {
        try {
            val text1 = openFileInput(FILENAME_BASIC_EX).use {
                String(it.readBytes())
            }
            val text2 = ObjectInputStream(openFileInput(FILENAME_OBJECT_EX)).use {
                val readObject = it.readObject()
                (readObject as Message).text
            }
            Snackbar
                .make(view, "$text1 / $text2", Snackbar.LENGTH_LONG)
                .show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "Files not found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val FILENAME_BASIC_EX = "testFileBasicIn.txt"
        private const val FILENAME_OBJECT_EX = "testFileObjectIn.bin"
    }

}