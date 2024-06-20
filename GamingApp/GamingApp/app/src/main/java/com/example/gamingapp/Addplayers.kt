package com.example.gamingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Addplayers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addplayers)

        val playerOneEditText = findViewById<EditText>(R.id.playerone)
        val gameBtn = findViewById<Button>(R.id.gameBtn)

        gameBtn.setOnClickListener {
            val getPlayerOneName = playerOneEditText.text.toString()

            if (getPlayerOneName.isEmpty()) {
                Toast.makeText(this@Addplayers, "Please enter player one name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@Addplayers, MainActivity::class.java)
                intent.putExtra("playerOne", getPlayerOneName)
                startActivity(intent)
            }
        }
    }
}
