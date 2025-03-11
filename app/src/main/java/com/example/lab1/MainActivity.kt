package com.example.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var networkReceiver = InternetReceiver()
    private var counterValue = 0

    @SuppressLint("UseKtx")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mysuperapp://test"))
            startActivity(intent)
        }

        val counterView = findViewById<TextView>(R.id.counter)
        savedInstanceState?.getString("counter")?.let {
            counterView.text = it
            counterValue = it.toInt()
        }

        counterView.setOnClickListener{
            counterValue++
            counterView.text = counterValue.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("counter", counterValue.toString())
    }
}
