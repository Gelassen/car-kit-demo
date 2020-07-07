package ru.autokit.android.screens.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_schedule.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.model.CarSparesItem
import ru.autokit.android.model.ScheduleItem
import ru.autokit.android.screens.BaseActivity
import ru.autokit.android.screens.calendar.CalendarActivity
import ru.autokit.android.screens.spares.PAYLOAD
import ru.autokit.android.screens.submitform.SubmitActivity
import java.util.*
import javax.inject.Inject

const val KEY_PAYLOAD = "PAYLOAD"

class ScheduleActivity: BaseActivity(), ScheduleContract.View, ScheduleAdapter.Listener {

    companion object {

        fun launch(context: Context, date: Date) {
            var intent = Intent(context, ScheduleActivity::class.java)
            intent.putExtra(KEY_PAYLOAD, date)
            context.startActivity(intent)
        }

    }

    @Inject
    lateinit var presenter: SchedulePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        (application as AppApplication).getComponent().inject(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ScheduleAdapter(this)

        presenter.onStart(this, (getIntent().getSerializableExtra(KEY_PAYLOAD) as Date).time)
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

    override fun onData(data: List<ScheduleItem>) {
        (list.adapter as ScheduleAdapter).update(data)
    }

    override fun onClick(item: ScheduleItem) {
        presenter.onSubmitData(item)
    }

    override fun onNextStep() {
        val intent = Intent(this, SubmitActivity::class.java)
        startActivity(intent)
    }
}