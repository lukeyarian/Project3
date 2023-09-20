package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficultyRG = findViewById<RadioGroup>(R.id.difficultyRG)
        val operationRG = findViewById<RadioGroup>(R.id.operationRG)
        val numQuestionsET = findViewById<EditText>(R.id.numQuestionsET)
        val start = findViewById<Button>(R.id.start)

        start.setOnClickListener {
            val difficulty = when(difficultyRG.checkedRadioButtonId) {
                R.id.easy -> "easy"
                R.id.medium -> "medium"
                R.id.hard -> "hard"
                else -> "easy"
            }

            val operation = when(operationRG.checkedRadioButtonId) {
                R.id.addition -> "addition"
                R.id.subtraction -> "subtraction"
                R.id.multiplication -> "multiplication"
                R.id.division -> "division"
                else -> "addition"
            }

            val numQuestions = numQuestionsET.text.toString().toIntOrNull() ?: 0


            //This is what I have learned about passing information between activities, the .java at the end is confusing
            //but it is necessary to turn the KClass into a Class and make the Intent information passing work.
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("operation", operation)
            intent.putExtra("numQuestions", numQuestions)
            startActivity(intent)
        }
    }
}