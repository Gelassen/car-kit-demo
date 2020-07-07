package ru.autokit.android.providers

import org.junit.Test

import ru.autokit.android.BaseTest

import org.junit.Assert.*
import org.junit.Before
import ru.autokit.android.model.CarSparesItem
import ru.autokit.android.model.ViewCarSparesItem

class OfferProviderTest : BaseTest() {

    lateinit var subject: OfferProvider

    @Before
    override fun setUp() {
        super.setUp()
        subject = OfferProvider()
    }

    @Test
    fun calculateTotalCostAndTime_nonSelected_zeroCostAndZeroTime() {
        val list = generateNonSelectedTestData()

        val result = subject.calculateTotalCostAndTime(list)

        assertEquals(0, result.first)
        assertEquals(0, result.second)
    }

    @Test
    fun calculateTotalCostAndTime_oneOriginSelected_nonZeroCostAndNonZeroTime() {
        val list = generateOneSelectedTestData()

        val result = subject.calculateTotalCostAndTime(list)

        assertEquals(1000, result.first)
        assertEquals(1, result.second)
    }

    @Test
    fun calculateTotalCostAndTime_oneReplacementSelected_nonZeroCostAndNonZeroTime() {
        val list = generateOneRepalcementSelectedTestData()

        val result = subject.calculateTotalCostAndTime(list)

        assertEquals(2000, result.first)
        assertEquals(2, result.second)
    }

    @Test(expected = IllegalStateException::class)
    fun calculateTotalCostAndTime_mutualExclusiveFieldsSelected_expectException() {
        val list = generateMutualExclusiveFieldsSelectedTestData()

        val result = subject.calculateTotalCostAndTime(list)

        assertEquals(2000, result.first)
        assertEquals(2, result.second)
    }

    @Test
    fun calculateTotalCostAndTime_bothOriginSelected_nonZeroCostAndNonZeroTime() {
        val list = generateBothOriginSelectedTestData()

        val result = subject.calculateTotalCostAndTime(list)

        assertEquals(4000, result.first)
        assertEquals(11, result.second)
    }

    private fun generateBothOriginSelectedTestData(): MutableList<ViewCarSparesItem> {
        val list = generateNonSelectedTestData()
        list.get(0).isOriginSelected = true
        list.get(1).isOriginSelected = true

        return list
    }

    private fun generateMutualExclusiveFieldsSelectedTestData(): MutableList<ViewCarSparesItem> {
        val list = generateNonSelectedTestData()
        list.get(0).isReplacementSelected = true
        list.get(0).isOriginSelected = true

        return list
    }

    private fun generateOneRepalcementSelectedTestData(): MutableList<ViewCarSparesItem> {
        val list = generateNonSelectedTestData()
        list.get(0).isReplacementSelected = true

        return list
    }

    private fun generateOneSelectedTestData(): MutableList<ViewCarSparesItem> {
        val list = generateNonSelectedTestData()
        list.get(0).isOriginSelected = true

        return list
    }

    private fun generateNonSelectedTestData(): ArrayList<ViewCarSparesItem> {
        val list = ArrayList<ViewCarSparesItem>()
        list.add(ViewCarSparesItem(CarSparesItem("запчасть №1", "1000", "2000", "", "1", "2")))
        list.add(ViewCarSparesItem(CarSparesItem("запчасть №2", "3000", "4000", "", "10", "22")))

        return list
    }
}