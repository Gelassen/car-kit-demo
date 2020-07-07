package ru.autokit.android.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CarSparesItem(
    @SerializedName("name") var name: String?,
    @SerializedName("costOrigin") var costOrigin: String?,
    @SerializedName("costReplacement") var costReplacement: String?,
    @SerializedName("replacementProduction") var replacementProduction: String?,
    @SerializedName("originDuration") var originDuration: String?,
    @SerializedName("replacementDuration") var replacementDuration: String?): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(costOrigin)
        parcel.writeString(costReplacement)
        parcel.writeString(replacementProduction)
        parcel.writeString(originDuration)
        parcel.writeString(replacementDuration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarSparesItem> {
        override fun createFromParcel(parcel: Parcel): CarSparesItem {
            return CarSparesItem(parcel)
        }

        override fun newArray(size: Int): Array<CarSparesItem?> {
            return arrayOfNulls(size)
        }
    }
}