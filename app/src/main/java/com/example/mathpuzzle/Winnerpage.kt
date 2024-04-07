package com.example.mathpuzzle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Winnerpage : AppCompatActivity() {

    lateinit var wintext: TextView
    lateinit var wincontinue: Button
    lateinit var winmainmanu: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winnerpage)

        wintext = findViewById(R.id.wintext)
        wincontinue = findViewById(R.id.wincontinue)
        winmainmanu = findViewById(R.id.winmainmanu)


        var n = intent.getIntExtra("level", 0)
        wintext.setText("Puzzle " + (n - 1).toString() + " Completed")


        wincontinue.setOnClickListener {
                var intent = Intent(this@Winnerpage, Gamepage::class.java).putExtra("level", n)
                startActivity(intent)
                finish()
            }

        winmainmanu.setOnClickListener {
            var intent = Intent(this@Winnerpage,MainActivity::class.java).putExtra("level", n)
            startActivity(intent)
            finish()

        }

    }

}