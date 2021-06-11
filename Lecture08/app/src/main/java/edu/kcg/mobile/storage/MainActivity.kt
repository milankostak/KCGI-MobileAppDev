package edu.kcg.mobile.storage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<SwitchMaterial>(R.id.test_switch_1).isChecked = getBooleanValue(SWITCH1_ID)
        findViewById<SwitchMaterial>(R.id.test_switch_2).isChecked = getBooleanValue(SWITCH2_ID)

        findViewById<Button>(R.id.settings_button).setOnClickListener {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }
        findViewById<Button>(R.id.internal_storage_button).setOnClickListener {
            val i = Intent(this, InternalStorageActivity::class.java)
            startActivity(i)
        }
        findViewById<Button>(R.id.external_storage_button).setOnClickListener {
            val i = Intent(this, ExternalStorageActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        val signatureKey = resources.getString(R.string.settings_key_signature)
        val signatureValue = getDefaultSharedPreferences(this)
            .getString(signatureKey, "") ?: ""
        findViewById<TextView>(R.id.text_view).text = signatureValue
    }

    fun onSwitch1Change(view: View) {
        val isChecked = (view as SwitchMaterial).isChecked
        saveBooleanValue(SWITCH1_ID, isChecked)
    }

    fun onSwitch2Change(view: View) {
        val isChecked = (view as SwitchMaterial).isChecked
        saveBooleanValue(SWITCH2_ID, isChecked)
    }

    private fun getBooleanValue(id: String): Boolean {
        val sharedPreferences = getSharedPreferences()
        return sharedPreferences.getBoolean(id, false)
    }

    private fun saveBooleanValue(id: String, value: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(id, value)
        editor.apply()
    }

    private fun getSharedPreferences(): SharedPreferences {
//        MODE_MULTI_PROCESS
//        This method will check for modification of preferences even if the shared preference instance has already been loaded
//        MODE_PRIVATE
//        By setting this mode, the file can only be accessed using calling application
//        MODE_WORLD_READABLE
//        This mode allow other application to read the preferences
//        MODE_WORLD_WRITEABLE
//        This mode allow other application to write the preferences
        return getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFERENCES_NAME = "MyStoragePreferences"
        private const val SWITCH1_ID = "switch1"
        private const val SWITCH2_ID = "switch2"
    }

//    private fun saveBoolean(id: String, value: Boolean) {
//        val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean(id, value)
//        editor.apply()
//    }

//    private fun getBoolean(id: String, defaultValue: Boolean = true): Boolean {
//        val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
//        return sharedPreferences.getBoolean(id, defaultValue)
//    }

}