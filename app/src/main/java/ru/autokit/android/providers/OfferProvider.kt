package ru.autokit.android.providers

import ru.autokit.android.model.ViewCarSparesItem
import java.lang.IllegalStateException

class OfferProvider {

    fun calculateTotalCostAndTime(list: MutableList<ViewCarSparesItem>): Pair<Int, Int> {
        var cost = 0
        var time = 0
        for (item in list) {
            val data = item.data
            if (item.isOriginSelected && item.isReplacementSelected)
                throw IllegalStateException("It is possible to have to fields unselected, but they shouldn't be both selected")

            cost += if (item.isOriginSelected) data.costOrigin!!.toInt() else 0
            cost += if (item.isReplacementSelected) data.costReplacement!!.toInt() else 0
            time += if (item.isOriginSelected) data.originDuration!!.toInt() else 0
            time += if (item.isReplacementSelected) data.replacementDuration!!.toInt() else 0
        }
        return Pair<Int, Int>(cost, time)
    }

}