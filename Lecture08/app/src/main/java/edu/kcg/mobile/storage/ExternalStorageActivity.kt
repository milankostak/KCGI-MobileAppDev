package edu.kcg.mobile.storage

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.io.*

class ExternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external)

        findViewById<Button>(R.id.save_external).setOnClickListener { saveTextToExternalFile() }
        findViewById<Button>(R.id.load_external).setOnClickListener { loadTextFromExternalFile(it) }
    }

    private fun saveTextToExternalFile() {
        // checking the availability state of the external Storage
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            Toast.makeText(this, "Media not mounted", Toast.LENGTH_LONG).show()
            return
        }

        val editText = findViewById<EditText>(R.id.external_storage_text)
        val text = editText.text.toString()

        val fileBasic = File(getExternalFilesDir(null), FILENAME_BASIC_EX)
        FileOutputStream(fileBasic, false).use {
            it.write(text.toByteArray())
        }

        val fileObject = File(getExternalFilesDir(null), FILENAME_OBJECT_EX)
        ObjectOutputStream(FileOutputStream(fileObject, false)).use {
            it.writeObject(Message(text))
        }

        editText.text.clear()
    }

    private fun loadTextFromExternalFile(view: View) {
        try {
            // checking the availability state of the external Storage
            val state = Environment.getExternalStorageState()
            if (Environment.MEDIA_MOUNTED != state) {
                Toast.makeText(this, "Media not mounted", Toast.LENGTH_LONG).show()
                return
            }

            val fileBasic = File(getExternalFilesDir(null), FILENAME_BASIC_EX)
            val text1 = FileInputStream(fileBasic).use {
                String(it.readBytes())
            }

            val fileObject = File(getExternalFilesDir(null), FILENAME_OBJECT_EX)
            val text2 = ObjectInputStream(FileInputStream(fileObject)).use {
                val readObject = it.readObject()
                (readObject as Message).text
            }
            Snackbar
                .make(view, "$text1 // $text2", Snackbar.LENGTH_LONG)
                .show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "Files not found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val FILENAME_BASIC_EX = "testFileBasicEx.txt"
        private const val FILENAME_OBJECT_EX = "testFileObjectEx.bin"
    }

}