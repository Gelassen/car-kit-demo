package ru.autokit.android.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ServiceDataItem(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String?): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceDataItem> {
        override fun createFromParcel(parcel: Parcel): ServiceDataItem {
            return ServiceDataItem(parcel)
        }

        override fun newArray(size: Int): Array<ServiceDataItem?> {
            return arrayOfNulls(size)
        }
    }
}