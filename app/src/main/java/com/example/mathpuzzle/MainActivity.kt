package com.example.mathpuzzle

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd


class MainActivity : AppCompatActivity() {

        companion object {

            lateinit var sp: SharedPreferences
            lateinit var edit: SharedPreferences.Editor
            var lock="lock"
            var skip="skip"
            var complate="complate"
        }

        lateinit var Continue: TextView
        lateinit var Puzzles: TextView
        lateinit var gmail: ImageView
        lateinit var share: ImageView
        var n = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Continue=findViewById(R.id.Continue)
        Puzzles=findViewById(R.id.Puzzles)
        gmail=findViewById(R.id.gmail)
        share=findViewById(R.id.share)


        sp = getSharedPreferences("hello", MODE_PRIVATE)
        edit = sp.edit()


        for(i in 1..75)
        {
            if(sp.getString("key$i", lock).equals(lock))
            {
                edit.putString("key$i",lock)
                edit.apply()
            }
        }

        n = sp.getInt("mylevel",1)

//        n=intent.getIntExtra("level",1)

        gmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:yogeshmoradiya747@gmail.com")
            try {
                emailIntent.setPackage("com.google.android.gm")
                startActivity(emailIntent)
            } catch (ex: android.content.ActivityNotFoundException) {
            }
        }

        share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject") // Subject (optional)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Your content to share") // Content to share

            try {
                startActivity(Intent.createChooser(shareIntent, "Share via")) // Show the chooser dialog
            } catch (ex: android.content.ActivityNotFoundException) {
                // Handle case where no sharing app is available
            }
        }


        Continue.setOnClickListener {
            var intent = Intent(this@MainActivity, Gamepage::class.java).putExtra("level", n)
            startActivity(intent)
            finish()

        }

        Puzzles.setOnClickListener {
            var p = Intent(this@MainActivity,Levelpage::class.java)
            startActivity(p)
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { dialog, which ->
            super.onBackPressed()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}