package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {
    private var difficulty: Int = 0
    private var operation: Int = 0
    private var numQuestions: Int = 0
    private var currentScore = 0
    private var currentQuestion = 1
    private var correctAnswer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val questionTV = findViewById<TextView>(R.id.questionTV)
        val answerET = findViewById<EditText>(R.id.answerET)
        val submit = findViewById<Button>(R.id.submit)

        difficulty = intent.getIntExtra("difficulty", -1)
        operation = intent.getIntExtra("operation", -1)
        numQuestions = intent.getIntExtra("numQuestions", 1)

        generateQuestion(questionTV)

        submit.setOnClickListener {
            val userAnswer = answerET.text.toString().toIntOrNull() ?: 0
            if (userAnswer == correctAnswer) currentScore++

            if (currentQuestion < numQuestions) {
                currentQuestion++
                generateQuestion(questionTV)
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", currentScore)
                intent.putExtra("total", numQuestions)
                startActivity(intent)
            }
        }
    }

    private fun generateQuestion(questionTV: TextView) {
        val limit = when(difficulty) {
            R.id.easy -> 20
            R.id.medium -> 50
            R.id.hard -> 100
            else -> 20
        }

        val op1 = Random.nextInt(limit)
        val op2 = Random.nextInt(limit)

        when(operation) {
            R.id.addition -> {
                correctAnswer = op1 + op2
                questionTV.text = "$op1 + $op2"
            }
            R.id.subtraction -> {
                correctAnswer = op1 - op2
                questionTV.text = "$op1 - $op2"
            }
            R.id.multiplication -> {
                correctAnswer = op1 * op2
                questionTV.text = "$op1 × $op2"
            }
            R.id.division -> {
                if (op2 != 0) {
                    correctAnswer = op1 / op2
                    questionTV.text = "$op1 ÷ $op2"
                } else {
                    generateQuestion(questionTV) // recursive call to avoid division by zero
                }
            }
        }
    }
}