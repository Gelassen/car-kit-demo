package ru.autokit.android.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.autokit.android.R
import ru.autokit.android.screens.intro.IntroActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)

        finish()
    }
}
