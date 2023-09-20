package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class QuizActivity : AppCompatActivity() {

    private var current = 0
    private var correctAnswers = 0
    private var total = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val difficulty = intent.getStringExtra("difficulty") ?: "easy"
        val operation = intent.getStringExtra("operation") ?: "addition"
        total = intent.getIntExtra("numQuestions", 1)

        val questionTextView = findViewById<TextView>(R.id.questionTV)
        val answerEditText = findViewById<EditText>(R.id.answerET)
        val submit = findViewById<Button>(R.id.submit)

        submit.setOnClickListener {
            val userAnswer = answerEditText.text.toString().toIntOrNull()

            if (userAnswer == correctAnswer) {
                correctAnswers++
            }

            current++

            if (current < total) {
                generateQuestion(difficulty, operation, questionTextView, answerEditText)
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", correctAnswers)
                intent.putExtra("total", total)
                startActivity(intent)
            }
        }

        generateQuestion(difficulty, operation, questionTextView, answerEditText)
    }

    private fun generateQuestion(difficulty: String, operation: String, questionView: TextView, answerView: EditText) {
        val maxOperand = when (difficulty) {
            "medium" -> 25
            "hard" -> 50
            else -> 10
        }

        val operand1 = (1..maxOperand).random()
        val operand2 = (1..maxOperand).random()

        correctAnswer = when (operation) {
            "addition" -> {
                questionView.text = "$operand1 + $operand2 = ?"
                operand1 + operand2
            }
            "subtraction" -> {
                questionView.text = "$operand1 - $operand2 = ?"
                operand1 - operand2
            }
            "multiplication" -> {
                questionView.text = "$operand1 × $operand2 = ?"
                operand1 * operand2
            }
            "division" -> {
                questionView.text = "$operand1 ÷ $operand2 = ?"
                operand1 / operand2
            }
            else -> {
                questionView.text = "$operand1 + $operand2 = ?"
                operand1 + operand2
            }
        }

        answerView.text.clear()
    }
}