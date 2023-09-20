package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val scoreTV = findViewById<TextView>(R.id.scoreTV)
        val retry = findViewById<Button>(R.id.retry)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        //Display total score... Main component of activity
        scoreTV.text = "Your score: $score out of $total"

        //This is the button that lets you restart the quiz
        retry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}