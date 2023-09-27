package com.example.project3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val request = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.start)
        //val resultTextView = findViewById<TextView>(R.id.resultTV)
        val numQuestionsET = findViewById<EditText>(R.id.numQuestionsET)

        startButton.setOnClickListener {
            val numQuestions = numQuestionsET.text.toString().toInt()
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("numQuestions", numQuestions)
            startActivityForResult(intent, request)
        }
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == request && resultCode == Activity.RESULT_OK) {
            val correctAnswers = data?.getIntExtra(QuizActivity.extraCorrect, 0) ?: 0
            val totalQuestions = data?.getIntExtra(QuizActivity.extraTotal, 1) ?: 1

            val percentage = (correctAnswers.toDouble() / totalQuestions) * 100
            val resultTextView = findViewById<TextView>(R.id.resultTV)
            if (percentage >= 80) {
                resultTextView.setTextColor(Color.GREEN)
                resultTextView.text = "Congrats! You scored $correctAnswers out of $totalQuestions."
            } else {
                resultTextView.setTextColor(Color.RED)
                resultTextView.text = "Sorry, You only scored $correctAnswers out of $totalQuestions."
            }
        }
    }
}
