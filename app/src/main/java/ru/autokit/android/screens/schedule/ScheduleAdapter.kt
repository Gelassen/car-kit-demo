package ru.autokit.android.screens.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.autokit.android.R
import ru.autokit.android.model.ScheduleItem

class ScheduleAdapter(val listener: Listener): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    interface Listener {
        fun onClick(item: ScheduleItem)
    }

    private var model = ArrayList<ScheduleItem>()

    fun update(model: List<ScheduleItem>) {
        this.model.clear()
        this.model.addAll(model)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model.get(position)
        holder.time.setText(item.time)
        holder.itemView.setOnClickListener { it ->
            listener.onClick(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var time: TextView

        init {
            time = itemView.findViewById(R.id.time)
        }

    }
}