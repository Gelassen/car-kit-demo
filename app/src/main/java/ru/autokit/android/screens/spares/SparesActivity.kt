package ru.autokit.android.screens.spares

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_spares.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.SummaryModel
import ru.autokit.android.model.ViewCarSparesItem
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.calendar.CalendarActivity
import java.util.ArrayList
import javax.inject.Inject

const val PAYLOAD = "payload"

class SparesActivity: BaseActivity(), SparesContract.View, SparesAdapter.Listener {

    companion object {

        fun launch(context: Context, list: List<ServiceDataItem>) {
            val intent = Intent(context, SparesActivity::class.java)
            intent.putParcelableArrayListExtra(PAYLOAD, ArrayList(list))
            context.startActivity(intent)
        }

    }

    @Inject
    lateinit var presenter: SparesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spares)

        (application as AppApplication).getComponent().inject(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = SparesAdapter(this)

        presenter.onStart(this)
        presenter.onPrepareData(intent.getParcelableArrayListExtra(PAYLOAD))

        nextScreen.setOnClickListener { it ->
            val viewModel = (list.adapter as SparesAdapter).getModel()
            presenter.onSubmit(viewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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

    override fun onSubmitModel(model: List<ViewCarSparesItem>) {
        (list.adapter as SparesAdapter).updateModel(model)
    }

    override fun onClick(model: MutableList<ViewCarSparesItem>) {
        presenter.onListUpdate(model)
    }

    override fun onUpdateSummary(model: SummaryModel) {
        cost.setText(resources.getQuantityString(R.plurals.summary_cost_fmt, model.cost, model.cost))
        time.setText(resources.getQuantityString(R.plurals.summary_time_fmt, model.time, model.time))
    }

    override fun openNextStep() {
        startActivity(Intent(this, CalendarActivity::class.java))
    }
}