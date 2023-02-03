package com.filous.getjokeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileOutputStream

const val BASE_PATH = "https://v2.jokeapi.dev/"
const val FILE_NAME = "joke.txt"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        var jokeVariant:Number = 0

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val getBtn = findViewById<Button>(R.id.get_btn)
        val saveBtn = findViewById<Button>(R.id.save_btn)
        val browseBtn = findViewById<Button>(R.id.browse_btn)
        val jokeText = findViewById<TextView>(R.id.joke_text)

        radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            if (checkedId ==R.id.radio_one)
                jokeVariant = 0
            if (checkedId ==R.id.radio_two)
                jokeVariant = 1
            if (checkedId ==R.id.radio_three)
                jokeVariant = 2
        }

        getBtn.setOnClickListener{
            getJokeData(jokeVariant)
        }

        saveBtn.setOnClickListener{
            val jokeTextVar:String = jokeText.text.toString()
            val fileOutputStream:FileOutputStream
            try {
                fileOutputStream = openFileOutput("joke.txt", Context.MODE_PRIVATE)
                fileOutputStream.write(jokeTextVar.toByteArray())
                Toast.makeText(this, "Joke saved", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        browseBtn.setOnClickListener {
            val intent = Intent(this, BrowseActivity::class.java)
            startActivity(intent);
        }

    }

    private fun getJokeData(jokeVariant: Number) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_PATH)
            .build()
            .create(Api::class.java)

        var retrofitData = retrofitBuilder.getRandomData();
        if (jokeVariant == 1) retrofitData = retrofitBuilder.getGeekData()
        if (jokeVariant == 2) retrofitData = retrofitBuilder.getDarkData()

        retrofitData.enqueue(object : Callback<DataType?> {
            override fun onResponse(call: Call<DataType?>, response: Response<DataType?>) {
                val responseBody = response.body()!!
                if (!responseBody.error) {
                    val jokeText = findViewById<TextView>(R.id.joke_text)
                    if (responseBody.joke !== null) jokeText.text = responseBody.joke
                    else jokeText.text = responseBody.setup + "\n" + responseBody.delivery
                }
            }

            override fun onFailure(call: Call<DataType?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }


}