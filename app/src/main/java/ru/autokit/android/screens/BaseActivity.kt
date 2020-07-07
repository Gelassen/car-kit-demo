package ru.autokit.android.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_offers.*

abstract class BaseActivity: AppCompatActivity(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressIndicator() {
        placeholder.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        placeholder.visibility = View.GONE
    }
}