package edu.kcg.mobile.storage

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.*

class ExternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external)

        findViewById<Button>(R.id.save_to_file).setOnClickListener { saveTextToExternalFile() }
        findViewById<FloatingActionButton>(R.id.float_button).setOnClickListener {
            loadTextFromExternalFile(it)
        }
    }

    private fun saveTextToExternalFile() {
        // checking the availability state of the external Storage
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return
        }

        val fileText = findViewById<TextView>(R.id.file_text).text.toString()

        val fileBasic = File(getExternalFilesDir(null), FILENAME_BASIC_IN)
        FileOutputStream(fileBasic, false).use {
            it.write(fileText.toByteArray())
        }

        val fileObject = File(getExternalFilesDir(null), FILENAME_OBJECT_IN)
        ObjectOutputStream(FileOutputStream(fileObject, false)).use {
            it.writeObject(Message(fileText))
        }
    }

    private fun loadTextFromExternalFile(view: View) {
        try {
            // checking the availability state of the external Storage
            val state = Environment.getExternalStorageState()
            if (Environment.MEDIA_MOUNTED != state) {
                return
            }

            val fileBasic = File(getExternalFilesDir(null), FILENAME_BASIC_IN)
            val text1 = FileInputStream(fileBasic).use {
                String(it.readBytes())
            }

            val fileObject = File(getExternalFilesDir(null), FILENAME_OBJECT_IN)
            val text2 = ObjectInputStream(FileInputStream(fileObject)).use {
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
        private const val FILENAME_BASIC_IN = "testFileBasicEx.txt"
        private const val FILENAME_OBJECT_IN = "testFileObjectEx.bin"
    }

}