package com.fatih.finalproject.Activities

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fatih.finalproject.R

class LoginActivity : AppCompatActivity() {

    private lateinit var userEdt: EditText
    private lateinit var passEdt: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        userEdt = findViewById(R.id.editText)
        passEdt = findViewById(R.id.editTextPassword)
        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener {
            val username = userEdt.text.toString()
            val password = passEdt.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill your user and password", Toast.LENGTH_SHORT)
                    .show()
            } else if (username == "test" && password == "test") {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Your user and password is not correct", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}