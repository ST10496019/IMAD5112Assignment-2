package com.example.fca2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    private lateinit var scoreText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var reviewButton: Button
    private lateinit var exitButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        scoreText = findViewById(R.id.scoreText)
        feedbackText = findViewById(R.id.feedbackTexts)
        reviewButton = findViewById(R.id.reviewButton)
        exitButton = findViewById(R.id.exitButton)

        val score = intent.getIntExtra("score", 0)
        val questions = intent.getStringArrayExtra("questions")
        val answers = intent.getBooleanArrayExtra("answers")

        scoreText.text = "You scored $score out of 5"

        feedbackText.text = when {
            score >= 4 -> "Great job!"
            score >= 2 -> "Keep practising!"
            else -> "Don’t give up — try again!"
        }

        if (score < 3) {
            reviewButton.text = "Retry"
            reviewButton.setOnClickListener {
                val intent = Intent(this, FlashcardActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            reviewButton.setOnClickListener {
                showReviewDialog(questions, answers)
            }
        }

        exitButton.setOnClickListener {
            Toast.makeText(this, "Thanks for playing", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
    }

    private fun showReviewDialog(questions: Array<String>?, answers: BooleanArray?) {
        if (questions == null || answers == null) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Unable to load review data.")
                .setPositiveButton("OK", null)
                .show()
            return
        }

        val reviewMessage = StringBuilder()
        for (i in questions.indices) {
            reviewMessage.append("${i + 1}. ${questions[i]}\nAnswer: ${if (answers[i]) "True" else "False"}\n\n")
        }

        AlertDialog.Builder(this)
            .setTitle("Review Answers")
            .setMessage(reviewMessage.toString())
            .setPositiveButton("OK", null)
            .show()
    }
}



