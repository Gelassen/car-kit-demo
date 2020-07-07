package ru.autokit.android.screens

interface BaseContract {

    interface View {

        fun onError(error: String)

        fun showProgressIndicator()

        fun hideProgressIndicator()

    }

}