package com.example.basesdistribuidas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOk = findViewById<Button>(R.id.btnOk)

        btnOk.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        })
    }
}