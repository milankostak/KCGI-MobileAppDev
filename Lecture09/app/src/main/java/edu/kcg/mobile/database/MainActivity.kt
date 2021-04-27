package edu.kcg.mobile.database

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.kcg.mobile.database.basic.BasicDatabaseActivity
import edu.kcg.mobile.database.room.RoomActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_go_to_basic).setOnClickListener {
            val i = Intent(this, BasicDatabaseActivity::class.java)
            startActivity(i)
        }
        findViewById<Button>(R.id.button_go_to_room).setOnClickListener {
            val i = Intent(this, RoomActivity::class.java)
            startActivity(i)
        }
    }

}