package ru.autokit.android.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.autokit.android.model.CarSparesItem
import ru.autokit.android.model.ViewCarSparesItem

class SparesItemConverter {

    fun toViewModel(model: List<CarSparesItem>): List<ViewCarSparesItem> {
        val viewModel = ArrayList<ViewCarSparesItem>()
        for (item in model) {
            viewModel.add(ViewCarSparesItem(item))
        }
        return viewModel
    }

    fun toModel(viewModel: List<ViewCarSparesItem>): List<CarSparesItem> {
        var model = ArrayList<CarSparesItem>()
        for (item in viewModel) {
            model.add(item.data)
        }
        return model
    }

    fun onlySelectedToModel(viewModel: List<ViewCarSparesItem>): List<CarSparesItem>  {
        var model = ArrayList<CarSparesItem>()
        for (item in viewModel) {
            if (item.isOriginSelected || item.isReplacementSelected) {
                model.add(item.data)
            }
        }
        return model
    }

    fun toJson(model: List<CarSparesItem>): String {
        return Gson().toJson(model)
    }

    fun fromJson(json: String): List<CarSparesItem> {
        val type = TypeToken.getParameterized(List::class.java, CarSparesItem::class.java).type
        return Gson().fromJson(json, type)
    }
}