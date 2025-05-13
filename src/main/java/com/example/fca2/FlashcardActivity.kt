package com.example.fca2

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FlashcardActivity : AppCompatActivity() {

    private val questions = arrayOf(
        "Are humans smarter then AI",
        "Kaizer Chiefs is bigger then Mamelodi Sundowns",
        "Bafana Bafana can win the World Cup in 2026",
        "The derby in Spain is Barcelona vs Real Madrid",
        "Did Nelson Mandela fight for france"
    )

    private val answers = booleanArrayOf(false, false, true, true, false)

    private var currentIndex = 0
    private var score = 0

    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)

        loadQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex++
            if (currentIndex < questions.size) {
                loadQuestion()
            } else {
                // Ensure questions are cast explicitly as StringArray
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("questions", questions as Array<String>)
                intent.putExtra("answers", answers)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        // Prevent crashing if index goes out of range
        if (currentIndex < questions.size) {
            questionText.text = questions[currentIndex]
            feedbackText.text = ""

            // Re-enable buttons for new question
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correct = answers[currentIndex]
        if (userAnswer == correct) {
            feedbackText.text = "Correct!"
            score++
        } else {
            feedbackText.text = "Incorrect"
        }

        // Disable buttons after answering
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }
}


