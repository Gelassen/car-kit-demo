package ru.autokit.android.screens.intro

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_intro.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.mileage.MileageActivity
import javax.inject.Inject

class IntroActivity : BaseActivity(), IntroContract.View {

    @Inject
    lateinit var presenter: IntroPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        (application as AppApplication).getComponent().inject(this)

        presenter.onStart(this)

        nextScreen.setOnClickListener { it ->
            if (TextUtils.isEmpty(carMake.text.toString())
                && TextUtils.isEmpty(carModel.text.toString())) {
                presenter.onEnterVIN(phoneField.text.toString())
            } else {
                presenter.onEnterVehicle(
                    carMake.text.toString(),
                    carModel.text.toString(),
                    carYear.text.toString()
                )
            }
        }

        carMake.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line))
        carModel.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showVehicleHandInputSection(
        carMakeValue: String,
        carModelValue: String,
        carYearValue: String
    ) {
        carMakeLayout.visibility = View.VISIBLE
        carMake.visibility = View.VISIBLE
        carMake.setText(carMakeValue)

        carModelLayout.visibility = View.VISIBLE
        carModel.visibility = View.VISIBLE
        carModel.setText(carModelValue)

        carYearLayout.visibility = View.VISIBLE
        carYear.visibility = View.VISIBLE
        carYear.setText(carYearValue)
    }

    override fun openNextStep() {
        val intent = Intent(this, MileageActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMakesDatasource(model: List<String>) {
        (carMake.adapter as ArrayAdapter<String>).addAll(model)
    }

    override fun onModelDatasource(model: List<String>) {
        (carModel.adapter as ArrayAdapter<String>).addAll(model)
    }

}