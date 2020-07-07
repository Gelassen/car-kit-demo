package ru.autokit.android.screens.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_final.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.screens.BaseActivity
import javax.inject.Inject

class FinalScreen : BaseActivity(), MessageContract.View {

    @Inject
    lateinit var presenter: MessagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        (application as AppApplication).getComponent().inject(this)

        presenter.onStart(this)

        confirm.setOnClickListener {
            presenter.onSubmit()
            finishAffinity()
        }
    }

    override fun onError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
