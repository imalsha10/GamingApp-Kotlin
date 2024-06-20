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
        val playerTwoEditText = findViewById<EditText>(R.id.playertwo)
        val gameBtn = findViewById<Button>(R.id.gameBtn)

        gameBtn.setOnClickListener {
            val getPlayerOneName = playerOneEditText.text.toString()
            val getPlayerTwoName = playerTwoEditText.text.toString()

            if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                Toast.makeText(this@Addplayers, "Please enter both player names", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@Addplayers, MainActivity::class.java)
                intent.putExtra("playerOne", getPlayerOneName)
                intent.putExtra("playerTwo", getPlayerTwoName)
                startActivity(intent)
            }
        }
    }
}
