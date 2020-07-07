package ru.autokit.android.screens.mileage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mileage.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.offers.OffersActivity
import javax.inject.Inject

class MileageActivity: BaseActivity(), MileageContract.View {

    @Inject
    lateinit var presenter: MileagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mileage)

        (application as AppApplication).getComponent().inject(this)
        presenter.onStart(this)

        nextScreen.setOnClickListener { it ->
            presenter.onEnterMileage(mileAgeField.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun openNextStep(result: List<ServiceDataItem>) {
        OffersActivity.launch(this, result)
    }

    override fun showProgressIndicator() {
        placeholder.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        placeholder.visibility = View.GONE
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}