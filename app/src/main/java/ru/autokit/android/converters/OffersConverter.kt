package ru.autokit.android.converters

import ru.autokit.android.model.*

class OffersConverter {

    fun toViewModel(model: List<ServiceDataItem>): List<ViewServiceDataItem> {
        var result = ArrayList<ViewServiceDataItem>()
        for (item in model) {
            result.add(ViewServiceDataItem(item))
        }
        return result
    }

    fun toModel(viewModel: List<ViewServiceDataItem>): List<ServiceDataItem> {
        var model = ArrayList<ServiceDataItem>()
        for (item in viewModel) {
            model.add(item.data)
        }
        return model
    }

    fun toModel(viewModel: ViewServiceDataItem): ServiceDataItem {
        return viewModel.data
    }

    fun toModelOfSelected(serviceData: List<ViewServiceDataItem>): List<ServiceDataItem> {
        var result = ArrayList<ServiceDataItem>()
        for (item in serviceData) {
            if (item.isSelected) {
                result.add(item.data)
            }
        }
        return result
    }
}