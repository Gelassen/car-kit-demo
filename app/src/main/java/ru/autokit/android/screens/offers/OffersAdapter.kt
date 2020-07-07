package ru.autokit.android.screens.offers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import ru.autokit.android.R
import ru.autokit.android.model.ViewServiceDataItem

class OffersAdapter: RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    private val model = ArrayList<ViewServiceDataItem>()

    fun updateModel(list: List<ViewServiceDataItem>) {
        model.clear()
        model.addAll(list)
        notifyDataSetChanged()
    }

    fun getModel(): List<ViewServiceDataItem> {
        return model
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = holder.itemView.resources
        val item = model.get(position)
        holder.offer.setText(item.data.name)
        holder.itemView.background.setLevel(if (item.isSelected) 1 else 0)
        holder.offer.setTextColor(if (item.isSelected) res.getColor(R.color.selected_text) else res.getColor(R.color.unselected_text))
        holder.itemView.setOnClickListener { it ->
            item.isSelected = !item.isSelected
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var offer: TextView

        init {
            offer = itemView.findViewById(R.id.offer)
        }

    }
}
