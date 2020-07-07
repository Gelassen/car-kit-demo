package ru.autokit.android.screens.submitform

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_submit.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.message.FinalScreen
import javax.inject.Inject

class SubmitActivity: BaseActivity(), SubmitContract.View {

    @Inject
    lateinit var presenter: SubmitPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        (application as AppApplication).getComponent().inject(this)

        presenter.onStart(this)

        nextScreen.setOnClickListener {
            presenter.onSubmitForm(phoneField.text.toString(), emailField.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showSuccess() {
        val intent = Intent(this, FinalScreen::class.java)
        startActivity(intent)
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressIndicator() {
    }

    override fun hideProgressIndicator() {
    }
}