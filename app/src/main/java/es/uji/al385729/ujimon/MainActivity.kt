package es.uji.al385729.ujimon

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        startButton = findViewById(R.id.startGameButton)

        startButton.setOnClickListener() {
            startGame(it)
        }
    }

    private fun startGame(it: View) {
        val intent = Intent(this, UjimonGameActivity::class.java)

        startActivity(intent)
    }
}