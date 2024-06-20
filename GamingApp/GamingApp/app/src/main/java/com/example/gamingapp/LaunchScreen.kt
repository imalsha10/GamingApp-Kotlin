package com.example.gamingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val imageStart = findViewById<ImageView>(R.id.imageStart)

        imageStart.setOnClickListener {
            val intent = Intent(this, Addplayers::class.java)
            startActivity(intent)
        }
    }
}
