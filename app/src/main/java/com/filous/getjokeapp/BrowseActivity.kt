package com.filous.getjokeapp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


class BrowseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        val backBtn = findViewById<Button>(R.id.back_btn)
        val jokesTextView = findViewById<TextView>(R.id.saved_jokes)

        jokesTextView.movementMethod = ScrollingMovementMethod()

        getLocalData()

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getLocalData() {
        var fileInputStream: FileInputStream? = null
        fileInputStream = openFileInput("joke.txt")
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
        val jokesTextView = findViewById<TextView>(R.id.saved_jokes)
        jokesTextView.text = stringBuilder.toString().toString()
    }
}