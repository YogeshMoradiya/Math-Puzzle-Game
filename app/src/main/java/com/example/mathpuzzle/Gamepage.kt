package com.example.mathpuzzle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.os.CountDownTimer
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class Gamepage : AppCompatActivity() {

    var hintClicked = false
    lateinit var puzzeleboard: TextView
    lateinit var levelboard: TextView
    lateinit var taxtview: TextView
    lateinit var delete: TextView
    lateinit var submit: TextView
    lateinit var skip: ImageView
    lateinit var hint: ImageView
    lateinit var mAdView: AdView
    lateinit var blackbox: RelativeLayout

    var array= arrayOf(R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,
        R.drawable.p6,R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,
        R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15,R.drawable.p16,R.drawable.p17,
        R.drawable.p18,R.drawable.p19,R.drawable.p20,R.drawable.p21,R.drawable.p22,R.drawable.p23,
        R.drawable.p24,R.drawable.p25,R.drawable.p26,R.drawable.p27,R.drawable.p28,R.drawable.p29,
        R.drawable.p30,R.drawable.p31, R.drawable.p32,R.drawable.p33,R.drawable.p34,R.drawable.p35,
        R.drawable.p36,R.drawable.p37,R.drawable.p38,R.drawable.p39,R.drawable.p40,R.drawable.p41,
        R.drawable.p42,R.drawable.p43,R.drawable.p44,R.drawable.p45,R.drawable.p46,R.drawable.p47,
        R.drawable.p48,R.drawable.p49,R.drawable.p50,R.drawable.p51,R.drawable.p52,R.drawable.p53,
        R.drawable.p54,R.drawable.p55,R.drawable.p56,R.drawable.p57,R.drawable.p58,R.drawable.p59,
        R.drawable.p60,R.drawable.p61,R.drawable.p62,R.drawable.p63,R.drawable.p64,R.drawable.p65,
        R.drawable.p66,R.drawable.p67,R.drawable.p68,R.drawable.p69,R.drawable.p70,R.drawable.p71,
        R.drawable.p72,R.drawable.p73,R.drawable.p74,R.drawable.p75)


    var answer= arrayOf("1","2","3","4","5", "6","7","8","9","10","11","12", "13","14","15","16",
        "17","18","19", "20","21","22","23","24","25","26", "27","28","29","30","31","32","33",
        "34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51"
        ,"52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69",
        "70","71","72","73","74","75")

    private var mInterstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamepage)

        mAdView = findViewById(R.id.adView)
        puzzeleboard = findViewById(R.id.puzzeleboard)
        levelboard = findViewById(R.id.levelboard)
        taxtview = findViewById(R.id.taxtview)
        submit = findViewById(R.id.submit)
        skip = findViewById(R.id.skip)
        hint = findViewById(R.id.hint)
        delete = findViewById(R.id.delete)
        blackbox = findViewById(R.id.blackbox)

        val ad = AdRequest.Builder().build()
        mAdView.loadAd(ad)


        var n = intent.getIntExtra("level", 0)

        levelboard.setText("Puzzle " + n)
        puzzeleboard.setBackgroundResource(array[n - 1])


        var buttons = listOf<Button>(
            findViewById(R.id.one),
            findViewById(R.id.two),
            findViewById(R.id.three),
            findViewById(R.id.four),
            findViewById(R.id.five),
            findViewById(R.id.six),
            findViewById(R.id.seven),
            findViewById(R.id.eight),
            findViewById(R.id.nine),
            findViewById(R.id.zero)
        )

        for (button in buttons) {
            button.setOnClickListener {
                taxtview.setText(taxtview.text.toString() + button.text.toString())
            }
        }

        hint.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            val sharedPreferences = getSharedPreferences("TimerPrefs", Context.MODE_PRIVATE)
            val storedTime = sharedPreferences.getLong("storedTime", -1)

            // If there is no stored time or the stored time is too old (more than 1 minute), reset the hintClicked flag and store the current time
            if (storedTime == -1L || currentTime - storedTime > 60000) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Hint")
                builder.setMessage(answer[n - 1])
                builder.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
                hintClicked = true
                sharedPreferences.edit().putLong("storedTime", currentTime).apply()
            } else {
                // Calculate the remaining time
                val remainingTime = 60000 - (currentTime - storedTime)
                Toast.makeText(
                    this,
                    "Timer set for ${remainingTime / 1000} seconds",
                    Toast.LENGTH_SHORT
                ).show()
                // Start a timer with the remaining time
                object : CountDownTimer(remainingTime, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        // Do nothing on tick
                    }

                    override fun onFinish() {
                        hintClicked = false
                        Toast.makeText(this@Gamepage, "Hint timer expired", Toast.LENGTH_SHORT)
                            .show()
                    }
                }.start()
            }
        }


        delete.setOnClickListener {
            try {
                taxtview.setText(taxtview.text.substring(0, taxtview.text.length - 1))
            } catch (k: StringIndexOutOfBoundsException) {
            }
        }

        var re = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            re,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("===", "Ad was loaded.")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })


        submit.setOnClickListener {

            if (taxtview.text.equals(answer[n - 1])) {

                Log.d("=====", "onCreate: n = $n")
                Log.d(
                    "=====",
                    "onCreate: n = ${MainActivity.sp.getString("key${n}", "lock")}"
                )

                if (MainActivity.sp.getString("key${n}", "lock")
                        .equals(MainActivity.lock)
                ) {
                    MainActivity.edit.putInt("mylevel", n + 1)
                    MainActivity.edit.apply()
                }
//                Log.d("=====", "onCreate: n = ${MainActivity.sp.getInt("mylevel")}")
                MainActivity.edit.putString("key${n}", MainActivity.complate)
                MainActivity.edit.apply()
                n++
                var s = Intent(this@Gamepage, Winnerpage::class.java).putExtra("level", n)
                startActivity(s)
                finish()
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }
            } else if (taxtview.text.isEmpty()) {
            } else {
                Toast.makeText(this@Gamepage, "Wrong", Toast.LENGTH_SHORT).show()
            }
        }


//        skip.setOnClickListener {
//            n++
//            if (MainActivity.sp.getString("key${n - 1}", "lock").equals(MainActivity.lock)) {
//                MainActivity.edit.putInt("mylevel", n)
//                MainActivity.edit.apply()
//            }
//            if (!MainActivity.sp.getString("key${n - 1}", "lock").equals(MainActivity.complate)) {
//                MainActivity.edit.putString("key${n - 1}", MainActivity.skip)
//                MainActivity.edit.apply()
//            }
//            var skip = Intent(this@Gamepage,Gamepage::class.java).putExtra("level", n)
//            startActivity(skip)
//            finish()
//
//        }
//    }


//        Reward ad
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("++++", "Ad not loaded.")
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("----", "Ad was loaded.")
                    rewardedAd = ad

                }
            })
        skip.setOnClickListener {
            rewardedAd?.let { ad ->
                ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    Log.d("===", "User earned the reward.")
                    n++
                    if (MainActivity.sp.getString("key${n - 1}", "lock")
                            .equals(MainActivity.lock)
                    ) {
                        MainActivity.edit.putInt("mylevel", n)
                        MainActivity.edit.apply()
                    }
                    if (!MainActivity.sp.getString("key${n - 1}", "lock")
                            .equals(MainActivity.complate)
                    ) {
                        MainActivity.edit.putString("key${n - 1}", MainActivity.skip)
                        MainActivity.edit.apply()
                    }
                    var skip = Intent(this@Gamepage, Gamepage::class.java).putExtra("level", n)
                    startActivity(skip)
                    finish()
                })
            } ?: run {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}
