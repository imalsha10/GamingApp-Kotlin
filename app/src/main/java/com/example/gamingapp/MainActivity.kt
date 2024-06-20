package com.example.gamingapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var playerOneActive = true
    private lateinit var playerOneScore: TextView
    private lateinit var playerTwoScore: TextView
    private lateinit var playerStatus: TextView
    private val buttons = Array<Button?>(9) { null }
    private lateinit var reset: Button
    private lateinit var playagain: Button
    private val gameState = IntArray(9) { 2 }
    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )
    private var rounds = 0
    private var playerOneScoreCount = 0
    private var playerTwoScoreCount = 0
    private var playerOneName: String = ""
    private var playerTwoName: String = ""
    private val playerScores = mutableListOf<PlayerScore>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerOneScore = findViewById(R.id.score_Player1)
        playerTwoScore = findViewById(R.id.score_Player2)
        playerStatus = findViewById(R.id.textStatus)
        reset = findViewById(R.id.btn_reset)
        playagain = findViewById(R.id.btn_play_again)

        for (i in buttons.indices) {
            buttons[i] = findViewById(resources.getIdentifier("btn$i", "id", packageName))
            buttons[i]?.setOnClickListener(this)
        }

        playerOneActive = true
        rounds = 0

        reset.setOnClickListener {
            playAgain()
            playerOneScoreCount = 0
            playerTwoScoreCount = 0
            updatePlayerScore()
        }

        playagain.setOnClickListener {
            playAgain()
        }

        // Retrieve player names from intent extras
        playerOneName = intent.getStringExtra("playerOne") ?: "Player 1"
        playerTwoName = intent.getStringExtra("playerTwo") ?: "Player 2"

        // Set player names to the score TextViews
        playerOneScore.text = "$playerOneName Score"
        playerTwoScore.text = "$playerTwoName Score"
    }

    override fun onClick(view: View) {
        if ((view as Button).text.toString() != "") {
            return
        } else if (checkWinner()) {
            return
        }
        val buttonID = resources.getResourceEntryName(view.id)
        val gameStatePointer = buttonID.substring(buttonID.length - 1).toInt()

        if (playerOneActive) {
            view.text = "X"
            view.setTextColor(Color.parseColor("#ffc34a"))
            gameState[gameStatePointer] = 0
        } else {
            view.text = "O"
            view.setTextColor(Color.parseColor("#70fc3a"))
            gameState[gameStatePointer] = 1
        }
        rounds++

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++
                playerScores.add(PlayerScore(playerOneName, playerOneScoreCount))
                updatePlayerScore()
                playerStatus.text = "$playerOneName has won"
            } else {
                playerTwoScoreCount++
                playerScores.add(PlayerScore(playerTwoName, playerTwoScoreCount))
                updatePlayerScore()
                playerStatus.text = "$playerTwoName has won"
            }
        } else if (rounds == 9) {
            playerStatus.text = "No Winner"
        } else {
            playerOneActive = !playerOneActive
        }
    }


    private fun checkWinner(): Boolean {
        var winnerResults = false
        for (winningPositions in winningPositions) {
            if (gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                gameState[winningPositions[0]] != 2
            ) {
                winnerResults = true
            }
        }
        return winnerResults
    }
    fun showHighScores(view: View) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("High Scores")

        // Add a table layout to the dialog to display high scores
        val layout = TableLayout(this)
        val layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        layout.layoutParams = layoutParams

        // Add table rows for each player's score
        for (i in 0 until playerScores.size) {
            val playerScore = playerScores[i]
            val row = TableRow(this)
            val nameTextView = TextView(this)
            val scoreTextView = TextView(this)
            nameTextView.text = playerScore.name
            scoreTextView.text = playerScore.score.toString()
            row.addView(nameTextView)
            row.addView(scoreTextView)
            layout.addView(row)
        }

        dialog.setView(layout)
        dialog.setPositiveButton("OK", null)
        dialog.show()
    }


    private fun playAgain() {
        rounds = 0
        playerOneActive = true
        for (i in buttons.indices) {
            gameState[i] = 2
            buttons[i]?.text = ""
        }
        playerStatus.text = "Status"
    }

    private fun updatePlayerScore() {
        playerOneScore.text = playerOneScoreCount.toString()
        playerTwoScore.text = playerTwoScoreCount.toString()
    }
}
