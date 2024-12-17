package com.fatih.finalproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.fatih.finalproject.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("IntroActivity", "onCreate started") // Tambahkan log di awal
        setContentView(R.layout.activity_intro)

        val getInBtn: Button = findViewById(R.id.getInBtn)
        getInBtn.setOnClickListener {
            Log.d("IntroActivity", "Button clicked") // Tambahkan log untuk memastikan tombol diklik
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        Log.d("IntroActivity", "onCreate finished") // Tambahkan log di akhir
    }
}