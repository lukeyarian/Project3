package com.example.project3

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    companion object {
        const val extraCorrect = "extraCorrectAnswers"
        const val extraTotal = "extraTotalQuestions"
    }

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

        val questionTV = findViewById<TextView>(R.id.questionTV)
        val answerET = findViewById<EditText>(R.id.answerET)
        val submit = findViewById<Button>(R.id.submit)

        submit.setOnClickListener {
            val userAnswer = answerET.text.toString().toIntOrNull()
            val isCorrect = userAnswer == correctAnswer
            if (isCorrect) {
                correctAnswers++
                Toast.makeText(this, "Correct. Good work!", Toast.LENGTH_SHORT).show()
                playSound(true)
            } else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show()
                playSound(false)
            }
            current++

            if (current < total - 1) {
                generateQuestion(difficulty, operation, questionTV, answerET)
            } else {
                val intent = Intent()
                intent.putExtra(extraCorrect, correctAnswers)
                intent.putExtra(extraTotal, total)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        generateQuestion(difficulty, operation, questionTV, answerET)
    }

    private fun playSound(isCorrect: Boolean) {
        if (isCorrect){
            val mediaPlayer = MediaPlayer.create(this, R.raw.correct)
            mediaPlayer.start()
            //mediaPlayer.release()
        }
        else{
            val mediaPlayer = MediaPlayer.create(this, R.raw.wrong)
            mediaPlayer.start()
            //mediaPlayer.release()
        }
    }

    //This is the function that will actually make the questions based on the answers from the previous activity.
    private fun generateQuestion(difficulty: String, operation: String, questionView: TextView, answerView: EditText) {
        val maxOperand = when (difficulty) {
            "easy" -> 10
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