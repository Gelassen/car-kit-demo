package ru.autokit.android.screens.offers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.activity_offers.list
import kotlinx.android.synthetic.main.activity_offers.nextScreen
import kotlinx.android.synthetic.main.activity_spares.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.ViewServiceDataItem
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.spares.SparesActivity
import javax.inject.Inject

const val KEY_PAYLOAD = "KEY_PAYLOAD"

class OffersActivity: BaseActivity(), OffersContract.View {

    companion object {

        fun launch(
            context: Context,
            payload: List<ServiceDataItem>
        ) {
            var intent = Intent(context, OffersActivity::class.java)
            intent.putParcelableArrayListExtra(KEY_PAYLOAD, ArrayList(payload))
            context.startActivity(intent)
        }

    }

    @Inject
    lateinit var presenter: OffersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        (application as AppApplication).getComponent().inject(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = OffersAdapter()
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

        presenter.onStart(this)
        presenter.onPrepareData(intent.getParcelableArrayListExtra(KEY_PAYLOAD))

        nextScreen.setOnClickListener{it ->
            presenter.onClick((list.adapter as OffersAdapter).getModel())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showData(data: List<ViewServiceDataItem>) {
        nextScreen.visibility = View.VISIBLE
        emptyView.visibility = View.GONE

        (list.adapter as OffersAdapter).updateModel(data)
    }

    override fun openNextScreen(serviceData: List<ServiceDataItem>) {
        SparesActivity.launch(this, serviceData)
    }

    override fun showEmptyScreen() {
        nextScreen.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

}